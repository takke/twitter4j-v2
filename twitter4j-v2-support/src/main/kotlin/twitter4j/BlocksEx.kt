package twitter4j

/**
 * Returns a list of users who are blocked by the specified user ID.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/users/blocks/api-reference/get-users-blocking"
 */
@Throws(TwitterException::class)
fun Twitter.getBlockingUsers(
    userId: Long,
    expansions: String? = "pinned_tweet_id",
    maxResults: Int? = null,
    paginationToken: PaginationToken? = null,
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
        http.get(conf.v2Configuration.baseURL + "users/" + userId + "/blocking", params.toTypedArray(), auth, this),
        conf
    )
}

/**
 * Causes the user (in the path) to block the target user. The user (in the path) must match the user context authorizing the request.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/users/blocks/api-reference/post-users-user_id-blocking"
 */
@Throws(TwitterException::class)
fun Twitter.blockUser(
    sourceUserId: Long,
    targetUserId: Long
): BooleanResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    val json = JSONObject()
    json.put("target_user_id", targetUserId.toString())

    return V2ResponseFactory().createBooleanResponse(
        http.post(
            conf.v2Configuration.baseURL + "users/" + sourceUserId + "/blocking",
            arrayOf(HttpParameter(json)),
            auth,
            this
        ),
        conf,
        "blocking"
    )
}

/**
 * Allows a user or authenticated user ID to unblock another user.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/users/blocks/api-reference/delete-users-user_id-blocking"
 */
@Throws(TwitterException::class)
fun Twitter.unblockUser(
    sourceUserId: Long,
    targetUserId: Long
): BooleanResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    return V2ResponseFactory().createBooleanResponse(
        http.delete(
            conf.v2Configuration.baseURL + "users/" + sourceUserId + "/blocking/" + targetUserId,
            emptyArray(),
            auth,
            this
        ),
        conf,
        "blocking"
    )
}
