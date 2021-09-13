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
    // getRetweetUsers example
    //--------------------------------------------------
    val statusId = 1435645603065778176L
    twitter.getRetweetUsers(statusId).let {
        println(it)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(it))
        println(json.toString(3))
    }

    println("minimum query")
    println("=============")
    twitter.getRetweetUsers(
        statusId,
        tweetFields = null,
        userFields = null,
        expansions = null
    ).let {
        println(it)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(it))
        println(json.toString(3))
    }

}
