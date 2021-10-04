package twitter4j

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.*

class ListsTest {

    private val twitter by lazy { V2TestUtil.createTwitterInstance() }
    private val myId by lazy { twitter.verifyCredentials().id }

//    @Test
//    fun create_then_delete() {
//
//        println("createList")
//        println("==========")
//        val listId = twitter.createList("test list " + Date().time).let { res ->
//            println(res)
//            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
//            println(json.toString(3))
//
//            assertThat(res.id).isGreaterThan(0)
//            assertThat(res.name).isNotNull
//
//            res.id
//        }
//
//        // delay
//        println("delaying...")
//        Thread.sleep(2000)
//
//        println("deleteList")
//        println("==========")
//        twitter.deleteList(listId).let { res ->
//            println(res)
//            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
//            println(json.toString(3))
//
//            assertThat(res.result).isEqualTo(true)
//        }
//    }

    @Test
    fun create_add_member_then_delete() {

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

        val targetUserId = 14276577L // sample2

        println("addListMember")
        println("=============")
        twitter.addListMember(listId, targetUserId).let { res ->
            println(res)
            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
            println(json.toString(3))

            assertThat(res.result).isEqualTo(true)
        }

        // delay
        println("delaying...")
        Thread.sleep(2000)

        println("deleteListMember")
        println("================")
        twitter.deleteListMember(listId, targetUserId).let { res ->
            println(res)
            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
            println(json.toString(3))

            assertThat(res.result).isEqualTo(false)
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

    @Test
    fun follow_then_unfollow() {

        // "Developers" of @Twitter
        val targetListId = 214727905L

        println("followList")
        println("==========")
        twitter.followList(myId, targetListId).let { res ->
            println(res)
            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
            println(json.toString(3))

            assertThat(res.result).isEqualTo(true)
        }

        // delay
        println("delaying...")
        Thread.sleep(2000)

        println("unfollowList")
        println("============")
        twitter.unfollowList(myId, targetListId).let { res ->
            println(res)
            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
            println(json.toString(3))

            assertThat(res.result).isEqualTo(false)
        }
    }
}