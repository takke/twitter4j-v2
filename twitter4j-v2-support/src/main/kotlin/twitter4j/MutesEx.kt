package twitter4j

/**
 * Returns a list of users who are muted by the specified user ID.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/users/mutes/api-reference/get-users-muting"
 */
@Throws(TwitterException::class)
fun Twitter.getMutingUsers(
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
        http.get(conf.v2Configuration.baseURL + "users/" + userId + "/muting", params.toTypedArray(), auth, this),
        conf
    )
}

/**
 * Allows an authenticated user ID to mute the target user.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/users/mutes/api-reference/post-users-user_id-muting"
 */
@Throws(TwitterException::class)
fun Twitter.muteUser(
    sourceUserId: Long,
    targetUserId: Long
): BooleanResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    val json = JSONObject()
    json.put("target_user_id", targetUserId.toString())

    return V2ResponseFactory().createBooleanResponse(
        http.post(
            conf.v2Configuration.baseURL + "users/" + sourceUserId + "/muting",
            arrayOf(HttpParameter(json)),
            auth,
            this
        ),
        conf,
        "muting"
    )
}

/**
 * Allows an authenticated user ID to unmute the target user.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/users/mutes/api-reference/delete-users-user_id-muting"
 */
@Throws(TwitterException::class)
fun Twitter.unmuteUser(
    sourceUserId: Long,
    targetUserId: Long
): BooleanResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    return V2ResponseFactory().createBooleanResponse(
        http.delete(
            conf.v2Configuration.baseURL + "users/" + sourceUserId + "/muting/" + targetUserId,
            emptyArray(),
            auth,
            this
        ),
        conf,
        "muting"
    )
}
