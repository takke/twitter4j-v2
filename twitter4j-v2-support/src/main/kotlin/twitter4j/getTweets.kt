package twitter4j

/**
 * Returns a variety of information about the Tweet specified by the requested ID or list of IDs.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/labs/tweets-and-users/api-reference/get-tweets"
 */
@Throws(TwitterException::class)
fun Twitter.getTweets(vararg tweetId: Long,
                      mediaFields: String? = "duration_ms,height,media_key,preview_image_url,public_metrics,type,url,width",
                      placeFields: String? = "contained_within,country,country_code,full_name,geo,id,name,place_type",
                      pollFields: String? = "duration_minutes,end_datetime,id,options,voting_status",
                      tweetFields: String? = "attachments,author_id,context_annotations,created_at,entities,geo,id,in_reply_to_user_id,lang,possibly_sensitive,referenced_tweets,source,public_metrics,text,withheld",
                      userFields: String? = "created_at,description,entities,id,location,name,pinned_tweet_id,profile_image_url,protected,public_metrics,url,username,verified,withheld",
                      expansions: String = "attachments.poll_ids,attachments.media_keys,author_id,entities.mentions.username,geo.place_id,in_reply_to_user_id,referenced_tweets.id,referenced_tweets.id.author_id"
): TweetsResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    val params = arrayListOf(
            HttpParameter("ids", tweetId.joinToString(",")),
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

    return TweetsFactory().createTweetsResponse(
            http.get(conf.v2Configuration.baseURL + "tweets", params.toTypedArray(), auth, this),
            conf
    )
}
