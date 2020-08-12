package twitter4j_v2_support_example

import twitter4j.JSONObject
import twitter4j.TwitterFactory
import twitter4j.TwitterObjectFactory
import twitter4j.conf.ConfigurationBuilder
import twitter4j.getUsers


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
    twitter.getUsers(twitterDesignId).let {
        println(it)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(it))
        println(json.toString(3))
    }

    println("minimum query")
    println("=============")
    twitter.getUsers(twitterDesignId,
            mediaFields = null,
            placeFields = null,
            pollFields = null,
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
    twitter.getUsers(twitterDesignId,
            mediaFields = null,
            placeFields = null,
            pollFields = null,
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
    twitter.getUsers(twitterDesignId,
            mediaFields = null,
            placeFields = null,
            pollFields = null,
            tweetFields = null,
            userFields = "pinned_tweet_id",
            expansions = ""
    ).let {
        println(it)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(it))
        println(json.toString(3))
    }

}
