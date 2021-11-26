package twitter4j

/**
 * Allows you to get information about who has Retweeted a Tweet.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/retweets/api-reference/get-tweets-id-retweeted_by"
 */
@Throws(TwitterException::class)
fun Twitter.getRetweetUsers(
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
            conf.v2Configuration.baseURL + "tweets/" + tweetId + "/retweeted_by",
            params.toTypedArray(),
            auth,
            this
        ),
        conf
    )
}

/**
 * Causes the user ID identified in the path parameter to Retweet the target Tweet.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/retweets/api-reference/post-users-id-retweets"
 */
@Throws(TwitterException::class)
fun Twitter.retweet(
    userId: Long,
    tweetId: Long
): BooleanResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    val json = JSONObject()
    json.put("tweet_id", tweetId.toString())

    return V2ResponseFactory().createBooleanResponse(
        http.post(
            conf.v2Configuration.baseURL + "users/" + userId + "/retweets",
            arrayOf(HttpParameter(json)),
            auth,
            this
        ),
        conf,
        "retweeted"
    )
}

/**
 * Allows a user or authenticated user ID to remove the Retweet of a Tweet.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/retweets/api-reference/delete-users-id-retweets-tweet_id"
 */
@Throws(TwitterException::class)
fun Twitter.unretweet(
    userId: Long,
    tweetId: Long
): BooleanResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    return V2ResponseFactory().createBooleanResponse(
        http.delete(
            conf.v2Configuration.baseURL + "users/" + userId + "/retweets/" + tweetId,
            emptyArray(),
            auth,
            this
        ),
        conf,
        "retweeted"
    )
}
