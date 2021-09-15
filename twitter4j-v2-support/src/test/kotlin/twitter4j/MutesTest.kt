package twitter4j

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class MutesTest {

    private val twitter by lazy { V2TestUtil.createTwitterInstance() }
    private val myId by lazy { twitter.verifyCredentials().id }

    @Test
    fun mute_then_unmute() {

        val targetUserId = 14276577L // sample2

        println("muteUser")
        println("========")
        twitter.muteUser(myId, targetUserId).let { res ->
            println(res)
            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
            println(json.toString(3))

            assertThat(res.result).isEqualTo(true)
        }

        // delay
        Thread.sleep(2000)

        println("unmuteUser")
        println("============")
        twitter.unmuteUser(myId, targetUserId).let { res ->
            println(res)
            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
            println(json.toString(3))

            assertThat(res.result).isEqualTo(false)
        }
    }

}