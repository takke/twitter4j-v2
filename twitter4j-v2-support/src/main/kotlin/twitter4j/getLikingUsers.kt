package twitter4j

/**
 * Allows you to get information about a Tweet’s liking users.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/likes/api-reference/get-tweets-id-liking_users"
 */
@Throws(TwitterException::class)
fun Twitter.getLikingUsers(
    tweetId: Long,
    expansions: String? = null,
    tweetFields: String? = null,
    userFields: String? = null,
): UsersResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    val params = ArrayList<HttpParameter>()

    if (expansions != null) {
        params.add(HttpParameter("expansions", expansions))
    }

    if (tweetFields != null) {
        params.add(HttpParameter("tweet.fields", tweetFields))
    }

    if (userFields != null) {
        params.add(HttpParameter("user.fields", userFields))
    }

    return V2ResponseFactory().createUsersResponse(
        http.get(
            conf.v2Configuration.baseURL + "tweets/" + tweetId + "/liking_users",
            params.toTypedArray(),
            auth,
            this
        ),
        conf
    )
}
