package twitter4j

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
