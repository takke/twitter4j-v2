package twitter4j

import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test

class BookmarksTest {

    private val twitter2 by lazy { V2TestUtil.createOAuth2TwitterInstance() }
    private val myId by lazy {
        val me = twitter2.v2.getMe().users[0]
//        println(me)
        me.id
    }

    @Test
    @Ignore("expiration time of oauth2.accessToken is too short")
    fun getBookmarks_full() {

        val res = twitter2.v2.getBookmarks(
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

    @Test
    @Ignore("expiration time of oauth2.accessToken is too short")
    fun add_get_delete_get() {

        // https://twitter.com/TwitterDevJP/status/1470916207130079239
        val tweetId = 1470916207130079239L

        println("addBookmark")
        println("===========")
        val bookmarked = twitter2.v2.addBookmark(myId, tweetId)
        assertThat(bookmarked.result).isTrue

        // delay
        println("delaying...")
        Thread.sleep(1000)

        println("getBookmarks")
        println("============")
        val res = twitter2.v2.getBookmarks(
            myId,
            maxResults = 3,
        )
        println("recent bookmarks: " + res.tweets.map { it.id }.joinToString(","))
        assertThat(res.tweets[0].id).isEqualTo(tweetId)

        println("deleteBookmark")
        println("==============")
        val deleteResult = twitter2.v2.deleteBookmark(myId, tweetId)
        println(deleteResult)
        assertThat(deleteResult.result).isFalse
    }

}