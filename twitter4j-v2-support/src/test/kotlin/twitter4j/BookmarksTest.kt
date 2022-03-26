package twitter4j

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class BookmarksTest {

    private val twitter1 by lazy { V2TestUtil.createTwitterInstance() }
    private val myId by lazy { twitter1.verifyCredentials().id }

    private val twitter2 by lazy { V2TestUtil.createOAuth2TwitterInstance() }

    @Test
    fun getBookmarks_full() {

        val res = twitter2.getBookmarks(
            myId,
            V2DefaultFields.expansions,
            10,
            V2DefaultFields.mediaFields,
            null,
            V2DefaultFields.placeFields,
            V2DefaultFields.pollFields,
            V2DefaultFields.tweetFields,
            V2DefaultFields.userFields
        )
        println(res)

        // no dump
        println("res.tweets.size: " + res.tweets.size)
        println("res.meta.resultCount: " + res.meta?.resultCount)

        assertThat(res.meta?.resultCount).isGreaterThanOrEqualTo(0)
    }

}