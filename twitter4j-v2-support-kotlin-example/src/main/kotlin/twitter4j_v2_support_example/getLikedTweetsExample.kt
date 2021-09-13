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
    // getLikedTweets example
    //--------------------------------------------------
    val userId = 8379212L       // @takke
//    val userId = 2244994945L    // @TwitterDev
//    val userId = 87532773L      // @TwitterDesign
    twitter.getLikedTweets(
        userId,
        tweetFields = "id,text,created_at"
    ).let {
        println(it)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(it))
        println(json.toString(3))
    }

    println("pagination")
    println("=============")
    val page1 = twitter.getLikedTweets(
        userId,
        maxResults = 10
    )
    val json1 = JSONObject(TwitterObjectFactory.getRawJSON(page1))

    println("page1:")
    println(json1.toString(3))
    println(page1.tweets.map { "{${it.id}, ${it.text}}" })

    val page2 = twitter.getLikedTweets(
        userId,
        maxResults = 10,
        paginationToken = page1.meta?.nextToken
    )
    val json2 = JSONObject(TwitterObjectFactory.getRawJSON(page2))

    println("page2:")
    println(json2.toString(3))
    println(page2.tweets.map { "{${it.id}, ${it.text}}" })
}
