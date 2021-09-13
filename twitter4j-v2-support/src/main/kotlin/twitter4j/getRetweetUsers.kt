package twitter4j

/**
 * Allows you to get information about who has Retweeted a Tweet.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/retweets/api-reference/get-tweets-id-retweeted_by"
 */
@Throws(TwitterException::class)
fun Twitter.getRetweetUsers(
    userId: Long,
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

    return UsersFactory().createUsersResponse(
        http.get(
            conf.v2Configuration.baseURL + "tweets/" + userId + "/retweeted_by",
            params.toTypedArray(),
            auth,
            this
        ),
        conf
    )
}
