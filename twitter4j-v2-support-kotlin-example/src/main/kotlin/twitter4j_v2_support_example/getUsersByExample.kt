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

    //--------------------------------------------------
    // getUsersBy example
    //--------------------------------------------------
    twitter.v2.getUsersBy("takke").let {
        println(it)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(it))
        println(json.toString(3))
    }

    twitter.v2.getUsersBy("takke", "twitterdesign").let {
        println(it)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(it))
        println(json.toString(3))
    }

    println("pinned_tweet")
    println("============")
    twitter.v2.getUsersBy(
        "twitterdesign",
        tweetFields = null,
        userFields = "pinned_tweet_id",
        expansions = "pinned_tweet_id"
    ).let {
        println(it)

        println(it.tweetsMap)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(it))
        println(json.toString(3))
    }

    println("pinned_tweet_id only")
    println("====================")
    twitter.v2.getUsersBy(
        "twitterdesign",
        tweetFields = null,
        userFields = "pinned_tweet_id",
        expansions = ""
    ).let {
        println(it)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(it))
        println(json.toString(3))
    }

}
