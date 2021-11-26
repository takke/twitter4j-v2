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
    fun getList_simple() {

        // Official Twitter Accounts
        val listId = 84839422L
        val res = twitter.getList(listId)
        println(res)
        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
        println(json.toString(3))

        assertThat(res.lists.size).isEqualTo(1)
        val list1 = res.lists.first()
        assertThat(list1.id).isEqualTo(listId)

        // optional fields are null
        assertThat(list1.ownerId).isNull()
        assertThat(list1.createdAt).isNull()
        assertThat(list1.followerCount).isNull()
        assertThat(list1.memberCount).isNull()
        assertThat(list1.isPrivate).isNull()
        assertThat(list1.description).isNull()

        // no users
        assertThat(res.usersMap.size).isEqualTo(0)
    }

    @Test
    fun getList_expansion_fields() {

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

            assertThat(res.lists.size).isEqualTo(1)
            val list1 = res.lists.first()
            assertThat(list1.id).isEqualTo(listId)

            // optional fields
            val ownerId = 783214L
            assertThat(list1.ownerId).isEqualTo(ownerId)
            assertThat(list1.createdAt).isNotNull
            assertThat(list1.followerCount).isNotNull
            assertThat(list1.memberCount).isNotNull
            assertThat(list1.isPrivate).isNotNull
            assertThat(list1.description).isNotNull

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
    fun getOwnedLists_simple() {

        val ownerId = 783214L
        twitter.getOwnedLists(
            ownerId
        ).let { res ->
            println(res)
            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
            println(json.toString(3))

            // actual count = 11 at 2021/11/25
            assertThat(res.lists.size).isGreaterThanOrEqualTo(2)

            val list1 = res.lists.first()

            // optional fields are null
            assertThat(list1.ownerId).isNull()
            assertThat(list1.createdAt).isNull()
            assertThat(list1.followerCount).isNull()
            assertThat(list1.memberCount).isNull()
            assertThat(list1.isPrivate).isNull()
            assertThat(list1.description).isNull()

            // no users
            assertThat(res.usersMap.size).isEqualTo(0)
        }
    }

    @Test
    fun getOwnedLists_expansion_fields() {

        val ownerId = 783214L
        twitter.getOwnedLists(
            ownerId,
            expansions = "owner_id",
            listFields = "created_at,follower_count,member_count,private,description,owner_id",
            userFields = "created_at,description"
        ).let { res ->
            println(res)
            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
            println(json.toString(3))

            // actual count = 11 at 2021/11/25
            assertThat(res.lists.size).isGreaterThanOrEqualTo(2)

            val list1 = res.lists.first()

            // optional fields
            assertThat(list1.ownerId).isEqualTo(ownerId)
            assertThat(list1.createdAt).isNotNull
            assertThat(list1.followerCount).isNotNull
            assertThat(list1.memberCount).isNotNull
            assertThat(list1.isPrivate).isNotNull
            assertThat(list1.description).isNotNull

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
    fun getListTweets_simple() {

        // Official Twitter Accounts
        val listId = 84839422L
        val res = twitter.getListTweets(listId)
        println(res)
        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
        println(json.toString(3))

        assertThat(res.tweets.size).isGreaterThanOrEqualTo(1)
    }

    @Test
    fun getListMembers_simple() {

        // Official Twitter Accounts
        val listId = 84839422L
        val res = twitter.getListMembers(listId)
        println(res)
        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
        println(json.toString(3))

        assertThat(res.users.size).isGreaterThanOrEqualTo(1)
    }

    @Test
    fun getListMemberships_simple() {

        val userId = 783214L
        val res = twitter.getListMemberships(userId)
        println(res)
        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
        println(json.toString(3))

        assertThat(res.lists.size).isGreaterThanOrEqualTo(1)
    }

    @Test
    fun getListFollowers_simple() {

        // Official Twitter Accounts
        val listId = 84839422L
        val res = twitter.getListFollowers(listId)
        println(res)
        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
        println(json.toString(3))

        assertThat(res.users.size).isGreaterThanOrEqualTo(1)
    }

    @Test
    fun getFollowedLists_simple() {

        val userId = 87532773L      // @TwitterDesign
        val res = twitter.getFollowedLists(userId)
        println(res)
        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
        println(json.toString(3))

        assertThat(res.lists.size).isGreaterThanOrEqualTo(1)
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

            assertThat(res.lists.size).isEqualTo(1)
            val list1 = res.lists.first()

            assertThat(list1.id).isGreaterThan(0)
            assertThat(list1.name).isNotNull

            list1.id
        }

        println("getList")
        println("=======")
        val listInfo = twitter.getList(listId)
        assertThat(listInfo.lists[0].id).isEqualTo(listId)
        assertThat(listInfo.lists[0].name).isEqualTo(name)

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