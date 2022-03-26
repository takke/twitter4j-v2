package twitter4j

import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test

class SpacesLookupTest {

    private val twitter by lazy { V2TestUtil.createOAuth2TwitterInstance() }
    private val myId by lazy { twitter.verifyCredentials().id }

    @Test
    @Ignore("expiration time of oauth2.accessToken is too short")
    fun getSpaces_minimum() {

        //--------------------------------------------------
        // just a search spaces to get space ids
        //--------------------------------------------------
        val spaces = twitter.searchSpaces("a", Space.State.Scheduled, spaceFields = "creator_id")
//        val json0 = JSONObject(TwitterObjectFactory.getRawJSON(spaces))
//        println(json0.toString(3))

        val spaceIds = spaces.spaces.map { it.id }.toTypedArray()
        println("target space ids:")
        println(spaceIds.joinToString(","))
        if (spaceIds.isEmpty()) {
            println("skip this test cause we can't get any spaces")
            return
        }

        //--------------------------------------------------
        // call, test
        //--------------------------------------------------
        println("getSpaces")
        println("=====================")
        val res = twitter.getSpaces(*spaceIds)
        println(res)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
        println(json.toString(3))

        assertThat(res.spaces.size).isGreaterThanOrEqualTo(0)
        assertThat(res.meta?.resultCount).isNull()
        if (res.spaces.isNotEmpty()) {
            res.spaces.forEach {
                assertThat(it.id.length).isGreaterThan(0)
                assertThat(it.state).isEqualTo(Space.State.Scheduled)
            }
        }

        assertThat(res.usersMap.size).isEqualTo(0)
    }

    @Test
    @Ignore("expiration time of oauth2.accessToken is too short")
    fun getSpacesByCreatorIds_minimum() {

        //--------------------------------------------------
        // just a search spaces to get creator_ids
        //--------------------------------------------------
        val spaces = twitter.searchSpaces("a", Space.State.Scheduled, spaceFields = "creator_id")
//        val json0 = JSONObject(TwitterObjectFactory.getRawJSON(spaces))
//        println(json0.toString(3))

        val creatorIds = spaces.spaces.map { it.creatorId!! }.toLongArray()
        println("target user ids:")
        println(creatorIds.joinToString(","))
        if (creatorIds.isEmpty()) {
            println("skip this test cause we can't get any spaces")
            return
        }

        //--------------------------------------------------
        // call, test
        //--------------------------------------------------
        println("getSpacesByCreatorIds")
        println("=====================")
        val res = twitter.getSpacesByCreatorIds(*creatorIds)
        println(res)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
        println(json.toString(3))

        assertThat(res.spaces.size).isGreaterThanOrEqualTo(0)
        assertThat(res.meta?.resultCount).isGreaterThanOrEqualTo(0)
        if (res.spaces.isNotEmpty()) {
            res.spaces.forEach {
                assertThat(it.id.length).isGreaterThan(0)
                assertThat(it.state).isIn(Space.State.Scheduled, Space.State.Live)
            }
        }

        assertThat(res.usersMap.size).isEqualTo(0)
    }

}