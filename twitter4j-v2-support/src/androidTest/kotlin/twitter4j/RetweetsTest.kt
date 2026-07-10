package twitter4j

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class RetweetsTest {

    private val twitter by lazy { V2TestUtil.createTwitterInstance() }
    private val myId by lazy { twitter.verifyCredentials().id }

    @Test
    fun getRetweetUsers_simple() {

        // https://twitter.com/TwitterDev/status/1430984356139470849
        val tweetId = 1430984356139470849L
        val res = twitter.v2.getRetweetUsers(tweetId)
        println(res)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
        println(json.toString(3))

        assertThat(res.meta?.resultCount).isGreaterThan(0)
        assertThat(res.users[0].id).isGreaterThan(0)
        assertThat(res.users[0].name.length).isGreaterThan(0)
        assertThat(res.users[0].screenName.length).isGreaterThan(0)
    }

    @Test
    fun retweet_then_unretweet() {

        // https://twitter.com/TwitterDev/status/1430984356139470849
        val tweetId = 1430984356139470849L

        // retweet
        twitter.v2.retweet(myId, tweetId).let { res ->
            println(res)
            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
            println(json.toString(3))

            assertThat(res.result).isEqualTo(true)
        }

        // delay
        Thread.sleep(2000)

        // unretweet
        twitter.v2.unretweet(myId, tweetId).let { res ->
            println(res)
            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
            println(json.toString(3))

            assertThat(res.result).isEqualTo(false)
        }
    }

}