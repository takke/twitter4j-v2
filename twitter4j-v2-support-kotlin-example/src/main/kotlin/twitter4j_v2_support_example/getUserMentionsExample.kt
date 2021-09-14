package twitter4j_v2_support_example

import twitter4j.JSONObject
import twitter4j.TwitterFactory
import twitter4j.TwitterObjectFactory
import twitter4j.conf.ConfigurationBuilder
import twitter4j.getUserMentions


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
    // getUserMentions example
    //--------------------------------------------------
    val userId = twitter.verifyCredentials().id     // me
    println("simple")
    println("======")
    twitter.getUserMentions(userId, maxResults = 5).let {
        println(it)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(it))
        println(json.toString(3))
    }

}
