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

    // set another baseURL for Twitter Labs API
//    conf.v2Configuration.baseURL = "https://api.twitter.com/labs/2/"

    val twitter = TwitterFactory(conf).instance
//    val twitter = TwitterFactory.getSingleton()

    //--------------------------------------------------
    // getUsers example
    //--------------------------------------------------
//    val takkeId = 8379212L
//    val twitterDevId = 2244994945L
    val twitterDesignId = 87532773L
    twitter.v2.getUsers(twitterDesignId).let {
        println(it)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(it))
        println(json.toString(3))
    }

    println("minimum query")
    println("=============")
    twitter.v2.getUsers(
        twitterDesignId,
        tweetFields = null,
        userFields = null,
        expansions = ""
    ).let {
        println(it)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(it))
        println(json.toString(3))
    }

    println("pinned_tweet")
    println("============")
    twitter.v2.getUsers(
        twitterDesignId,
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
    twitter.v2.getUsers(
        twitterDesignId,
        tweetFields = null,
        userFields = "pinned_tweet_id",
        expansions = ""
    ).let {
        println(it)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(it))
        println(json.toString(3))
    }

}
