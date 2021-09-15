package twitter4j

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.TimeZone

class GetUserTweetsTest {

    @Test
    fun simpleById() {

        val twitter = V2TestUtil.createTwitterInstance()
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
    fun startEndTimeById() {

        val twitter = V2TestUtil.createTwitterInstance()
        val userId = 12L       // @jack
        println("account id[$userId]")
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        sdf.timeZone = TimeZone.getTimeZone("GMT")
        val res = twitter.getUserTweets(
            userId, maxResults = 50,
            exclude = "retweets",
            startTime = sdf.parse("2019-11-01T00:00:00Z"),
            endTime = sdf.parse("2019-11-03T00:00:00Z"),
        )
        println(res)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
        println(json.toString(3))

        // meta
        assertThat(res.meta?.resultCount).isEqualTo(2)
        assertThat(res.meta?.oldestId).isEqualTo(1190321758915555328)
        assertThat(res.meta?.newestId).isEqualTo(1190339489593380864)
        assertThat(res.meta?.previousToken).isNull()
        assertThat(res.meta?.nextToken).isNull()
    }

//    @Test
//    fun startEndTimeByUsername() {
//
//        val twitter = V2TestUtil.createTwitterInstance()
//        val res = twitter.getUserTweets(
//            "twitterdev", maxResults = 50,
//            exclude = "retweets",
//            startTime = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse("2019-11-01T00:00:00Z"),
//            endTime = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse("2019-11-03T00:00:00Z"),
//        )
//        println(res)
//
//        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
//        println(json.toString(3))
//
//        // meta
//        assertThat(res.meta?.resultCount).isEqualTo(3)
//        assertThat(res.meta?.oldestId).isEqualTo(1189976124517781504L)
//        assertThat(res.meta?.newestId).isEqualTo(1190339489593380864)
//        assertThat(res.meta?.previousToken).isNull()
//        assertThat(res.meta?.nextToken).isNull()
//    }

}