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


    //--------------------------------------------------
    // getLikingUsers example
    //--------------------------------------------------
    val statusId = 1435645603065778176L
    twitter.v2.getLikingUsers(
        statusId,
        expansions = "pinned_tweet_id",
        tweetFields = V2DefaultFields.tweetFields,
        userFields = V2DefaultFields.userFields
    ).let {
        println(it)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(it))
        println(json.toString(3))
    }

    println("minimum query")
    println("=============")
    twitter.v2.getLikingUsers(
        statusId
    ).let {
        println(it)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(it))
        println(json.toString(3))
    }

}
