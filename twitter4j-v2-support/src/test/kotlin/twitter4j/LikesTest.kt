package twitter4j

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class LikesTest {

    private val twitter by lazy { V2TestUtil.createTwitterInstance() }
    private val myId by lazy { twitter.verifyCredentials().id }

    @Test
    fun getLikingUsers_simple() {

        // https://twitter.com/TwitterDev/status/1430984356139470849
        val tweetId = 1430984356139470849L
        val res = twitter.getLikingUsers(tweetId)
        println(res)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
        println(json.toString(3))

        assertThat(res.meta?.resultCount).isGreaterThan(0)
        assertThat(res.users[0].id).isGreaterThan(0)
        assertThat(res.users[0].name.length).isGreaterThan(0)
        assertThat(res.users[0].screenName.length).isGreaterThan(0)
    }

    @Test
    fun getLikedTweets_simple() {

        val res = twitter.getLikedTweets(myId)

        // no dump
        println("res.tweets.size: " + res.tweets.size)
        println("res.meta.resultCount: " + res.meta?.resultCount)

        assertThat(res.meta?.resultCount).isGreaterThan(0)
    }

    @Test
    fun like_then_unlike() {

        // https://twitter.com/TwitterDev/status/1430984356139470849
        val tweetId = 1430984356139470849L

        // like
        twitter.likeTweet(myId, tweetId).let { res ->
            println(res)
            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
            println(json.toString(3))

            assertThat(res.result).isEqualTo(true)
        }

        // delay
        Thread.sleep(2000)

        // unlike
        twitter.unlikeTweet(myId, tweetId).let { res ->
            println(res)
            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
            println(json.toString(3))

            assertThat(res.result).isEqualTo(false)
        }
    }

}