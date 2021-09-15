package twitter4j

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class CountsTest {

    private val twitter by lazy { V2TestUtil.createOAuth2TwitterInstance() }
    private val myId by lazy { twitter.verifyCredentials().id }

    @Test
    fun countRecent() {

        val res = twitter.countRecent("hello")
        println(res)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
        println(json.toString(3))

        assertThat(res.counts.size).isGreaterThan(0)
        assertThat(res.totalTweetCount).isGreaterThan(0)
        res.counts[0].let {
            assertThat(it.tweetCount).isGreaterThan(0)
        }
    }

    @Test
    fun startEndTimeById_hour() {

        val startTime = Date(Date().time - 7 * 86400 * 1000)
        val endTime = Date(Date().time - 6 * 86400 * 1000)
        val query = "ファイザー OR モデルナ"
        val res = twitter.countRecent(
            query,
            startTime = startTime,
            endTime = endTime,
        )

        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm")
        println("query: \"$query\", ${sdf.format(startTime)} to ${sdf.format(endTime)}")
//        println(res.counts.size)

        res.counts.forEach {
            println("${sdf.format(it.start)}-${sdf.format(it.end)} : ${it.tweetCount}")
        }

//        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
//        println(json.toString(3))

        assertThat(res.counts.size).isBetween(24, 25)
    }

    @Test
    fun startEndTimeById_minute() {

        val startTime = Date(Date().time - 7 * 86400 * 1000)
        val endTime = Date(Date().time - (7 * 86400 - 3600) * 1000)
        val query = "ワクチン"
        val res = twitter.countRecent(
            query,
            granularity = "minute",
            startTime = startTime,
            endTime = endTime,
        )

        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm")
        println("query: \"$query\", ${sdf.format(startTime)} to ${sdf.format(endTime)}")
//        println(res.counts.size)

        res.counts.forEach {
            println("${sdf.format(it.start)}-${sdf.format(it.end)} : ${it.tweetCount}")
        }

//        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
//        println(json.toString(3))

        assertThat(res.counts.size).isBetween(60, 61)
    }

}