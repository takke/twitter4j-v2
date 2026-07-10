package twitter4j

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class FollowsTest {

    private val twitter by lazy { V2TestUtil.createTwitterInstance() }
    private val myId by lazy { twitter.verifyCredentials().id }

    @Test
    fun getFollowingUsers_simple() {

        val res = twitter.v2.getFollowingUsers(myId, expansions = null)
        println(res.users.size)

        assertThat(res.meta?.resultCount).isGreaterThan(0)
        assertThat(res.users[0].id).isGreaterThan(0)
        assertThat(res.users[0].name.length).isGreaterThan(0)
        assertThat(res.users[0].screenName.length).isGreaterThan(0)
    }

    @Test
    fun getFollowerUsers_simple() {

        val res = twitter.v2.getFollowerUsers(myId, expansions = null)
        println(res.users.size)

        assertThat(res.meta?.resultCount).isGreaterThan(0)
        assertThat(res.users[0].id).isGreaterThan(0)
        assertThat(res.users[0].name.length).isGreaterThan(0)
        assertThat(res.users[0].screenName.length).isGreaterThan(0)
    }

    @Test
    fun follow_then_unfollow() {

        val targetUserId = 14276577L // sample2

        println("followUser")
        println("==========")
        twitter.v2.followUser(myId, targetUserId).let { res ->
            println(res)
            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
            println(json.toString(3))

            assertThat(res.result.following).isEqualTo(true)
            assertThat(res.result.pendingFollow).isEqualTo(false)
        }

        // delay
        Thread.sleep(2000)

        println("unfollowUser")
        println("============")
        twitter.v2.unfollowUser(myId, targetUserId).let { res ->
            println(res)
            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
            println(json.toString(3))

            assertThat(res.result).isEqualTo(false)
        }
    }

}