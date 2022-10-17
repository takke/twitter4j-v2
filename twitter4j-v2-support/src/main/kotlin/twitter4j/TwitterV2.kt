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

    /**
     * The recent search endpoint returns Tweets from the last seven days that match a search query.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/search/api-reference/get-tweets-search-recent"
     */
    @Throws(TwitterException::class)
    fun searchRecent(
        query: String,
        endTime: Date? = null,
        expansions: String? = null,
        maxResults: Int? = null,
        mediaFields: String? = null,
        nextToken: PaginationToken? = null,
        placeFields: String? = null,
        pollFields: String? = null,
        sinceId: Long? = null,
        startTime: Date? = null,
        tweetFields: String? = null,
        untilId: Long? = null,
        userFields: String? = null,
    ): TweetsResponse

    /**
     * The full-archive search endpoint returns the complete history of public Tweets matching a search query; since the first Tweet was created March 26, 2006.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/search/api-reference/get-tweets-search-all"
     */
    @Throws(TwitterException::class)
    fun searchAll(
        query: String,
        endTime: Date? = null,
        expansions: String? = null,
        maxResults: Int? = null,
        mediaFields: String? = null,
        nextToken: PaginationToken? = null,
        placeFields: String? = null,
        pollFields: String? = null,
        sinceId: Long? = null,
        startTime: Date? = null,
        tweetFields: String? = null,
        untilId: Long? = null,
        userFields: String? = null,
    ): TweetsResponse

    /**
     * The recent Tweet counts endpoint returns count of Tweets from the last seven days that match a search query.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/counts/api-reference/get-tweets-counts-recent"
     */
    @Throws(TwitterException::class)
    fun countRecent(
        query: String,
        endTime: Date? = null,
        granularity: String? = null,
        sinceId: Long? = null,
        startTime: Date? = null,
        untilId: Long? = null
    ): CountsResponse

    /**
     * The full-archive search endpoint returns the complete history of public Tweets matching a search query; since the first Tweet was created March 26, 2006.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/counts/api-reference/get-tweets-counts-all"
     */
    @Throws(TwitterException::class)
    fun countAll(
        query: String,
        endTime: Date? = null,
        granularity: String? = null,
        nextToken: PaginationToken? = null,
        sinceId: Long? = null,
        startTime: Date? = null,
        untilId: Long? = null
    ): CountsResponse

    /**
     * Allows you to get information about who has Retweeted a Tweet.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/retweets/api-reference/get-tweets-id-retweeted_by"
     */
    @Throws(TwitterException::class)
    fun getRetweetUsers(
        tweetId: Long,
        expansions: String? = null,
        tweetFields: String? = null,
        userFields: String? = null,
    ): UsersResponse

    /**
     * Returns Quote Tweets for a Tweet specified by the requested Tweet ID.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/quote-tweets/api-reference/get-tweets-id-quote_tweets"
     */
    @Throws(TwitterException::class)
    fun getQuoteTweets(
        /**
         * Unique identifier of the Tweet to request.
         */
        id: Long,
        expansions: String? = null,
        maxResults: Int? = null,
        exclude: String? = null,
        mediaFields: String? = null,
        paginationToken: PaginationToken? = null,
        placeFields: String? = null,
        pollFields: String? = null,
        tweetFields: String? = null,
        userFields: String? = null,
    ): TweetsResponse

    /**
     * Causes the user ID identified in the path parameter to Retweet the target Tweet.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/retweets/api-reference/post-users-id-retweets"
     */
    @Throws(TwitterException::class)
    fun retweet(
        userId: Long,
        tweetId: Long
    ): BooleanResponse

    /**
     * Allows a user or authenticated user ID to remove the Retweet of a Tweet.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/retweets/api-reference/delete-users-id-retweets-tweet_id"
     */
    @Throws(TwitterException::class)
    fun unretweet(
        userId: Long,
        tweetId: Long
    ): BooleanResponse

    /**
     * Allows you to get information about a Tweet’s liking users.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/likes/api-reference/get-tweets-id-liking_users"
     */
    @Throws(TwitterException::class)
    fun getLikingUsers(
        tweetId: Long,
        expansions: String? = null,
        tweetFields: String? = null,
        userFields: String? = null,
    ): UsersResponse

    /**
     * Allows you to get information about a user’s liked Tweets.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/likes/api-reference/get-users-id-liked_tweets"
     */
    @Throws(TwitterException::class)
    fun getLikedTweets(
        userId: Long,
        expansions: String? = null,
        maxResults: Int? = null,
        paginationToken: PaginationToken? = null,
        mediaFields: String? = null,
        placeFields: String? = null,
        pollFields: String? = null,
        tweetFields: String? = null,
        userFields: String? = null,
    ): TweetsResponse

    /**
     * Causes the user ID identified in the path parameter to Like the target Tweet.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/likes/api-reference/post-users-id-likes"
     */
    @Throws(TwitterException::class)
    fun likeTweet(
        userId: Long,
        tweetId: Long
    ): BooleanResponse

    /**
     * Allows a user or authenticated user ID to unlike a Tweet.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/likes/api-reference/delete-users-id-likes-tweet_id"
     */
    @Throws(TwitterException::class)
    fun unlikeTweet(
        userId: Long,
        tweetId: Long
    ): BooleanResponse
}
