package twitter4j

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.TimeZone

class TimelinesTest {

    private val twitter by lazy { V2TestUtil.createTwitterInstance() }
    private val myId by lazy { twitter.verifyCredentials().id }

    @Test
    fun getUserMentions_simpleById() {

        println("account id[$myId]")
        val res = twitter.getUserMentions(myId, maxResults = 5)
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
    fun getUserTweets_simpleById() {

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
    fun getUserTweets_startEndTimeById() {

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

    @Test
    fun getReverseChronologicalTimeline_simple() {

        val res = twitter.getReverseChronologicalTimeline(myId, maxResults = 100)
        // CI のログに残らないように出力を抑制する
//        println(res)
        println(res.meta)

//        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
//        println(json.toString(3))

        // meta
        assertThat(res.meta?.resultCount).isGreaterThanOrEqualTo(1)
        assertThat(res.meta?.previousToken).isNull()
        assertThat(res.meta?.nextToken).isNotNull
        assertThat(res.meta?.oldestId).isNotNull
        assertThat(res.meta?.newestId).isNotNull

        assertThat(res.tweets.size).isGreaterThanOrEqualTo(1)
        res.tweets[0].let {
            assertThat(it.text.length).isGreaterThan(0)
        }
    }

    @Test
    fun getReverseChronologicalTimeline_full() {

        val res = twitter.getReverseChronologicalTimeline(
            myId,
            maxResults = 100,
            mediaFields = V2DefaultFields.mediaFields,
            placeFields = V2DefaultFields.placeFields,
            pollFields = V2DefaultFields.pollFields,
            tweetFields = V2DefaultFields.tweetFields,
            userFields = V2DefaultFields.userFields,
            expansions = V2DefaultFields.expansions,
        )
        // CI のログに残らないように出力を抑制する
//        println(res)
        println(res.meta)

//        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
//        println(json.toString(3))

        // meta
        assertThat(res.meta?.resultCount).isGreaterThanOrEqualTo(1)
        assertThat(res.meta?.previousToken).isNull()
        assertThat(res.meta?.nextToken).isNotNull
        assertThat(res.meta?.oldestId).isNotNull
        assertThat(res.meta?.newestId).isNotNull

        assertThat(res.tweets.size).isGreaterThanOrEqualTo(1)
        res.tweets[0].let {
            assertThat(it.text.length).isGreaterThan(0)
        }
    }

//    @Test
//    fun getUserTweets_startEndTimeByUsername() {
//
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