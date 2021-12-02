package twitter4j

/**
 * Creates a Tweet on behalf of an authenticated user.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/manage-tweets/api-reference/post-tweets"
 */
@Throws(TwitterException::class)
fun Twitter.createTweet(
    directMessageDeepLink: String? = null,
    forSuperFollowersOnly: Boolean? = null,
    placeId: String? = null,
    mediaIds: Array<Long>? = null,
    taggedUserIds: Array<Long>? = null,
    pollDurationMinutes: Long? = null,
    pollOptions: Array<String>? = null,
    quoteTweetId: Long? = null,
    excludeReplyUserIds: Array<Long>? = null,
    inReplyToTweetId: Long? = null,
    replySettings: ReplySettings? = null,
    text: String? = null,
): CreateTweetResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

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
                    put(it)
                }
            })

            // tagged_user_ids
            if (taggedUserIds != null) {
                put("tagged_user_ids", JSONArray().apply {
                    taggedUserIds.forEach {
                        put(it)
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
        json.put("quote_tweet_id", quoteTweetId)
    }

    if (inReplyToTweetId != null) {
        json.put("reply", JSONObject().apply {
            put("in_reply_to_tweet_id", inReplyToTweetId.toString())

            if (excludeReplyUserIds != null) {
                put("exclude_reply_user_ids", JSONArray().apply {
                    excludeReplyUserIds.forEach {
                        put(it)
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
        http.post(
            conf.v2Configuration.baseURL + "tweets",
            arrayOf(HttpParameter(json)),
            auth,
            this
        ),
        conf
    )
}

/**
 * Allows a user or authenticated user ID to delete a Tweet.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/manage-tweets/api-reference/delete-tweets-id"
 */
@Throws(TwitterException::class)
fun Twitter.deleteTweet(
    /**
     * The Tweet ID you are deleting.
     */
    id: Long
): BooleanResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    return V2ResponseFactory().createBooleanResponse(
        http.delete(conf.v2Configuration.baseURL + "tweets/" + id, emptyArray(), auth, this),
        conf,
        "deleted"
    )
}

