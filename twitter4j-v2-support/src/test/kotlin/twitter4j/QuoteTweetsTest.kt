package twitter4j

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class QuoteTweetsTest {

    private val twitter by lazy { V2TestUtil.createTwitterInstance() }

    @Test
    fun getQuoteTweets_full() {

        // https://twitter.com/TwitterJP/status/1506789702019416069
        val res = twitter.getQuoteTweets(
            1506789702019416069L,
            V2DefaultFields.expansions,
            10,
            V2DefaultFields.mediaFields,
            null,
            V2DefaultFields.placeFields,
            V2DefaultFields.pollFields,
            V2DefaultFields.tweetFields,
            V2DefaultFields.userFields
        )

        // no dump
        println("res.tweets.size: " + res.tweets.size)
        println("res.meta.resultCount: " + res.meta?.resultCount)

        assertThat(res.meta?.resultCount).isGreaterThan(0)
    }

}