package twitter4j

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
