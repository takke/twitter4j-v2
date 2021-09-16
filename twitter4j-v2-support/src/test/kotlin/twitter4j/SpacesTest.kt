package twitter4j

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SpacesTest {

    private val twitter by lazy { V2TestUtil.createOAuth2TwitterInstance() }
    private val myId by lazy { twitter.verifyCredentials().id }

    @Test
    fun searchSpaces_minimum() {

        // Scheduled
        twitter.searchSpaces("a", Space.State.Scheduled).let { res ->
            println(res)

            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
            println(json.toString(3))

            assertThat(res.spaces.size).isGreaterThanOrEqualTo(0)
            assertThat(res.meta?.resultCount).isGreaterThanOrEqualTo(0)
            if (res.spaces.isNotEmpty()) {
                res.spaces.forEach {
                    assertThat(it.id.length).isGreaterThan(0)
                    assertThat(it.state).isEqualTo(Space.State.Scheduled)
                }
            }

            assertThat(res.usersMap.size).isEqualTo(0)
        }

        // Live
        twitter.searchSpaces("a", Space.State.Live).let { res ->
            println(res)

            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
            println(json.toString(3))

            assertThat(res.spaces.size).isGreaterThanOrEqualTo(0)
            assertThat(res.meta?.resultCount).isGreaterThanOrEqualTo(0)
            if (res.spaces.isNotEmpty()) {
                res.spaces.forEach {
                    assertThat(it.id.length).isGreaterThan(0)
                    assertThat(it.state).isNotEqualTo(Space.State.Scheduled)
                }
            }

            assertThat(res.usersMap.size).isEqualTo(0)
        }
    }

    @Test
    fun searchSpaces_full_result() {

        val res = twitter.searchSpaces(
            "a",
            Space.State.Live,
            maxResults = 10,
            expansions = "invited_user_ids,speaker_ids,creator_id,host_ids",
            spaceFields = "host_ids,created_at,creator_id,id,lang,invited_user_ids,participant_count,speaker_ids,started_at,state,title,updated_at,scheduled_start,is_ticketed",
            userFields = "created_at,description,entities,id,location,name,pinned_tweet_id,profile_image_url,protected,public_metrics,url,username,verified,withheld"
        )
        println(res)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
        println(json.toString(3))

        assertThat(res.spaces.size).isGreaterThanOrEqualTo(0)
        assertThat(res.meta?.resultCount).isGreaterThanOrEqualTo(0)
        if (res.spaces.isNotEmpty()) {
            res.spaces.forEach {
                assertThat(it.id.length).isGreaterThan(0)
                assertThat(it.state).isNotEqualTo(Space.State.Scheduled)

                // expansions
//                assertThat(it.hostIds).isNotEmpty         // ない場合もある
//                assertThat(it.speakerIds).isNotEmpty      // ない場合もある
//                assertThat(it.invitedUserIds).isNotNull   // ない場合もある
                assertThat(it.creatorId).isNotNull

                // space.fields
                assertThat(it.createdAt).isNotNull
                assertThat(it.lang).isNotNull
                assertThat(it.participantCount).isGreaterThanOrEqualTo(0)
                assertThat(it.startedAt).isNotNull
//                assertThat(it.title).isNotNull            // ない場合もある
                assertThat(it.updatedAt).isNotNull
//                assertThat(it.scheduledStart).isNull()    // スケジュールで開始の場合は存在する
                assertThat(it.isTicketed).isNotNull
            }
        }

        assertThat(res.usersMap.size).isGreaterThan(0)
    }

}