package twitter4j

import java.util.*

interface TwitterV2 {

    /**
     * Returns a variety of information about the Tweet specified by the requested ID or list of IDs.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/lookup/api-reference/get-tweets"
     */
    @Throws(TwitterException::class)
    fun getTweets(
        vararg tweetId: Long,
        mediaFields: String? = V2DefaultFields.mediaFields,
        placeFields: String? = V2DefaultFields.placeFields,
        pollFields: String? = V2DefaultFields.pollFields,
        tweetFields: String? = V2DefaultFields.tweetFields,
        userFields: String? = V2DefaultFields.userFields,
        expansions: String = V2DefaultFields.expansions
    ): TweetsResponse

    /**
     * Creates a Tweet on behalf of an authenticated user.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/manage-tweets/api-reference/post-tweets"
     */
    @Throws(TwitterException::class)
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

    /**
     * Allows a user or authenticated user ID to delete a Tweet.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/manage-tweets/api-reference/delete-tweets-id"
     */
    @Throws(TwitterException::class)
    fun deleteTweet(
        /**
         * The Tweet ID you are deleting.
         */
        id: Long
    ): BooleanResponse

    /**
     * Returns Tweets composed by a single user, specified by the requested user ID.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/timelines/api-reference/get-users-id-tweets"
     */
    @Throws(TwitterException::class)
    fun getUserTweets(
        userId: Long,
        endTime: Date? = null,
        exclude: String? = null,
        expansions: String? = null,
        maxResults: Int? = null,
        mediaFields: String? = null,
        paginationToken: PaginationToken? = null,
        placeFields: String? = null,
        pollFields: String? = null,
        sinceId: Long? = null,
        startTime: Date? = null,
        tweetFields: String? = null,
        untilId: Long? = null,
        userFields: String? = null,
    ): TweetsResponse

    /**
     * Returns Tweets mentioning a single user specified by the requested user ID.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/timelines/api-reference/get-users-id-mentions"
     */
    @Throws(TwitterException::class)
    fun getUserMentions(
        userId: Long,
        endTime: Date? = null,
        expansions: String? = null,
        maxResults: Int? = null,
        mediaFields: String? = null,
        paginationToken: PaginationToken? = null,
        placeFields: String? = null,
        pollFields: String? = null,
        sinceId: Long? = null,
        startTime: Date? = null,
        tweetFields: String? = null,
        untilId: Long? = null,
        userFields: String? = null,
    ): TweetsResponse

    /**
     * Allows you to retrieve a collection of the most recent Tweets and Retweets posted by you and users you follow.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/timelines/api-reference/get-users-id-reverse-chronological"
     */
    @Throws(TwitterException::class)
    fun getReverseChronologicalTimeline(
        userId: Long,
        endTime: Date? = null,
        exclude: String? = null,
        expansions: String? = null,
        maxResults: Int? = null,
        mediaFields: String? = null,
        paginationToken: PaginationToken? = null,
        placeFields: String? = null,
        pollFields: String? = null,
        sinceId: Long? = null,
        startTime: Date? = null,
        tweetFields: String? = null,
        untilId: Long? = null,
        userFields: String? = null,
    ): TweetsResponse


}
