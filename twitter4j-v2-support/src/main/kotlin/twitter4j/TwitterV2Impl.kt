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

    /**
     * Creates a Tweet on behalf of an authenticated user.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/manage-tweets/api-reference/post-tweets"
     */
    @Throws(TwitterException::class)
    override fun createTweet(
        directMessageDeepLink: String?,
        forSuperFollowersOnly: Boolean?,
        placeId: String?,
        mediaIds: Array<Long>?,
        taggedUserIds: Array<Long>?,
        pollDurationMinutes: Long?,
        pollOptions: Array<String>?,
        quoteTweetId: Long?,
        excludeReplyUserIds: Array<Long>?,
        inReplyToTweetId: Long?,
        replySettings: ReplySettings?,
        text: String?,
    ): CreateTweetResponse {

        if (twitter !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

        twitter.ensureAuthorizationEnabled()

        val json = JSONObject()

        if (directMessageDeepLink != null) {
            json.put("direct_message_deep_link", directMessageDeepLink)
        }

        if (forSuperFollowersOnly != null) {
            json.put("for_super_followers_only", forSuperFollowersOnly)
        }

        if (placeId != null) {
            // geo.place_id
            json.put("geo", JSONObject().apply {
                put("place_id", placeId)
            })
        }

        // media
        if (mediaIds != null) {
            json.put("media", JSONObject().apply {
                put("media_ids", JSONArray().apply {
                    mediaIds.forEach {
                        put(it.toString())
                    }
                })

                // tagged_user_ids
                if (taggedUserIds != null) {
                    put("tagged_user_ids", JSONArray().apply {
                        taggedUserIds.forEach {
                            put(it.toString())
                        }
                    })
                }
            })
        }

        // poll
        if (pollDurationMinutes != null && pollOptions != null) {
            json.put("poll", JSONObject().apply {
                put("duration_minutes", pollDurationMinutes)

                put("options", JSONArray().apply {
                    pollOptions.forEach {
                        put(it)
                    }
                })
            })
        }

        if (quoteTweetId != null) {
            json.put("quote_tweet_id", quoteTweetId.toString())
        }

        if (inReplyToTweetId != null) {
            json.put("reply", JSONObject().apply {
                put("in_reply_to_tweet_id", inReplyToTweetId.toString())

                if (excludeReplyUserIds != null) {
                    put("exclude_reply_user_ids", JSONArray().apply {
                        excludeReplyUserIds.forEach {
                            put(it.toString())
                        }
                    })
                }
            })
        }


        if (replySettings != null) {
            json.put("reply_settings", replySettings.value)
        }

        if (text != null) {
            json.put("text", text)
        }

        return V2ResponseFactory().createCreateTweetResponse(
            twitter.http.post(
                twitter.conf.v2Configuration.baseURL + "tweets",
                arrayOf(HttpParameter(json)),
                twitter.auth,
                twitter
            ),
            twitter.conf
        )
    }

    /**
     * Allows a user or authenticated user ID to delete a Tweet.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/manage-tweets/api-reference/delete-tweets-id"
     */
    @Throws(TwitterException::class)
    override fun deleteTweet(
        /**
         * The Tweet ID you are deleting.
         */
        id: Long
    ): BooleanResponse {

        if (twitter !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

        twitter.ensureAuthorizationEnabled()

        return V2ResponseFactory().createBooleanResponse(
            twitter.http.delete(twitter.conf.v2Configuration.baseURL + "tweets/" + id, emptyArray(), twitter.auth, twitter),
            twitter.conf,
            "deleted"
        )
    }

}
