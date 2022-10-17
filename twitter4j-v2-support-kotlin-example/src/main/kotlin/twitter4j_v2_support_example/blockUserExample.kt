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
    // blockUser example
    //--------------------------------------------------
    val targetUserId = 14276577L // sample2
    println("blockUser")
    println("=========")
    twitter.v2.blockUser(
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
    // unblockUser example
    //--------------------------------------------------
    println("unblockUser")
    println("===========")
    twitter.v2.unblockUser(
        myId,
        targetUserId
    ).let {
        println("result: " + it.result)
        println(it)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(it))
        println(json.toString(3))
    }
}
