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
    // getFollowingUsers example
    //--------------------------------------------------
    val twitterDesignId = 87532773L
    twitter.getFollowingUsers(
        twitterDesignId,
        tweetFields = "attachments,author_id,context_annotations,conversation_id,created_at,entities,geo,id,in_reply_to_user_id,lang,public_metrics,possibly_sensitive,referenced_tweets,reply_settings,source,text,withheld",
        userFields = "created_at,description,entities,id,location,name,pinned_tweet_id,profile_image_url,protected,public_metrics,url,username,verified,withheld"
    ).let {
        println(it)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(it))
        println(json.toString(3))
    }

    println("minimum query")
    println("=============")
    twitter.getFollowingUsers(
        twitterDesignId
    ).let {
        println(it)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(it))
        println(json.toString(3))
    }

    println("pagination")
    println("=============")
    val page1 = twitter.getFollowingUsers(
        twitterDesignId,
        maxResults = 10
    )
    val json1 = JSONObject(TwitterObjectFactory.getRawJSON(page1))

    println("page1:")
    println(json1.toString(3))
    println(page1.users.map { "(${it.id}, ${it.name}, ${it.username})" })

    val page2 = twitter.getFollowingUsers(
        twitterDesignId,
        maxResults = 10,
        paginationToken = page1.meta?.nextToken
    )
    val json2 = JSONObject(TwitterObjectFactory.getRawJSON(page2))

    println("page2:")
    println(json2.toString(3))
    println(page2.users.map { "(${it.id}, ${it.name}, ${it.username})" })

}
