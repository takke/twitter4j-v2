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
    val myId = myUser.id


    //--------------------------------------------------
    // muteUser example
    //--------------------------------------------------
    val targetUserId = 14276577L // sample2
    println("muteUser")
    println("========")
    twitter.muteUser(
        myId,
        targetUserId
    ).let {
        println("result: " + it.result)
        println(it)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(it))
        println(json.toString(3))
    }

    println("waiting...")
    Thread.sleep(3000)

    //--------------------------------------------------
    // unmuteUser example
    //--------------------------------------------------
    println("unmuteUser")
    println("==========")
    twitter.unmuteUser(
        myId,
        targetUserId
    ).let {
        println("result: " + it.result)
        println(it)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(it))
        println(json.toString(3))
    }
}
