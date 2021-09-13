package twitter4j_v2_support_example

import twitter4j.*
import twitter4j.conf.ConfigurationBuilder


fun main(@Suppress("UNUSED_PARAMETER") args: Array<String>) {

    //--------------------------------------------------
    // prepare twitter instance
    //--------------------------------------------------
    val conf = ConfigurationBuilder()
        .setJSONStoreEnabled(true)
        .build()

    val twitter = TwitterFactory(conf).instance
//    val twitter = TwitterFactory.getSingleton()

    // get my id
    val myUser = twitter.verifyCredentials()

    //--------------------------------------------------
    // getBlockingUsers example
    //--------------------------------------------------
    val myId = myUser.id
    twitter.getBlockingUsers(
        myId,
        tweetFields = "attachments,author_id,context_annotations,conversation_id,created_at,entities,geo,id,in_reply_to_user_id,lang,non_public_metrics,public_metrics,organic_metrics,promoted_metrics,possibly_sensitive,referenced_tweets,reply_settings,source,text,withheld",
        userFields = "created_at,description,entities,id,location,name,pinned_tweet_id,profile_image_url,protected,public_metrics,url,username,verified,withheld"
    ).let {
        println(it)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(it))
        println(json.toString(3))
    }

    println("minimum query")
    println("=============")
    twitter.getBlockingUsers(
        myId
    ).let {
        println(it)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(it))
        println(json.toString(3))
    }

}
