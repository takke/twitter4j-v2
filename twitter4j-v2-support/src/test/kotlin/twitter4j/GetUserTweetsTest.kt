package twitter4j

import org.assertj.core.api.Assertions.*
import org.junit.Test
import twitter4j.conf.ConfigurationBuilder
import java.util.*

class GetUserTweetsTest {

    @Test
    fun simple() {

        val twitter = createTwitterInstance()
        val userId = 8379212L       // @takke
        println("account id[$userId]")
        val res = twitter.getUserTweets(userId, maxResults = 5)
        println(res)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
        println(json.toString(3))

        // meta
        assertThat(res.meta?.resultCount).isEqualTo(5)
        assertThat(res.meta?.previousToken).isNull()
        assertThat(res.meta?.nextToken).isNotNull
        assertThat(res.meta?.oldestId).isNotNull
        assertThat(res.meta?.newestId).isNotNull

        assertThat(res.tweets.size).isEqualTo(5)
        res.tweets[0].let {
            assertThat(it.text.length).isGreaterThan(0)
        }
    }

    @Test
    fun startEndTime() {

        val twitter = createTwitterInstance()
        val userId = 8379212L       // @takke
        println("account id[$userId]")
        val res = twitter.getUserTweets(
            userId, maxResults = 50,
            endTime = Date(Date().time - 3 * 86400 * 1000),
            startTime = Date(Date().time - 5 * 86400 * 1000),
        )
        println(res)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
        println(json.toString(3))

        // meta
        assertThat(res.meta?.resultCount).isGreaterThan(0)
        assertThat(res.meta?.previousToken).isNull()
        assertThat(res.meta?.oldestId).isNotNull
        assertThat(res.meta?.newestId).isNotNull

        res.tweets[0].let {
            assertThat(it.text.length).isGreaterThan(0)
        }
    }

    private fun createTwitterInstance(): Twitter {
        val conf = ConfigurationBuilder()
            .setJSONStoreEnabled(true)
            .build()
        return TwitterFactory(conf).instance
//        return TwitterFactory.getSingleton()
    }
}