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
