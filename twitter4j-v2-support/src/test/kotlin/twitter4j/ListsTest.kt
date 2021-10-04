package twitter4j

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.*

class ListsTest {

    private val twitter by lazy { V2TestUtil.createTwitterInstance() }

    @Test
    fun create_then_delete() {

        println("createList")
        println("==========")
        val listId = twitter.createList("test list " + Date().time).let { res ->
            println(res)
            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
            println(json.toString(3))

            assertThat(res.id).isGreaterThan(0)
            assertThat(res.name).isNotNull

            res.id
        }

        // delay
        println("delaying...")
        Thread.sleep(2000)

        println("deleteList")
        println("==========")
        twitter.deleteList(listId).let { res ->
            println(res)
            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
            println(json.toString(3))

            assertThat(res.result).isEqualTo(true)
        }
    }

}