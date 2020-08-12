package twitter4j

/**
 * Returns a variety of information about one or more Users specified by the requested IDs or usernames.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/users/lookup/api-reference/get-users"
 */
@Throws(TwitterException::class)
fun Twitter.getUsers(vararg ids: Long,
                      mediaFields: String? = "duration_ms,height,media_key,preview_image_url,type,url,width",
                      placeFields: String? = "contained_within,country,country_code,full_name,geo,id,name,place_type",
                      pollFields: String? = "duration_minutes,end_datetime,id,options,voting_status",
                      tweetFields: String? = "attachments,author_id,context_annotations,created_at,entities,geo,id,in_reply_to_user_id,lang,possibly_sensitive,referenced_tweets,source,public_metrics,text,withheld",
                      userFields: String? = "created_at,description,entities,id,location,name,pinned_tweet_id,profile_image_url,protected,public_metrics,url,username,verified,withheld",
                      expansions: String = "pinned_tweet_id"
): UsersResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    val params = arrayListOf(
            HttpParameter("ids", ids.joinToString(",")),
            HttpParameter("expansions", expansions)
    )

    if (mediaFields != null) {
        params.add(HttpParameter("media.fields", mediaFields))
    }

    if (placeFields != null) {
        params.add(HttpParameter("place.fields", placeFields))
    }

    if (pollFields != null) {
        params.add(HttpParameter("poll.fields", pollFields))
    }

    if (tweetFields != null) {
        params.add(HttpParameter("tweet.fields", tweetFields))
    }

    if (userFields != null) {
        params.add(HttpParameter("user.fields", userFields))
    }

    return UsersFactory().createUsersResponse(
            http.get(conf.v2Configuration.baseURL + "users", params.toTypedArray(), auth, this),
            conf
    )
}
