package twitter4j

/**
 * Returns a list of users the specified user ID is following.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/users/follows/api-reference/get-users-id-following"
 */
@Throws(TwitterException::class)
fun Twitter.getFollowingUsers(
    userId: Long,
    expansions: String? = "pinned_tweet_id",
    maxResults: Int? = null,
    paginationToken: String? = null,
    tweetFields: String? = null,
    userFields: String? = null,
): UsersResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    val params = ArrayList<HttpParameter>()

    V2Util.addHttpParamIfNotNull(params, "expansions", expansions)
    V2Util.addHttpParamIfNotNull(params, "max_results", maxResults)
    V2Util.addHttpParamIfNotNull(params, "pagination_token", paginationToken)
    V2Util.addHttpParamIfNotNull(params, "tweet.fields", tweetFields)
    V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

    return V2ResponseFactory().createUsersResponse(
        http.get(conf.v2Configuration.baseURL + "users/" + userId + "/following", params.toTypedArray(), auth, this),
        conf
    )
}

/**
 * Returns a list of users who are followers of the specified user ID.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/users/follows/api-reference/get-users-id-followers"
 */
@Throws(TwitterException::class)
fun Twitter.getFollowerUsers(
    userId: Long,
    expansions: String? = "pinned_tweet_id",
    maxResults: Int? = null,
    paginationToken: String? = null,
    tweetFields: String? = null,
    userFields: String? = null
): UsersResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    val params = ArrayList<HttpParameter>()

    V2Util.addHttpParamIfNotNull(params, "expansions", expansions)
    V2Util.addHttpParamIfNotNull(params, "max_results", maxResults)
    V2Util.addHttpParamIfNotNull(params, "pagination_token", paginationToken)
    V2Util.addHttpParamIfNotNull(params, "tweet.fields", tweetFields)
    V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

    return V2ResponseFactory().createUsersResponse(
        http.get(conf.v2Configuration.baseURL + "users/" + userId + "/followers", params.toTypedArray(), auth, this),
        conf
    )
}

/**
 * Allows a user ID to follow another user.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/users/follows/api-reference/post-users-source_user_id-following"
 */
@Throws(TwitterException::class)
fun Twitter.followUser(
    sourceUserId: Long,
    targetUserId: Long
): FollowResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    val json = JSONObject()
    json.put("target_user_id", targetUserId.toString())

    return V2ResponseFactory().createFollowResponse(
        http.post(
            conf.v2Configuration.baseURL + "users/" + sourceUserId + "/following",
            arrayOf(HttpParameter(json)),
            auth,
            this
        ),
        conf
    )
}

/**
 * Allows a user ID to unfollow another user.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/users/follows/api-reference/delete-users-source_id-following"
 */
@Throws(TwitterException::class)
fun Twitter.unfollowUser(
    sourceUserId: Long,
    targetUserId: Long
): BooleanResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    return V2ResponseFactory().createBooleanResponse(
        http.delete(
            conf.v2Configuration.baseURL + "users/" + sourceUserId + "/following/" + targetUserId,
            emptyArray(),
            auth,
            this
        ),
        conf,
        "following"
    )
}
