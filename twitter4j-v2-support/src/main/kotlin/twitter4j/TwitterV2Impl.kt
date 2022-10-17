package twitter4j

class TwitterV2Impl(private val twitter: Twitter) : TwitterV2 {

    /**
     * Returns a variety of information about the Tweet specified by the requested ID or list of IDs.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/lookup/api-reference/get-tweets"
     */
    @Throws(TwitterException::class)
    override fun getTweets(
        vararg tweetId: Long,
        mediaFields: String?,
        placeFields: String?,
        pollFields: String?,
        tweetFields: String?,
        userFields: String?,
        expansions: String
    ): TweetsResponse {

        if (twitter !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

        twitter.ensureAuthorizationEnabled()

        val params = arrayListOf(
            HttpParameter("ids", tweetId.joinToString(",")),
            HttpParameter("expansions", expansions)
        )

        V2Util.addHttpParamIfNotNull(params, "media.fields", mediaFields)
        V2Util.addHttpParamIfNotNull(params, "place.fields", placeFields)
        V2Util.addHttpParamIfNotNull(params, "poll.fields", pollFields)
        V2Util.addHttpParamIfNotNull(params, "tweet.fields", tweetFields)
        V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

        return V2ResponseFactory().createTweetsResponse(
            twitter.http.get(twitter.conf.v2Configuration.baseURL + "tweets", params.toTypedArray(), twitter.auth, twitter),
            twitter.conf
        )
    }
}
