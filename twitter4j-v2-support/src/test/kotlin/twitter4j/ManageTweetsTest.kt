package twitter4j

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ManageTweetsTest {

    private val twitter by lazy { V2TestUtil.createTwitterInstance() }
    private val myId by lazy { twitter.verifyCredentials().id }

    @Test
    fun create_then_delete_tweet() {

        println("createTweet")
        println("===========")
        val tweetId = twitter.createTweet(text = "test: " + System.currentTimeMillis()).let { res ->
            println(res)
            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
            println(json.toString(3))

            assertThat(res.id).isGreaterThan(1)
            assertThat(res.text).isNotBlank

            res.id
        }

        // delay
        println("delaying...")
        Thread.sleep(1000)

        // reply
        println("createTweet(reply)")
        println("==================")
        val tweetId2 = twitter.createTweet(text = "reply: " + System.currentTimeMillis(), inReplyToTweetId = tweetId).let { res ->
            println(res)
            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
            println(json.toString(3))

            assertThat(res.id).isGreaterThan(1)
            assertThat(res.text).isNotBlank

            res.id
        }

        // delay
        println("delaying...")
        Thread.sleep(1000)

        println("deleteTweet")
        println("===========")

        twitter.deleteTweet(tweetId).let { res ->
            println(res)
            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
            println(json.toString(3))

            assertThat(res.result).isEqualTo(true)
        }

        // delay
        println("delaying...")
        Thread.sleep(500)

        twitter.deleteTweet(tweetId2).let { res ->
            println(res)
            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
            println(json.toString(3))

            assertThat(res.result).isEqualTo(true)
        }
    }

    // poll の動作確認ができたのでコメントアウトしておく
//    @Test
//    fun poll() {
//        println("createTweet with polls")
//        println("======================")
//        val tweetId = twitter.createTweet(
//            text = "poll test: " + System.currentTimeMillis(),
//            pollDurationMinutes = 60,
//            pollOptions = arrayOf("option1", "option2")
//        ).let { res ->
//
//            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
//            println(json.toString(3))
//
//            assertThat(res.id).isGreaterThan(1)
//            assertThat(res.text).isNotBlank
//
//            res.id
//        }
//
//        // delay
//        println("delaying...")
//        Thread.sleep(2000)
//
//        println("deleteTweet")
//        println("===========")
//
//        twitter.deleteTweet(tweetId).let { res ->
//            println(res)
//            assertThat(res.result).isEqualTo(true)
//        }
//    }

}