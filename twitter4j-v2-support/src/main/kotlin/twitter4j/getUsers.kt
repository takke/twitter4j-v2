package twitter4j

/**
 * Returns a variety of information about one or more users specified by the requested IDs.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/users/lookup/api-reference/get-users"
 */
@Throws(TwitterException::class)
fun Twitter.getUsers(
    vararg ids: Long,
    tweetFields: String? = V2DefaultFields.tweetFields,
    userFields: String? = V2DefaultFields.userFields,
    expansions: String = "pinned_tweet_id"
): UsersResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    val params = arrayListOf(
        HttpParameter("ids", ids.joinToString(",")),
        HttpParameter("expansions", expansions)
    )

    if (tweetFields != null) {
        params.add(HttpParameter("tweet.fields", tweetFields))
    }

    if (userFields != null) {
        params.add(HttpParameter("user.fields", userFields))
    }

    return V2ResponseFactory().createUsersResponse(
        http.get(conf.v2Configuration.baseURL + "users", params.toTypedArray(), auth, this),
        conf
    )
}
