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
    fun get_list() {

        // Official Twitter Accounts
        val listId = 84839422L
        val res = twitter.getList(listId)
        println(res)
        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
        println(json.toString(3))
        assertThat(res.id).isEqualTo(listId)

        // optional fields are null
        assertThat(res.ownerId).isNull()
        assertThat(res.createdAt).isNull()
        assertThat(res.followerCount).isNull()
        assertThat(res.memberCount).isNull()
        assertThat(res.isPrivate).isNull()
        assertThat(res.description).isNull()

        // not user
        assertThat(res.usersMap.size).isEqualTo(0)
    }

    @Test
    fun get_list_expansion_fields() {

        // Official Twitter Accounts
        val listId = 84839422L
        twitter.getList(
            listId,
            expansions = "owner_id",
            listFields = "created_at,follower_count,member_count,private,description,owner_id",
            userFields = "created_at,description"
        ).let { res ->
            println(res)
            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
            println(json.toString(3))
            assertThat(res.id).isEqualTo(listId)

            // optional fields
            val ownerId = 783214L
            assertThat(res.ownerId).isEqualTo(ownerId)
            assertThat(res.createdAt).isNotNull
            assertThat(res.followerCount).isNotNull
            assertThat(res.memberCount).isNotNull
            assertThat(res.isPrivate).isNotNull
            assertThat(res.description).isNotNull

            // user
            val user = res.usersMap[ownerId]!!
            assertThat(user.id).isEqualTo(ownerId)
            assertThat(user.name).isEqualTo("Twitter")
            assertThat(user.username).isEqualTo("Twitter")
            assertThat(user.createdAt).isNotNull
            assertThat(user.description).isNotNull
        }
    }

    @Test
    fun create_add_member_then_delete() {

        println("createList")
        println("==========")
        val name = "test list " + Date().time
        val listId = twitter.createList(name).let { res ->
            println(res)
            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
            println(json.toString(3))

            assertThat(res.id).isGreaterThan(0)
            assertThat(res.name).isNotNull

            res.id
        }

        println("getList")
        println("=======")
        val listInfo = twitter.getList(listId)
        assertThat(listInfo.id).isEqualTo(listId)
        assertThat(listInfo.name).isEqualTo(name)

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

    @Test
    fun pin_then_unpin() {

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

        println("pinList")
        println("=======")
        twitter.pinList(myId, targetListId).let { res ->
            println(res)
            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
            println(json.toString(3))

            assertThat(res.result).isEqualTo(true)
        }

        // delay
        println("delaying...")
        Thread.sleep(2000)

        println("unpinList")
        println("=========")
        twitter.unpinList(myId, targetListId).let { res ->
            println(res)
            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
            println(json.toString(3))

            assertThat(res.result).isEqualTo(false)
        }
    }

}