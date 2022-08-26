package twitter4j

object V2DefaultFields {

    // media.fields
    const val mediaFields = "duration_ms,height,media_key,preview_image_url,public_metrics,type,url,width,alt_text,variants"

    // place.fields
    const val placeFields = "contained_within,country,country_code,full_name,geo,id,name,place_type"

    // poll.fields
    const val pollFields = "duration_minutes,end_datetime,id,options,voting_status"

    // tweet.fields without private-metrics
    const val tweetFields =
        "attachments,author_id,context_annotations,conversation_id,created_at,entities,geo,id,in_reply_to_user_id,lang,public_metrics,possibly_sensitive,referenced_tweets,reply_settings,source,text,withheld"
    const val tweetFieldsFull =
        "attachments,author_id,context_annotations,conversation_id,created_at,entities,geo,id,in_reply_to_user_id,lang,non_public_metrics,public_metrics,organic_metrics,promoted_metrics,possibly_sensitive,referenced_tweets,reply_settings,source,text,withheld"

    // user.fields
    const val userFields =
        "created_at,description,entities,id,location,name,pinned_tweet_id,profile_image_url,protected,public_metrics,url,username,verified,withheld"

    // expansions for "GET /2/tweets"
    const val expansions =
        "attachments.poll_ids,attachments.media_keys,author_id,entities.mentions.username,geo.place_id,in_reply_to_user_id,referenced_tweets.id,referenced_tweets.id.author_id"
}
