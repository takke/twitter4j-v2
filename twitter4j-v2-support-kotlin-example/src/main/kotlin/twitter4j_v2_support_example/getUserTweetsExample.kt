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
    // getUserTweets example
    //--------------------------------------------------
    val userId = 8379212L       // @takke
//    val userId = 2244994945L    // @TwitterDev
//    val userId = 87532773L      // @TwitterDesign
    println("simple")
    println("======")
    twitter.v2.getUserTweets(userId, maxResults = 5).let {
        println(it)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(it))
        println(json.toString(3))
    }

}
