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
    // likeTweet example
    //--------------------------------------------------
    val tweetId = 1437346169106042886L
    println("likeTweet")
    println("=========")
    twitter.likeTweet(
        myId,
        tweetId
    ).let {
        println("result: " + it.result)
        println(it)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(it))
        println(json.toString(3))
    }

    //--------------------------------------------------
    // deleteLikeTweet example
    //--------------------------------------------------
    println("deleteLikeTweet")
    println("===============")
    twitter.deleteLikeTweet(
        myId,
        tweetId
    ).let {
        println("result: " + it.result)
        println(it)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(it))
        println(json.toString(3))
    }
}
