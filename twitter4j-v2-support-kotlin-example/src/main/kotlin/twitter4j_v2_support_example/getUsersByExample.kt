package twitter4j_v2_support_example

import twitter4j.JSONObject
import twitter4j.TwitterFactory
import twitter4j.TwitterObjectFactory
import twitter4j.conf.ConfigurationBuilder
import twitter4j.getUsersBy


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
    twitter.getUsersBy("takke").let {
        println(it)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(it))
        println(json.toString(3))
    }

    twitter.getUsersBy("takke", "twitterdesign").let {
        println(it)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(it))
        println(json.toString(3))
    }

    println("pinned_tweet")
    println("============")
    twitter.getUsersBy("twitterdesign",
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
    twitter.getUsersBy("twitterdesign",
            tweetFields = null,
            userFields = "pinned_tweet_id",
            expansions = ""
    ).let {
        println(it)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(it))
        println(json.toString(3))
    }

}
