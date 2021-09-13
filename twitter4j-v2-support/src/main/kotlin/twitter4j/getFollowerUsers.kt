package twitter4j

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

    if (expansions != null) {
        params.add(HttpParameter("expansions", expansions))
    }

    if (maxResults != null) {
        params.add(HttpParameter("max_results", maxResults))
    }

    if (paginationToken != null) {
        params.add(HttpParameter("pagination_token", paginationToken))
    }

    if (tweetFields != null) {
        params.add(HttpParameter("tweet.fields", tweetFields))
    }

    if (userFields != null) {
        params.add(HttpParameter("user.fields", userFields))
    }

    return UsersFactory().createUsersResponse(
        http.get(conf.v2Configuration.baseURL + "users/" + userId + "/followers", params.toTypedArray(), auth, this),
        conf
    )
}
