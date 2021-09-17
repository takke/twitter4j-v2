package twitter4j

/**
 * Hides or unhides a reply to a Tweet.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/hide-replies/api-reference/put-tweets-id-hidden"
 */
@Throws(TwitterException::class)
fun Twitter.hideReplies(
    tweetId: Long,
    hidden: Boolean
): BooleanResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    val json = JSONObject()
    json.put("hidden", hidden)

    return V2ResponseFactory().createBooleanResponse(
        http.put(
            conf.v2Configuration.baseURL + "tweets/" + tweetId + "/hidden",
            arrayOf(HttpParameter(json)),
            auth,
            this
        ),
        conf,
        "hidden"
    )
}
