package twitter4j

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
