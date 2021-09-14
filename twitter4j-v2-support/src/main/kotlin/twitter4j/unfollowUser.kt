package twitter4j

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
