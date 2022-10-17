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
    // getFollowerUsers example
    //--------------------------------------------------
    val twitterDesignId = 87532773L
    twitter.v2.getFollowerUsers(
        twitterDesignId,
        tweetFields = V2DefaultFields.tweetFields,
        userFields = V2DefaultFields.userFields
    ).let {
        println(it)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(it))
        println(json.toString(3))
    }

    println("minimum query")
    println("=============")
    twitter.v2.getFollowerUsers(
        twitterDesignId
    ).let {
        println(it)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(it))
        println(json.toString(3))
    }

    println("pagination")
    println("=============")
    val page1 = twitter.v2.getFollowerUsers(
        twitterDesignId,
        maxResults = 10
    )
    val json1 = JSONObject(TwitterObjectFactory.getRawJSON(page1))

    println("page1:")
    println(json1.toString(3))
    println(page1.users.map { "(${it.id}, ${it.name}, ${it.screenName})" })

    val page2 = twitter.v2.getFollowerUsers(
        twitterDesignId,
        maxResults = 10,
        paginationToken = page1.meta?.nextToken
    )
    val json2 = JSONObject(TwitterObjectFactory.getRawJSON(page2))

    println("page2:")
    println(json2.toString(3))
    println(page2.users.map { "(${it.id}, ${it.name}, ${it.screenName})" })

}
