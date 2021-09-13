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
    expansions: String? = "pinned_tweet_id",
    tweetFields: String? = "attachments,author_id,context_annotations,conversation_id,created_at,entities,geo,id,in_reply_to_user_id,lang,non_public_metrics,public_metrics,organic_metrics,promoted_metrics,possibly_sensitive,referenced_tweets,reply_settings,source,text,withheld",
    userFields: String? = "created_at,description,entities,id,location,name,pinned_tweet_id,profile_image_url,protected,public_metrics,url,username,verified,withheld",
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
        http.get(conf.v2Configuration.baseURL + "tweets/" + userId + "/retweeted_by", params.toTypedArray(), auth, this),
        conf
    )
}
