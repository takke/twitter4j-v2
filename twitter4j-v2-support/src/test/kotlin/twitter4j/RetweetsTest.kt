package twitter4j

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class RetweetsTest {

    @Test
    fun getRetweetUsers_simple() {

        val twitter = V2TestUtil.createTwitterInstance()

        // https://twitter.com/TwitterDev/status/1430984356139470849
        val tweetId = 1430984356139470849L
        val res = twitter.getRetweetUsers(tweetId)
        println(res)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
        println(json.toString(3))

        assertThat(res.meta?.resultCount).isGreaterThan(0)
        assertThat(res.users[0].id).isGreaterThan(0)
        assertThat(res.users[0].name.length).isGreaterThan(0)
        assertThat(res.users[0].username.length).isGreaterThan(0)
    }

    @Test
    fun retweet_then_unretweet() {

        val twitter = V2TestUtil.createTwitterInstance()
        val myId = twitter.verifyCredentials().id

        // https://twitter.com/TwitterDev/status/1430984356139470849
        val tweetId = 1430984356139470849L

        // retweet
        twitter.retweet(myId, tweetId).let { res ->
            println(res)
            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
            println(json.toString(3))

            assertThat(res.result).isEqualTo(true)
        }

        // delay
        Thread.sleep(2000)

        // unretweet
        twitter.unretweet(myId, tweetId).let { res ->
            println(res)
            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
            println(json.toString(3))

            assertThat(res.result).isEqualTo(false)
        }
    }

}