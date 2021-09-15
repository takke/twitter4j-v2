package twitter4j

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.*

class SearchTest {

    private val twitter by lazy { V2TestUtil.createTwitterInstance() }
    private val myId by lazy { twitter.verifyCredentials().id }

    @Test
    fun searchRecent() {

        val res = twitter.searchRecent("hello", maxResults = 10)
        println(res)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
        println(json.toString(3))

        // meta
        assertThat(res.meta?.resultCount).isEqualTo(10)
        assertThat(res.meta?.previousToken).isNull()
        assertThat(res.meta?.nextToken).isNotNull
        assertThat(res.meta?.oldestId).isNotNull
        assertThat(res.meta?.newestId).isNotNull

        assertThat(res.tweets.size).isEqualTo(10)
        res.tweets[0].let {
            assertThat(it.text.length).isGreaterThan(0)
        }
    }

    @Test
    fun startEndTimeById() {

        val res = twitter.searchRecent(
            "hello", maxResults = 50,
            startTime = Date(Date().time - 7 * 86400 * 1000),
            endTime = Date(Date().time - 6 * 86400 * 1000),
        )
        println("query: \"hello\", 7days ago:")
        println(res.tweets.size)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
        println(json.toString(3))

        // meta
        assertThat(res.meta?.resultCount).isGreaterThan(0)
        assertThat(res.meta?.oldestId).isNotNull
        assertThat(res.meta?.newestId).isNotNull
        assertThat(res.meta?.previousToken).isNull()
        assertThat(res.meta?.nextToken).isNotNull
    }

}