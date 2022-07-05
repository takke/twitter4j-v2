package twitter4j

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class BlocksTest {

    private val twitter by lazy { V2TestUtil.createTwitterInstance() }
    private val myId by lazy { twitter.verifyCredentials().id }

    @Test
    fun getBlockingUsers_simple() {

        val res = twitter.getBlockingUsers(myId, expansions = null)
        println(res.users.size)

        assertThat(res.meta?.resultCount).isGreaterThan(0)
        assertThat(res.users[0].id).isGreaterThan(0)
        assertThat(res.users[0].name.length).isGreaterThan(0)
        assertThat(res.users[0].screenName.length).isGreaterThan(0)
    }

    @Test
    fun block_then_unblock() {

        val targetUserId = 14276577L // sample2

        println("blockUser")
        println("=========")
        twitter.blockUser(myId, targetUserId).let { res ->
            println(res)
            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
            println(json.toString(3))

            assertThat(res.result).isEqualTo(true)
        }

        // delay
        Thread.sleep(2000)

        println("unblockUser")
        println("===========")
        twitter.unblockUser(myId, targetUserId).let { res ->
            println(res)
            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
            println(json.toString(3))

            assertThat(res.result).isEqualTo(false)
        }
    }

}