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

    /**
     * Allows you to get an authenticated user's 800 most recent bookmarked Tweets.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/bookmarks/api-reference/get-users-id-bookmarks"
     */
    @Throws(TwitterException::class)
    fun getBookmarks(
        /**
         * User ID of an authenticated user to request bookmarked Tweets for.
         */
        id: Long,
        expansions: String? = null,
        maxResults: Int? = null,
        mediaFields: String? = null,
        paginationToken: PaginationToken? = null,
        placeFields: String? = null,
        pollFields: String? = null,
        tweetFields: String? = null,
        userFields: String? = null,
    ): TweetsResponse

    /**
     * Causes the user ID identified in the path parameter to Bookmark the target Tweet provided in the request body.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/bookmarks/api-reference/post-users-id-bookmarks"
     */
    @Throws(TwitterException::class)
    fun addBookmark(
        /**
         * The user ID who you are bookmarking a Tweet on behalf of.
         */
        id: Long,
        tweetId: Long,
    ): BooleanResponse

    /**
     * Allows a user or authenticated user ID to remove a Bookmark of a Tweet.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/bookmarks/api-reference/delete-users-id-bookmarks-tweet_id"
     */
    @Throws(TwitterException::class)
    fun deleteBookmark(
        /**
         * The user ID who you are bookmarking a Tweet on behalf of.
         */
        id: Long,
        tweetId: Long,
    ): BooleanResponse

    /**
     * Returns a variety of information about one or more users specified by the requested IDs.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/users/lookup/api-reference/get-users"
     */
    @Throws(TwitterException::class)
    fun getUsers(
        vararg ids: Long,
        tweetFields: String? = V2DefaultFields.tweetFields,
        userFields: String? = V2DefaultFields.userFields,
        expansions: String = "pinned_tweet_id"
    ): UsersResponse

    /**
     * Returns a variety of information about one or more users specified by their usernames.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/users/lookup/api-reference/get-users-by"
     */
    @Throws(TwitterException::class)
    fun getUsersBy(
        vararg usernames: String,
        tweetFields: String? = V2DefaultFields.tweetFields,
        userFields: String? = V2DefaultFields.userFields,
        expansions: String = "pinned_tweet_id"
    ): UsersResponse

    /**
     * Returns information about an authorized user.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/users/lookup/api-reference/get-users-me"
     */
    @Throws(TwitterException::class)
    fun getMe(
        expansions: String = "pinned_tweet_id",
        tweetFields: String? = V2DefaultFields.tweetFields,
        userFields: String? = V2DefaultFields.userFields,
    ): UsersResponse

    /**
     * Returns a list of users the specified user ID is following.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/users/follows/api-reference/get-users-id-following"
     */
    @Throws(TwitterException::class)
    fun getFollowingUsers(
        userId: Long,
        expansions: String? = "pinned_tweet_id",
        maxResults: Int? = null,
        paginationToken: PaginationToken? = null,
        tweetFields: String? = null,
        userFields: String? = null,
    ): UsersResponse

    /**
     * Returns a list of users who are followers of the specified user ID.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/users/follows/api-reference/get-users-id-followers"
     */
    @Throws(TwitterException::class)
    fun getFollowerUsers(
        userId: Long,
        expansions: String? = "pinned_tweet_id",
        maxResults: Int? = null,
        paginationToken: PaginationToken? = null,
        tweetFields: String? = null,
        userFields: String? = null
    ): UsersResponse

    /**
     * Allows a user ID to follow another user.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/users/follows/api-reference/post-users-source_user_id-following"
     */
    @Throws(TwitterException::class)
    fun followUser(
        sourceUserId: Long,
        targetUserId: Long
    ): FollowResponse

    /**
     * Allows a user ID to unfollow another user.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/users/follows/api-reference/delete-users-source_id-following"
     */
    @Throws(TwitterException::class)
    fun unfollowUser(
        sourceUserId: Long,
        targetUserId: Long
    ): BooleanResponse

    /**
     * Returns a list of users who are blocked by the specified user ID.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/users/blocks/api-reference/get-users-blocking"
     */
    @Throws(TwitterException::class)
    fun getBlockingUsers(
        userId: Long,
        expansions: String? = "pinned_tweet_id",
        maxResults: Int? = null,
        paginationToken: PaginationToken? = null,
        tweetFields: String? = null,
        userFields: String? = null,
    ): UsersResponse

    /**
     * Causes the user (in the path) to block the target user. The user (in the path) must match the user context authorizing the request.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/users/blocks/api-reference/post-users-user_id-blocking"
     */
    @Throws(TwitterException::class)
    fun blockUser(
        sourceUserId: Long,
        targetUserId: Long
    ): BooleanResponse

    /**
     * Allows a user or authenticated user ID to unblock another user.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/users/blocks/api-reference/delete-users-user_id-blocking"
     */
    @Throws(TwitterException::class)
    fun unblockUser(
        sourceUserId: Long,
        targetUserId: Long
    ): BooleanResponse

    /**
     * Returns a list of users who are muted by the specified user ID.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/users/mutes/api-reference/get-users-muting"
     */
    @Throws(TwitterException::class)
    fun getMutingUsers(
        /**
         * The user ID whose muted users you would like to retrieve. The user’s ID must correspond to the user ID of the
         * authenticating user, meaning that you must pass the Access Tokens associated with the user ID when authenticating
         * your request.
         */
        userId: Long,
        expansions: String? = "pinned_tweet_id",
        /**
         * The maximum number of results to be returned per page. This can be a number between 1 and 1000. By default, each page will return 100 results.
         */
        maxResults: Int? = null,
        paginationToken: PaginationToken? = null,
        tweetFields: String? = null,
        userFields: String? = null,
    ): UsersResponse

    /**
     * Allows an authenticated user ID to mute the target user.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/users/mutes/api-reference/post-users-user_id-muting"
     */
    @Throws(TwitterException::class)
    fun muteUser(
        sourceUserId: Long,
        targetUserId: Long
    ): BooleanResponse

    /**
     * Allows an authenticated user ID to unmute the target user.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/users/mutes/api-reference/delete-users-user_id-muting"
     */
    @Throws(TwitterException::class)
    fun unmuteUser(
        sourceUserId: Long,
        targetUserId: Long
    ): BooleanResponse

    /**
     * Returns details about multiple Spaces. Up to 100 comma-separated Spaces IDs can be looked up using this endpoint.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/spaces/lookup/api-reference/get-spaces"
     */
    @Throws(TwitterException::class)
    fun getSpaces(
        vararg spaceIds: String,
        expansions: String? = null,
        spaceFields: String? = null,
        userFields: String? = null,
    ): SpacesResponse

    /**
     * Returns live or scheduled Spaces created by the specified user IDs. Up to 100 comma-separated IDs can be looked up using this endpoint.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/spaces/lookup/api-reference/get-spaces-by-creator-ids"
     */
    @Throws(TwitterException::class)
    fun getSpacesByCreatorIds(
        vararg userIds: Long,
        expansions: String? = null,
        spaceFields: String? = null,
        userFields: String? = null,
    ): SpacesResponse

    /**
     * The recent search endpoint returns Tweets from the last seven days that match a search query.
     *
     * @throws TwitterException when Twitter service or network is unavailable
     * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/search/api-reference/get-tweets-search-recent"
     */
    @Throws(TwitterException::class)
    fun searchSpaces(
        query: String,
        state: Space.State,
        expansions: String? = null,
        maxResults: Int? = null,
        spaceFields: String? = null,
        userFields: String? = null,
    ): SpacesResponse

}
