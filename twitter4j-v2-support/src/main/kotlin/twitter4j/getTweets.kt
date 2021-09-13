package twitter4j

/**
 * Returns a variety of information about the Tweet specified by the requested ID or list of IDs.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/lookup/api-reference/get-tweets"
 */
@Throws(TwitterException::class)
fun Twitter.getTweets(
    vararg tweetId: Long,
    mediaFields: String? = V2DefaultFields.mediaFields,
    placeFields: String? = V2DefaultFields.placeFields,
    pollFields: String? = V2DefaultFields.pollFields,
    tweetFields: String? = V2DefaultFields.tweetFields,
    userFields: String? = V2DefaultFields.userFields,
    expansions: String = V2DefaultFields.expansions
): TweetsResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    val params = arrayListOf(
        HttpParameter("ids", tweetId.joinToString(",")),
        HttpParameter("expansions", expansions)
    )

    if (mediaFields != null) {
        params.add(HttpParameter("media.fields", mediaFields))
    }

    if (placeFields != null) {
        params.add(HttpParameter("place.fields", placeFields))
    }

    if (pollFields != null) {
        params.add(HttpParameter("poll.fields", pollFields))
    }

    if (tweetFields != null) {
        params.add(HttpParameter("tweet.fields", tweetFields))
    }

    if (userFields != null) {
        params.add(HttpParameter("user.fields", userFields))
    }

    return TweetsFactory().createTweetsResponse(
        http.get(conf.v2Configuration.baseURL + "tweets", params.toTypedArray(), auth, this),
        conf
    )
}
