package twitter4j

/**
 * Returns a variety of information about one or more users specified by their usernames.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/users/lookup/api-reference/get-users-by"
 */
@Throws(TwitterException::class)
fun Twitter.getUsersBy(vararg usernames: String,
                       tweetFields: String? = "attachments,author_id,context_annotations,conversation_id,created_at,entities,geo,id,in_reply_to_user_id,lang,public_metrics,possibly_sensitive,referenced_tweets,source,text,withheld",
                       userFields: String? = "created_at,description,entities,id,location,name,pinned_tweet_id,profile_image_url,protected,public_metrics,url,username,verified,withheld",
                       expansions: String = "pinned_tweet_id"
): UsersResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    val params = arrayListOf(
            HttpParameter("usernames", usernames.joinToString(",")),
            HttpParameter("expansions", expansions)
    )

    if (tweetFields != null) {
        params.add(HttpParameter("tweet.fields", tweetFields))
    }

    if (userFields != null) {
        params.add(HttpParameter("user.fields", userFields))
    }

    return UsersFactory().createUsersResponse(
            http.get(conf.v2Configuration.baseURL + "users/by", params.toTypedArray(), auth, this),
            conf
    )
}
