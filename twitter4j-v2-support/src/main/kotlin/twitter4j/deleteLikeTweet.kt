package twitter4j

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
