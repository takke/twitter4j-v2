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

    // get my latest tweet
    val myUser = twitter.verifyCredentials()
    val tweets = twitter.getUserTimeline(myUser.id, Paging(1, 10))

    // find non-rt tweet
    val lastTweet = tweets.firstOrNull { !it.isRetweet }
    if (lastTweet == null) {
        println("tweet not found")
        return
    }

    println("${lastTweet.id}: ${lastTweet.text}")


    //--------------------------------------------------
    // hideReplies example
    //--------------------------------------------------
    println("hideReplies: hidden=true")
    println("========================")
    twitter.v2.hideReplies(
        lastTweet.id,
        true
    ).let {
        println("result: " + it.result)
        println(it)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(it))
        println(json.toString(3))
    }

    println("waiting...")
    Thread.sleep(3000)

    println("hideReplies: hidden=false")
    println("=========================")
    twitter.v2.hideReplies(
        lastTweet.id,
        false
    ).let {
        println("result: " + it.result)
        println(it)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(it))
        println(json.toString(3))
    }
}
