package twitter4j

/**
 * Allows you to get information about a Tweet’s liking users.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/likes/api-reference/get-tweets-id-liking_users"
 */
@Throws(TwitterException::class)
fun Twitter.getLikingUsers(
    tweetId: Long,
    expansions: String? = null,
    tweetFields: String? = null,
    userFields: String? = null,
): UsersResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    val params = ArrayList<HttpParameter>()

    V2Util.addHttpParamIfNotNull(params, "expansions", expansions)
    V2Util.addHttpParamIfNotNull(params, "tweet.fields", tweetFields)
    V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

    return V2ResponseFactory().createUsersResponse(
        http.get(
            conf.v2Configuration.baseURL + "tweets/" + tweetId + "/liking_users",
            params.toTypedArray(),
            auth,
            this
        ),
        conf
    )
}

/**
 * Allows you to get information about a user’s liked Tweets.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/likes/api-reference/get-users-id-liked_tweets"
 */
@Throws(TwitterException::class)
fun Twitter.getLikedTweets(
    userId: Long,
    expansions: String? = null,
    maxResults: Int? = null,
    paginationToken: String? = null,
    mediaFields: String? = null,
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
        http.get(conf.v2Configuration.baseURL + "users/" + userId + "/liked_tweets", params.toTypedArray(), auth, this),
        conf
    )
}

/**
 * Causes the user ID identified in the path parameter to Like the target Tweet.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/likes/api-reference/post-users-id-likes"
 */
@Throws(TwitterException::class)
fun Twitter.likeTweet(
    userId: Long,
    tweetId: Long
): BooleanResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    val json = JSONObject()
    json.put("tweet_id", tweetId.toString())

    return V2ResponseFactory().createBooleanResponse(
        http.post(
            conf.v2Configuration.baseURL + "users/" + userId + "/likes",
            arrayOf(HttpParameter(json)),
            auth,
            this
        ),
        conf,
        "liked"
    )
}

/**
 * Allows a user or authenticated user ID to unlike a Tweet.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/likes/api-reference/delete-users-id-likes-tweet_id"
 */
@Throws(TwitterException::class)
fun Twitter.unlikeTweet(
    userId: Long,
    tweetId: Long
): BooleanResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    return V2ResponseFactory().createBooleanResponse(
        http.delete(
            conf.v2Configuration.baseURL + "users/" + userId + "/likes/" + tweetId,
            emptyArray(),
            auth,
            this
        ),
        conf,
        "liked"
    )
}
