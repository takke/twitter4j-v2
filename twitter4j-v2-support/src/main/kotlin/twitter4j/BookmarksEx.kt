package twitter4j

/**
 * Allows you to get an authenticated user's 800 most recent bookmarked Tweets.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/bookmarks/api-reference/get-users-id-bookmarks"
 */
@Throws(TwitterException::class)
fun Twitter.getBookmarks(
    /**
     * User ID of an authenticated user to request bookmarked Tweets for.
     */
    id: Long,
    expansions: String? = null,
    maxResults: Int? = null,
    mediaFields: String? = null,
    paginationToken: String? = null,
    placeFields: String? = null,
    pollFields: String? = null,
    tweetFields: String? = null,
    userFields: String? = null,
): TweetsResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    val params = ArrayList<HttpParameter>()

    V2Util.addHttpParamIfNotNull(params, "expansions", expansions)
    V2Util.addHttpParamIfNotNull(params, "max_results", maxResults)
    V2Util.addHttpParamIfNotNull(params, "pagination_token", paginationToken)
    V2Util.addHttpParamIfNotNull(params, "media.fields", mediaFields)
    V2Util.addHttpParamIfNotNull(params, "place.fields", placeFields)
    V2Util.addHttpParamIfNotNull(params, "poll.fields", pollFields)
    V2Util.addHttpParamIfNotNull(params, "tweet.fields", tweetFields)
    V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

    return V2ResponseFactory().createTweetsResponse(
        http.get(conf.v2Configuration.baseURL + "users/" + id + "/bookmarks", params.toTypedArray(), auth, this),
        conf
    )
}

/**
 * Causes the user ID identified in the path parameter to Bookmark the target Tweet provided in the request body.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/bookmarks/api-reference/post-users-id-bookmarks"
 */
@Throws(TwitterException::class)
fun Twitter.addBookmark(
    /**
     * The user ID who you are bookmarking a Tweet on behalf of.
     */
    id: Long,
    tweetId: Long,
): BooleanResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    val json = JSONObject()
    json.put("tweet_id", tweetId.toString())

    return V2ResponseFactory().createBooleanResponse(
        http.post(conf.v2Configuration.baseURL + "users/" + id + "/bookmarks", arrayOf(HttpParameter(json)), auth, this),
        conf, "bookmarked"
    )
}

/**
 * Allows a user or authenticated user ID to remove a Bookmark of a Tweet.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/bookmarks/api-reference/delete-users-id-bookmarks-tweet_id"
 */
@Throws(TwitterException::class)
fun Twitter.deleteBookmark(
    /**
     * The user ID who you are bookmarking a Tweet on behalf of.
     */
    id: Long,
    tweetId: Long,
): BooleanResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    return V2ResponseFactory().createBooleanResponse(
        http.delete(conf.v2Configuration.baseURL + "users/" + id + "/bookmarks/" + tweetId, emptyArray(), auth, this),
        conf, "bookmarked"
    )
}
