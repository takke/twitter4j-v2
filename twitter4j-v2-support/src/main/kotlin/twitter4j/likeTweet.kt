package twitter4j

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
