package twitter4j

import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.util.*

private const val MB = 1024 * 1024 // 1 MByte

private const val MAX_IMAGE_SIZE: Int = 5 * MB // 5MB is a constraint imposed by Twitter for image files

private const val MAX_VIDEO_SIZE: Int = 512 * MB // 512MB is a constraint  imposed by Twitter for video files

private const val CHUNK_SIZE = 2 * MB // max chunk size

class TwitterV2Impl(private val twitter: Twitter) : TwitterV2 {

    private val conf by lazy { twitter.configuration }

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
            get(conf.v2Configuration.baseURL + "tweets", params.toTypedArray()),
            conf
        )
    }

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
            post(conf.v2Configuration.baseURL + "tweets", json),
            conf
        )
    }

    @Throws(TwitterException::class)
    override fun deleteTweet(
        id: Long
    ): BooleanResponse {

        return V2ResponseFactory().createBooleanResponse(
            delete(conf.v2Configuration.baseURL + "tweets/" + id),
            conf,
            "deleted"
        )
    }

    @Throws(TwitterException::class)
    override fun getUserTweets(
        userId: Long,
        endTime: Date?,
        exclude: String?,
        expansions: String?,
        maxResults: Int?,
        mediaFields: String?,
        paginationToken: PaginationToken?,
        placeFields: String?,
        pollFields: String?,
        sinceId: Long?,
        startTime: Date?,
        tweetFields: String?,
        untilId: Long?,
        userFields: String?,
    ): TweetsResponse {

        return getUserTweetsIn(
            conf.v2Configuration.baseURL + "users/" + userId + "/tweets",
            endTime,
            exclude,
            expansions,
            maxResults,
            mediaFields,
            paginationToken,
            placeFields,
            pollFields,
            sinceId,
            startTime,
            tweetFields,
            untilId,
            userFields
        )
    }

    @Throws(TwitterException::class)
    override fun getUserMentions(
        userId: Long,
        endTime: Date?,
        expansions: String?,
        maxResults: Int?,
        mediaFields: String?,
        paginationToken: PaginationToken?,
        placeFields: String?,
        pollFields: String?,
        sinceId: Long?,
        startTime: Date?,
        tweetFields: String?,
        untilId: Long?,
        userFields: String?,
    ): TweetsResponse {

        return getUserTweetsIn(
            conf.v2Configuration.baseURL + "users/" + userId + "/mentions",
            endTime,
            null,
            expansions,
            maxResults,
            mediaFields,
            paginationToken,
            placeFields,
            pollFields,
            sinceId,
            startTime,
            tweetFields,
            untilId,
            userFields
        )
    }

    @Throws(TwitterException::class)
    override fun getReverseChronologicalTimeline(
        userId: Long,
        endTime: Date?,
        exclude: String?,
        expansions: String?,
        maxResults: Int?,
        mediaFields: String?,
        paginationToken: PaginationToken?,
        placeFields: String?,
        pollFields: String?,
        sinceId: Long?,
        startTime: Date?,
        tweetFields: String?,
        untilId: Long?,
        userFields: String?,
    ): TweetsResponse {

        return getUserTweetsIn(
            conf.v2Configuration.baseURL + "users/" + userId + "/timelines/reverse_chronological",
            endTime,
            exclude,
            expansions,
            maxResults,
            mediaFields,
            paginationToken,
            placeFields,
            pollFields,
            sinceId,
            startTime,
            tweetFields,
            untilId,
            userFields
        )
    }

    // 2021/09/14 時点では 500 が返ってくるのでコメントアウトしておく
    //
    ///**
    // * Returns the most recent Tweets composed by a single user specified by the requested username.
    // *
    // * @throws TwitterException when Twitter service or network is unavailable
    // * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/timelines/api-reference/get-users-by-username-username-tweets"
    // */
    //@Throws(TwitterException::class)
    //fun getUserTweetsBy(
    //    username: String,
    //    endTime: Date? = null,
    //    exclude: String? = null,
    //    expansions: String? = null,
    //    maxResults: Int? = null,
    //    mediaFields: String? = null,
    //    paginationToken: PaginationToken? = null,
    //    placeFields: String? = null,
    //    pollFields: String? = null,
    //    sinceId: Long? = null,
    //    startTime: Date? = null,
    //    tweetFields: String? = null,
    //    untilId: Long? = null,
    //    userFields: String? = null,
    //): TweetsResponse {
    //
    //    return getUserTweetsIn(
    //        conf.v2Configuration.baseURL + "users/by/username/" + username + "/tweets",
    //        endTime,
    //        exclude,
    //        expansions,
    //        maxResults,
    //        mediaFields,
    //        paginationToken,
    //        placeFields,
    //        pollFields,
    //        sinceId,
    //        startTime,
    //        tweetFields,
    //        untilId,
    //        userFields
    //    )
    //}

    @Throws(TwitterException::class)
    private fun getUserTweetsIn(
        url: String,
        endTime: Date?,
        exclude: String?,
        expansions: String?,
        maxResults: Int?,
        mediaFields: String?,
        paginationToken: PaginationToken?,
        placeFields: String?,
        pollFields: String?,
        sinceId: Long?,
        startTime: Date?,
        tweetFields: String?,
        untilId: Long?,
        userFields: String?
    ): TweetsResponse {

        val params = ArrayList<HttpParameter>()

        V2Util.addHttpParamIfNotNull(params, "end_time", V2Util.dateToISO8601(endTime))
        V2Util.addHttpParamIfNotNull(params, "exclude", exclude)
        V2Util.addHttpParamIfNotNull(params, "expansions", expansions)
        V2Util.addHttpParamIfNotNull(params, "max_results", maxResults)
        V2Util.addHttpParamIfNotNull(params, "media.fields", mediaFields)
        V2Util.addHttpParamIfNotNull(params, "pagination_token", paginationToken)
        V2Util.addHttpParamIfNotNull(params, "place.fields", placeFields)
        V2Util.addHttpParamIfNotNull(params, "poll.fields", pollFields)
        V2Util.addHttpParamIfNotNull(params, "since_id", sinceId)
        V2Util.addHttpParamIfNotNull(params, "start_time", V2Util.dateToISO8601(startTime))
        V2Util.addHttpParamIfNotNull(params, "tweet.fields", tweetFields)
        V2Util.addHttpParamIfNotNull(params, "until_id", untilId)
        V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

        return V2ResponseFactory().createTweetsResponse(
            get(url, params.toTypedArray()),
            conf
        )
    }

    @Throws(TwitterException::class)
    override fun searchRecent(
        query: String,
        endTime: Date?,
        expansions: String?,
        maxResults: Int?,
        mediaFields: String?,
        nextToken: PaginationToken?,
        placeFields: String?,
        pollFields: String?,
        sinceId: Long?,
        startTime: Date?,
        tweetFields: String?,
        untilId: Long?,
        userFields: String?,
    ): TweetsResponse {

        return searchTweetsIn(
            conf.v2Configuration.baseURL + "tweets/search/recent",
            query,
            endTime,
            expansions,
            maxResults,
            mediaFields,
            nextToken,
            placeFields,
            pollFields,
            sinceId,
            startTime,
            tweetFields,
            untilId,
            userFields
        )
    }

    @Throws(TwitterException::class)
    override fun searchAll(
        query: String,
        endTime: Date?,
        expansions: String?,
        maxResults: Int?,
        mediaFields: String?,
        nextToken: PaginationToken?,
        placeFields: String?,
        pollFields: String?,
        sinceId: Long?,
        startTime: Date?,
        tweetFields: String?,
        untilId: Long?,
        userFields: String?,
    ): TweetsResponse {

        return searchTweetsIn(
            conf.v2Configuration.baseURL + "tweets/search/all",
            query,
            endTime,
            expansions,
            maxResults,
            mediaFields,
            nextToken,
            placeFields,
            pollFields,
            sinceId,
            startTime,
            tweetFields,
            untilId,
            userFields
        )
    }

    @Throws(TwitterException::class)
    private fun searchTweetsIn(
        url: String,
        query: String,
        endTime: Date?,
        expansions: String?,
        maxResults: Int?,
        mediaFields: String?,
        nextToken: PaginationToken?,
        placeFields: String?,
        pollFields: String?,
        sinceId: Long?,
        startTime: Date?,
        tweetFields: String?,
        untilId: Long?,
        userFields: String?
    ): TweetsResponse {

        val params = ArrayList<HttpParameter>()

        params.add(HttpParameter("query", query))

        V2Util.addHttpParamIfNotNull(params, "end_time", V2Util.dateToISO8601(endTime))
        V2Util.addHttpParamIfNotNull(params, "expansions", expansions)
        V2Util.addHttpParamIfNotNull(params, "max_results", maxResults)
        V2Util.addHttpParamIfNotNull(params, "media.fields", mediaFields)
        V2Util.addHttpParamIfNotNull(params, "next_token", nextToken)
        V2Util.addHttpParamIfNotNull(params, "place.fields", placeFields)
        V2Util.addHttpParamIfNotNull(params, "poll.fields", pollFields)
        V2Util.addHttpParamIfNotNull(params, "since_id", sinceId)
        V2Util.addHttpParamIfNotNull(params, "start_time", V2Util.dateToISO8601(startTime))
        V2Util.addHttpParamIfNotNull(params, "tweet.fields", tweetFields)
        V2Util.addHttpParamIfNotNull(params, "until_id", untilId)
        V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

        return V2ResponseFactory().createTweetsResponse(
            get(url, params.toTypedArray()),
            conf
        )
    }

    @Throws(TwitterException::class)
    override fun countRecent(
        query: String,
        endTime: Date?,
        granularity: String?,
        sinceId: Long?,
        startTime: Date?,
        untilId: Long?
    ): CountsResponse {

        return countTweetsIn(
            conf.v2Configuration.baseURL + "tweets/counts/recent",
            query,
            endTime,
            granularity,
            null,
            sinceId,
            startTime,
            untilId,
        )
    }

    @Throws(TwitterException::class)
    override fun countAll(
        query: String,
        endTime: Date?,
        granularity: String?,
        nextToken: PaginationToken?,
        sinceId: Long?,
        startTime: Date?,
        untilId: Long?
    ): CountsResponse {

        return countTweetsIn(
            conf.v2Configuration.baseURL + "tweets/counts/all",
            query,
            endTime,
            granularity,
            nextToken,
            sinceId,
            startTime,
            untilId,
        )
    }

    @Throws(TwitterException::class)
    private fun countTweetsIn(
        url: String,
        query: String,
        endTime: Date?,
        granularity: String? = null,
        nextToken: PaginationToken? = null,
        sinceId: Long?,
        startTime: Date?,
        untilId: Long?
    ): CountsResponse {

        val params = ArrayList<HttpParameter>()

        params.add(HttpParameter("query", query))

        V2Util.addHttpParamIfNotNull(params, "end_time", V2Util.dateToISO8601(endTime))
        V2Util.addHttpParamIfNotNull(params, "granularity", granularity)
        V2Util.addHttpParamIfNotNull(params, "next_token", nextToken)
        V2Util.addHttpParamIfNotNull(params, "since_id", sinceId)
        V2Util.addHttpParamIfNotNull(params, "start_time", V2Util.dateToISO8601(startTime))
        V2Util.addHttpParamIfNotNull(params, "until_id", untilId)

        return V2ResponseFactory().createCountsResponse(
            get(url, params.toTypedArray()),
            conf
        )
    }

    @Throws(TwitterException::class)
    override fun getRetweetUsers(
        tweetId: Long,
        expansions: String?,
        tweetFields: String?,
        userFields: String?,
    ): UsersResponse {

        val params = ArrayList<HttpParameter>()

        V2Util.addHttpParamIfNotNull(params, "expansions", expansions)
        V2Util.addHttpParamIfNotNull(params, "tweet.fields", tweetFields)
        V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

        return V2ResponseFactory().createUsersResponse(
            get(conf.v2Configuration.baseURL + "tweets/" + tweetId + "/retweeted_by", params.toTypedArray()),
            conf
        )
    }

    @Throws(TwitterException::class)
    override fun getQuoteTweets(
        id: Long,
        expansions: String?,
        maxResults: Int?,
        exclude: String?,
        mediaFields: String?,
        paginationToken: PaginationToken?,
        placeFields: String?,
        pollFields: String?,
        tweetFields: String?,
        userFields: String?,
    ): TweetsResponse {

        val params = ArrayList<HttpParameter>()

        V2Util.addHttpParamIfNotNull(params, "expansions", expansions)
        V2Util.addHttpParamIfNotNull(params, "max_results", maxResults)
        V2Util.addHttpParamIfNotNull(params, "exclude", exclude)
        V2Util.addHttpParamIfNotNull(params, "pagination_token", paginationToken)
        V2Util.addHttpParamIfNotNull(params, "media.fields", mediaFields)
        V2Util.addHttpParamIfNotNull(params, "place.fields", placeFields)
        V2Util.addHttpParamIfNotNull(params, "poll.fields", pollFields)
        V2Util.addHttpParamIfNotNull(params, "tweet.fields", tweetFields)
        V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

        return V2ResponseFactory().createTweetsResponse(
            get(conf.v2Configuration.baseURL + "tweets/" + id + "/quote_tweets", params.toTypedArray()),
            conf
        )
    }

    @Throws(TwitterException::class)
    override fun retweet(
        userId: Long,
        tweetId: Long
    ): BooleanResponse {

        val json = JSONObject()
        json.put("tweet_id", tweetId.toString())

        return V2ResponseFactory().createBooleanResponse(
            post(
                conf.v2Configuration.baseURL + "users/" + userId + "/retweets",
                arrayOf(HttpParameter(json))
            ),
            conf,
            "retweeted"
        )
    }

    @Throws(TwitterException::class)
    override fun unretweet(
        userId: Long,
        tweetId: Long
    ): BooleanResponse {

        return V2ResponseFactory().createBooleanResponse(
            delete(conf.v2Configuration.baseURL + "users/" + userId + "/retweets/" + tweetId),
            conf,
            "retweeted"
        )
    }

    @Throws(TwitterException::class)
    override fun getLikingUsers(
        tweetId: Long,
        expansions: String?,
        tweetFields: String?,
        userFields: String?,
    ): UsersResponse {

        val params = ArrayList<HttpParameter>()

        V2Util.addHttpParamIfNotNull(params, "expansions", expansions)
        V2Util.addHttpParamIfNotNull(params, "tweet.fields", tweetFields)
        V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

        return V2ResponseFactory().createUsersResponse(
            get(conf.v2Configuration.baseURL + "tweets/" + tweetId + "/liking_users", params.toTypedArray()),
            conf
        )
    }

    @Throws(TwitterException::class)
    override fun getLikedTweets(
        userId: Long,
        expansions: String?,
        maxResults: Int?,
        paginationToken: PaginationToken?,
        mediaFields: String?,
        placeFields: String?,
        pollFields: String?,
        tweetFields: String?,
        userFields: String?,
    ): TweetsResponse {

        val params = ArrayList<HttpParameter>()

        V2Util.addHttpParamIfNotNull(params, "expansions", expansions)
        V2Util.addHttpParamIfNotNull(params, "max_results", maxResults)
        V2Util.addHttpParamIfNotNull(params, "pagination_token", paginationToken)
        V2Util.addHttpParamIfNotNull(params, "media.fields", mediaFields)
        V2Util.addHttpParamIfNotNull(params, "place.fields", placeFields)
        V2Util.addHttpParamIfNotNull(params, "poll.fields", pollFields)
        V2Util.addHttpParamIfNotNull(params, "tweet.fields", tweetFields)
        V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

        return V2ResponseFactory().createTweetsResponse(
            get(conf.v2Configuration.baseURL + "users/" + userId + "/liked_tweets", params.toTypedArray()),
            conf
        )
    }

    @Throws(TwitterException::class)
    override fun likeTweet(
        userId: Long,
        tweetId: Long
    ): BooleanResponse {

        val json = JSONObject()
        json.put("tweet_id", tweetId.toString())

        return V2ResponseFactory().createBooleanResponse(
            post(conf.v2Configuration.baseURL + "users/" + userId + "/likes", arrayOf(HttpParameter(json))),
            conf,
            "liked"
        )
    }

    @Throws(TwitterException::class)
    override fun unlikeTweet(
        userId: Long,
        tweetId: Long
    ): BooleanResponse {

        return V2ResponseFactory().createBooleanResponse(
            delete(conf.v2Configuration.baseURL + "users/" + userId + "/likes/" + tweetId),
            conf,
            "liked"
        )
    }

    @Throws(TwitterException::class)
    override fun getBookmarks(
        id: Long,
        expansions: String?,
        maxResults: Int?,
        mediaFields: String?,
        paginationToken: PaginationToken?,
        placeFields: String?,
        pollFields: String?,
        tweetFields: String?,
        userFields: String?,
    ): TweetsResponse {

        val params = ArrayList<HttpParameter>()

        V2Util.addHttpParamIfNotNull(params, "expansions", expansions)
        V2Util.addHttpParamIfNotNull(params, "max_results", maxResults)
        V2Util.addHttpParamIfNotNull(params, "pagination_token", paginationToken)
        V2Util.addHttpParamIfNotNull(params, "media.fields", mediaFields)
        V2Util.addHttpParamIfNotNull(params, "place.fields", placeFields)
        V2Util.addHttpParamIfNotNull(params, "poll.fields", pollFields)
        V2Util.addHttpParamIfNotNull(params, "tweet.fields", tweetFields)
        V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

        return V2ResponseFactory().createTweetsResponse(
            get(conf.v2Configuration.baseURL + "users/" + id + "/bookmarks", params.toTypedArray()),
            conf
        )
    }

    @Throws(TwitterException::class)
    override fun addBookmark(
        id: Long,
        tweetId: Long,
    ): BooleanResponse {

        val json = JSONObject()
        json.put("tweet_id", tweetId.toString())

        return V2ResponseFactory().createBooleanResponse(
            post(conf.v2Configuration.baseURL + "users/" + id + "/bookmarks", arrayOf(HttpParameter(json))),
            conf, "bookmarked"
        )
    }

    @Throws(TwitterException::class)
    override fun deleteBookmark(
        id: Long,
        tweetId: Long,
    ): BooleanResponse {

        return V2ResponseFactory().createBooleanResponse(
            delete(conf.v2Configuration.baseURL + "users/" + id + "/bookmarks/" + tweetId),
            conf, "bookmarked"
        )
    }

    @Throws(TwitterException::class)
    override fun hideReplies(
        tweetId: Long,
        hidden: Boolean
    ): BooleanResponse {

        val json = JSONObject()
        json.put("hidden", hidden)

        return V2ResponseFactory().createBooleanResponse(
            put(
                conf.v2Configuration.baseURL + "tweets/" + tweetId + "/hidden",
                arrayOf(HttpParameter(json))
            ),
            conf,
            "hidden"
        )
    }

    @Throws(TwitterException::class)
    override fun getUsers(
        vararg ids: Long,
        tweetFields: String?,
        userFields: String?,
        expansions: String
    ): UsersResponse {

        val params = arrayListOf(
            HttpParameter("ids", ids.joinToString(",")),
            HttpParameter("expansions", expansions)
        )

        V2Util.addHttpParamIfNotNull(params, "tweet.fields", tweetFields)
        V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

        return V2ResponseFactory().createUsersResponse(
            get(conf.v2Configuration.baseURL + "users", params.toTypedArray()),
            conf
        )
    }

    @Throws(TwitterException::class)
    override fun getUsersBy(
        vararg usernames: String,
        tweetFields: String?,
        userFields: String?,
        expansions: String
    ): UsersResponse {

        val params = arrayListOf(
            HttpParameter("usernames", usernames.joinToString(",")),
            HttpParameter("expansions", expansions)
        )

        V2Util.addHttpParamIfNotNull(params, "tweet.fields", tweetFields)
        V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

        return V2ResponseFactory().createUsersResponse(
            get(conf.v2Configuration.baseURL + "users/by", params.toTypedArray()),
            conf
        )
    }

    @Throws(TwitterException::class)
    override fun getMe(
        expansions: String,
        tweetFields: String?,
        userFields: String?,
    ): UsersResponse {

        val params = arrayListOf(
            HttpParameter("expansions", expansions)
        )

        V2Util.addHttpParamIfNotNull(params, "tweet.fields", tweetFields)
        V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

        return V2ResponseFactory().createUsersResponse(
            get(conf.v2Configuration.baseURL + "users/me", params.toTypedArray()),
            conf
        )
    }

    @Throws(TwitterException::class)
    override fun getFollowingUsers(
        userId: Long,
        expansions: String?,
        maxResults: Int?,
        paginationToken: PaginationToken?,
        tweetFields: String?,
        userFields: String?,
    ): UsersResponse {

        val params = ArrayList<HttpParameter>()

        V2Util.addHttpParamIfNotNull(params, "expansions", expansions)
        V2Util.addHttpParamIfNotNull(params, "max_results", maxResults)
        V2Util.addHttpParamIfNotNull(params, "pagination_token", paginationToken)
        V2Util.addHttpParamIfNotNull(params, "tweet.fields", tweetFields)
        V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

        return V2ResponseFactory().createUsersResponse(
            get(conf.v2Configuration.baseURL + "users/" + userId + "/following", params.toTypedArray()),
            conf
        )
    }

    @Throws(TwitterException::class)
    override fun getFollowerUsers(
        userId: Long,
        expansions: String?,
        maxResults: Int?,
        paginationToken: PaginationToken?,
        tweetFields: String?,
        userFields: String?
    ): UsersResponse {

        val params = ArrayList<HttpParameter>()

        V2Util.addHttpParamIfNotNull(params, "expansions", expansions)
        V2Util.addHttpParamIfNotNull(params, "max_results", maxResults)
        V2Util.addHttpParamIfNotNull(params, "pagination_token", paginationToken)
        V2Util.addHttpParamIfNotNull(params, "tweet.fields", tweetFields)
        V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

        return V2ResponseFactory().createUsersResponse(
            get(conf.v2Configuration.baseURL + "users/" + userId + "/followers", params.toTypedArray()),
            conf
        )
    }

    @Throws(TwitterException::class)
    override fun followUser(
        sourceUserId: Long,
        targetUserId: Long
    ): FollowResponse {

        val json = JSONObject()
        json.put("target_user_id", targetUserId.toString())

        return V2ResponseFactory().createFollowResponse(
            post(conf.v2Configuration.baseURL + "users/" + sourceUserId + "/following", json),
            conf
        )
    }

    @Throws(TwitterException::class)
    override fun unfollowUser(
        sourceUserId: Long,
        targetUserId: Long
    ): BooleanResponse {

        return V2ResponseFactory().createBooleanResponse(
            delete(conf.v2Configuration.baseURL + "users/" + sourceUserId + "/following/" + targetUserId),
            conf,
            "following"
        )
    }

    @Throws(TwitterException::class)
    override fun getBlockingUsers(
        userId: Long,
        expansions: String?,
        maxResults: Int?,
        paginationToken: PaginationToken?,
        tweetFields: String?,
        userFields: String?,
    ): UsersResponse {

        val params = ArrayList<HttpParameter>()

        V2Util.addHttpParamIfNotNull(params, "expansions", expansions)
        V2Util.addHttpParamIfNotNull(params, "max_results", maxResults)
        V2Util.addHttpParamIfNotNull(params, "pagination_token", paginationToken)
        V2Util.addHttpParamIfNotNull(params, "tweet.fields", tweetFields)
        V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

        return V2ResponseFactory().createUsersResponse(
            get(conf.v2Configuration.baseURL + "users/" + userId + "/blocking", params.toTypedArray()),
            conf
        )
    }

    @Throws(TwitterException::class)
    override fun blockUser(
        sourceUserId: Long,
        targetUserId: Long
    ): BooleanResponse {

        val json = JSONObject()
        json.put("target_user_id", targetUserId.toString())

        return V2ResponseFactory().createBooleanResponse(
            post(conf.v2Configuration.baseURL + "users/" + sourceUserId + "/blocking", json),
            conf,
            "blocking"
        )
    }

    @Throws(TwitterException::class)
    override fun unblockUser(
        sourceUserId: Long,
        targetUserId: Long
    ): BooleanResponse {

        return V2ResponseFactory().createBooleanResponse(
            delete(conf.v2Configuration.baseURL + "users/" + sourceUserId + "/blocking/" + targetUserId),
            conf,
            "blocking"
        )
    }

    @Throws(TwitterException::class)
    override fun getMutingUsers(
        userId: Long,
        expansions: String?,
        maxResults: Int?,
        paginationToken: PaginationToken?,
        tweetFields: String?,
        userFields: String?,
    ): UsersResponse {

        val params = ArrayList<HttpParameter>()

        V2Util.addHttpParamIfNotNull(params, "expansions", expansions)
        V2Util.addHttpParamIfNotNull(params, "max_results", maxResults)
        V2Util.addHttpParamIfNotNull(params, "pagination_token", paginationToken)
        V2Util.addHttpParamIfNotNull(params, "tweet.fields", tweetFields)
        V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

        return V2ResponseFactory().createUsersResponse(
            get(conf.v2Configuration.baseURL + "users/" + userId + "/muting", params.toTypedArray()),
            conf
        )
    }

    @Throws(TwitterException::class)
    override fun muteUser(
        sourceUserId: Long,
        targetUserId: Long
    ): BooleanResponse {

        val json = JSONObject()
        json.put("target_user_id", targetUserId.toString())

        return V2ResponseFactory().createBooleanResponse(
            post(conf.v2Configuration.baseURL + "users/" + sourceUserId + "/muting", json),
            conf,
            "muting"
        )
    }

    @Throws(TwitterException::class)
    override fun unmuteUser(
        sourceUserId: Long,
        targetUserId: Long
    ): BooleanResponse {

        return V2ResponseFactory().createBooleanResponse(
            delete(conf.v2Configuration.baseURL + "users/" + sourceUserId + "/muting/" + targetUserId),
            conf,
            "muting"
        )
    }

    @Throws(TwitterException::class)
    override fun getSpaces(
        vararg spaceIds: String,
        expansions: String?,
        spaceFields: String?,
        userFields: String?,
    ): SpacesResponse {

        val params = arrayListOf(
            HttpParameter("ids", spaceIds.joinToString(",")),
        )

        V2Util.addHttpParamIfNotNull(params, "expansions", expansions)
        V2Util.addHttpParamIfNotNull(params, "space.fields", spaceFields)
        V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

        return V2ResponseFactory().createSpacesResponse(
            get(conf.v2Configuration.baseURL + "spaces", params.toTypedArray()),
            conf
        )
    }

    @Throws(TwitterException::class)
    override fun getSpacesByCreatorIds(
        vararg userIds: Long,
        expansions: String?,
        spaceFields: String?,
        userFields: String?,
    ): SpacesResponse {

        val params = arrayListOf(
            HttpParameter("user_ids", userIds.joinToString(",")),
        )

        V2Util.addHttpParamIfNotNull(params, "expansions", expansions)
        V2Util.addHttpParamIfNotNull(params, "space.fields", spaceFields)
        V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

        return V2ResponseFactory().createSpacesResponse(
            get(conf.v2Configuration.baseURL + "spaces/by/creator_ids", params.toTypedArray()),
            conf
        )
    }

    @Throws(TwitterException::class)
    override fun searchSpaces(
        query: String,
        state: Space.State,
        expansions: String?,
        maxResults: Int?,
        spaceFields: String?,
        userFields: String?,
    ): SpacesResponse {

        val params = arrayListOf(
            HttpParameter("query", query),
            HttpParameter("state", state.rawValue),
        )

        V2Util.addHttpParamIfNotNull(params, "expansions", expansions)
        V2Util.addHttpParamIfNotNull(params, "max_results", maxResults)
        V2Util.addHttpParamIfNotNull(params, "space.fields", spaceFields)
        V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

        return V2ResponseFactory().createSpacesResponse(
            get(conf.v2Configuration.baseURL + "spaces/search", params.toTypedArray()),
            conf
        )
    }

    @Throws(TwitterException::class)
    override fun getList(
        id: Long,
        expansions: String?,
        listFields: String?,
        userFields: String?,
    ): ListsResponse {

        val params = ArrayList<HttpParameter>()

        V2Util.addHttpParamIfNotNull(params, "expansions", expansions)
        V2Util.addHttpParamIfNotNull(params, "list.fields", listFields)
        V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

        return V2ResponseFactory().createListsResponse(
            get(conf.v2Configuration.baseURL + "lists/" + id, params.toTypedArray()),
            conf
        )
    }

    @Throws(TwitterException::class)
    override fun getOwnedLists(
        id: Long,
        expansions: String?,
        listFields: String?,
        maxResults: Int?,
        paginationToken: PaginationToken?,
        userFields: String?,
    ): ListsResponse {

        val params = ArrayList<HttpParameter>()

        V2Util.addHttpParamIfNotNull(params, "expansions", expansions)
        V2Util.addHttpParamIfNotNull(params, "list.fields", listFields)
        V2Util.addHttpParamIfNotNull(params, "max_results", maxResults)
        V2Util.addHttpParamIfNotNull(params, "pagination_token", paginationToken)
        V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

        return V2ResponseFactory().createListsResponse(
            get(conf.v2Configuration.baseURL + "users/" + id + "/owned_lists", params.toTypedArray()),
            conf
        )
    }

    @Throws(TwitterException::class)
    override fun getListTweets(
        id: Long,
        expansions: String?,
        maxResults: Int?,
        paginationToken: PaginationToken?,
        tweetFields: String?,
        userFields: String?,
    ): TweetsResponse {

        val params = ArrayList<HttpParameter>()

        V2Util.addHttpParamIfNotNull(params, "expansions", expansions)
        V2Util.addHttpParamIfNotNull(params, "max_results", maxResults)
        V2Util.addHttpParamIfNotNull(params, "pagination_token", paginationToken)
        V2Util.addHttpParamIfNotNull(params, "tweet.fields", tweetFields)
        V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

        return V2ResponseFactory().createTweetsResponse(
            get(conf.v2Configuration.baseURL + "lists/" + id + "/tweets", params.toTypedArray()),
            conf
        )
    }

    @Throws(TwitterException::class)
    override fun getListMembers(
        id: Long,
        expansions: String?,
        maxResults: Int?,
        paginationToken: PaginationToken?,
        tweetFields: String?,
        userFields: String?,
    ): UsersResponse {

        val params = ArrayList<HttpParameter>()

        V2Util.addHttpParamIfNotNull(params, "expansions", expansions)
        V2Util.addHttpParamIfNotNull(params, "max_results", maxResults)
        V2Util.addHttpParamIfNotNull(params, "pagination_token", paginationToken)
        V2Util.addHttpParamIfNotNull(params, "tweet.fields", tweetFields)
        V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

        return V2ResponseFactory().createUsersResponse(
            get(conf.v2Configuration.baseURL + "lists/" + id + "/members", params.toTypedArray()),
            conf
        )
    }

    @Throws(TwitterException::class)
    override fun getListMemberships(
        id: Long,
        expansions: String?,
        listFields: String?,
        maxResults: Int?,
        paginationToken: PaginationToken?,
        userFields: String?,
    ): ListsResponse {

        val params = ArrayList<HttpParameter>()

        V2Util.addHttpParamIfNotNull(params, "expansions", expansions)
        V2Util.addHttpParamIfNotNull(params, "list.fields", listFields)
        V2Util.addHttpParamIfNotNull(params, "max_results", maxResults)
        V2Util.addHttpParamIfNotNull(params, "pagination_token", paginationToken)
        V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

        return V2ResponseFactory().createListsResponse(
            get(conf.v2Configuration.baseURL + "users/" + id + "/list_memberships", params.toTypedArray()),
            conf
        )
    }

    @Throws(TwitterException::class)
    override fun getListFollowers(
        id: Long,
        expansions: String?,
        maxResults: Int?,
        paginationToken: PaginationToken?,
        tweetFields: String?,
        userFields: String?,
    ): UsersResponse {

        val params = ArrayList<HttpParameter>()

        V2Util.addHttpParamIfNotNull(params, "expansions", expansions)
        V2Util.addHttpParamIfNotNull(params, "max_results", maxResults)
        V2Util.addHttpParamIfNotNull(params, "pagination_token", paginationToken)
        V2Util.addHttpParamIfNotNull(params, "tweet.fields", tweetFields)
        V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

        return V2ResponseFactory().createUsersResponse(
            get(conf.v2Configuration.baseURL + "lists/" + id + "/followers", params.toTypedArray()),
            conf
        )
    }

    @Throws(TwitterException::class)
    override fun getFollowedLists(
        id: Long,
        expansions: String?,
        maxResults: Int?,
        paginationToken: PaginationToken?,
        userFields: String?,
    ): ListsResponse {

        val params = ArrayList<HttpParameter>()

        V2Util.addHttpParamIfNotNull(params, "expansions", expansions)
        V2Util.addHttpParamIfNotNull(params, "max_results", maxResults)
        V2Util.addHttpParamIfNotNull(params, "pagination_token", paginationToken)
        V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

        return V2ResponseFactory().createListsResponse(
            get(conf.v2Configuration.baseURL + "users/" + id + "/followed_lists", params.toTypedArray()),
            conf
        )
    }

    @Throws(TwitterException::class)
    override fun getPinnedLists(
        id: Long,
        expansions: String?,
        listFields: String?,
        userFields: String?,
    ): ListsResponse {

        val params = ArrayList<HttpParameter>()

        V2Util.addHttpParamIfNotNull(params, "expansions", expansions)
        V2Util.addHttpParamIfNotNull(params, "list.fields", listFields)
        V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

        return V2ResponseFactory().createListsResponse(
            get(conf.v2Configuration.baseURL + "users/" + id + "/pinned_lists", params.toTypedArray()),
            conf
        )
    }

    @Throws(TwitterException::class)
    override fun createList(
        name: String,
        description: String?,
        private: Boolean?,
    ): ListsResponse {

        val json = JSONObject()
        json.put("name", name)

        if (description != null) {
            json.put("description", description)
        }

        if (private != null) {
            json.put("private", private)
        }

        return V2ResponseFactory().createListsResponse(
            post(conf.v2Configuration.baseURL + "lists", json),
            conf
        )
    }

    @Throws(TwitterException::class)
    override fun deleteList(
        id: Long
    ): BooleanResponse {

        return V2ResponseFactory().createBooleanResponse(
            delete(conf.v2Configuration.baseURL + "lists/" + id),
            conf,
            "deleted"
        )
    }

    @Throws(TwitterException::class)
    override fun addListMember(
        listId: Long,
        userId: Long
    ): BooleanResponse {

        val json = JSONObject()
        json.put("user_id", userId.toString())

        return V2ResponseFactory().createBooleanResponse(
            post(conf.v2Configuration.baseURL + "lists/" + listId + "/members", json),
            conf,
            "is_member"
        )
    }

    @Throws(TwitterException::class)
    override fun deleteListMember(
        listId: Long,
        userId: Long
    ): BooleanResponse {

        return V2ResponseFactory().createBooleanResponse(
            delete(conf.v2Configuration.baseURL + "lists/" + listId + "/members/" + userId),
            conf,
            "is_member"
        )
    }

    @Throws(TwitterException::class)
    override fun followList(
        userId: Long,
        listId: Long
    ): BooleanResponse {

        val json = JSONObject()
        json.put("list_id", listId.toString())

        return V2ResponseFactory().createBooleanResponse(
            post(conf.v2Configuration.baseURL + "users/${userId}/followed_lists", json),
            conf,
            "following"
        )
    }

    @Throws(TwitterException::class)
    override fun unfollowList(
        userId: Long,
        listId: Long,
    ): BooleanResponse {

        return V2ResponseFactory().createBooleanResponse(
            delete(conf.v2Configuration.baseURL + "users/${userId}/followed_lists/${listId}"),
            conf,
            "following"
        )
    }

    @Throws(TwitterException::class)
    override fun pinList(
        userId: Long,
        listId: Long
    ): BooleanResponse {

        val json = JSONObject()
        json.put("list_id", listId.toString())

        return V2ResponseFactory().createBooleanResponse(
            post(conf.v2Configuration.baseURL + "users/${userId}/pinned_lists", json),
            conf,
            "pinned"
        )
    }

    @Throws(TwitterException::class)
    override fun unpinList(
        userId: Long,
        listId: Long,
    ): BooleanResponse {

        return V2ResponseFactory().createBooleanResponse(
            delete(conf.v2Configuration.baseURL + "users/${userId}/pinned_lists/${listId}"),
            conf,
            "pinned"
        )
    }

    @Throws(TwitterException::class)
    override fun uploadMediaChunkedInit(size: Long, mediaType: String): LongResponse {
        val json = JSONObject()
        json.put("media_type", mediaType)
        json.put("total_bytes", size)

        return V2ResponseFactory().createLongResponse(
            post(conf.v2Configuration.baseURL + "media/upload/initialize", json),
            conf,
            "id"
        )
    }

    @Throws(TwitterException::class)
    override fun uploadMediaChunkedAppend(mediaId: Long, segmentIndex: Long, fileName: String, media: InputStream) {
        val params = arrayListOf(
            HttpParameter("media", fileName, media),
            HttpParameter("segment_index", segmentIndex),
        )

        post(conf.v2Configuration.baseURL + "media/upload/${mediaId}/append", params.toTypedArray())
    }

    @Throws(TwitterException::class)
    override fun uploadMediaChunkedFinalize(mediaId: Long): LongResponse {
        return V2ResponseFactory().createLongResponse(
            post(conf.v2Configuration.baseURL + "media/upload/${mediaId}/finalize", emptyArray<HttpParameter>()),
            conf,
            "id"
        )
    }

    @Throws(TwitterException::class)
    override fun uploadMedia(mediaType: String, fileName: String, media: InputStream): LongResponse {
        val dataBytes = try {
            media.readBytes().also {
                if (it.size > MAX_IMAGE_SIZE && mediaType.startsWith("image/")) {
                    throw TwitterException("Image file can't exceed ${MAX_IMAGE_SIZE / MB} MB")
                }

                if (it.size > MAX_VIDEO_SIZE && mediaType.startsWith("video/")) {
                    throw TwitterException("Video file can't exceed ${MAX_VIDEO_SIZE / MB} MB")
                }
            }
        } catch (ioe: IOException) {
            throw TwitterException("Failed to download the file.", ioe)
        }

        val response = uploadMediaChunkedInit(dataBytes.size.toLong(), mediaType)
        val dataInputStream = ByteArrayInputStream(dataBytes)
        var segmentIndex = 0L

        while (true) {
            val segmentData = dataInputStream.readNBytes(CHUNK_SIZE)
            if (segmentData.isEmpty()) break
            uploadMediaChunkedAppend(response.mediaId, segmentIndex++, fileName, ByteArrayInputStream(segmentData))
        }

        uploadMediaChunkedFinalize(response.mediaId)
        return response
    }

    //--------------------------------------------------
    // get/post/delete
    //--------------------------------------------------

    private fun get(url: String, params: Array<HttpParameter>): HttpResponse {

        if (twitter !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")
        twitter.ensureAuthorizationEnabled()

        return twitter.http.get(url, params, twitter.auth, twitter)
    }

    private fun post(url: String, json: JSONObject): HttpResponse {
        return post(url, arrayOf(HttpParameter(json)))
    }

    private fun post(url: String, params: Array<HttpParameter>): HttpResponse {

        if (twitter !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")
        twitter.ensureAuthorizationEnabled()

        return twitter.http.post(url, params, twitter.auth, twitter)
    }

    private fun delete(url: String): HttpResponse {

        if (twitter !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")
        twitter.ensureAuthorizationEnabled()

        return twitter.http.delete(url, emptyArray(), twitter.auth, twitter)
    }

    private fun put(url: String, params: Array<HttpParameter>): HttpResponse {

        if (twitter !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")
        twitter.ensureAuthorizationEnabled()

        // TODO Support put method at the constructor of HttpRequest
        return twitter.http.put(url, params, twitter.auth, twitter)
    }

}
