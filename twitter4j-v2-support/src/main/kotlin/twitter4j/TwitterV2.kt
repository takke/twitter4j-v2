package twitter4j

interface TwitterV2 {

    fun getTweets(
        vararg tweetId: Long,
        mediaFields: String? = V2DefaultFields.mediaFields,
        placeFields: String? = V2DefaultFields.placeFields,
        pollFields: String? = V2DefaultFields.pollFields,
        tweetFields: String? = V2DefaultFields.tweetFields,
        userFields: String? = V2DefaultFields.userFields,
        expansions: String = V2DefaultFields.expansions
    ): TweetsResponse

    fun createTweet(
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
    ): CreateTweetResponse

    fun deleteTweet(
        /**
         * The Tweet ID you are deleting.
         */
        id: Long
    ): BooleanResponse
}
