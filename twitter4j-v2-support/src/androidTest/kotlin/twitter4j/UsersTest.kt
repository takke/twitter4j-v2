package twitter4j

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class UsersTest {

    private val twitter by lazy { V2TestUtil.createTwitterInstance() }

    @Test
    fun usersBy() {

        val res = twitter.v2.getUsersBy(
            "twitterdev",
            tweetFields = V2DefaultFields.tweetFields,
            userFields = V2DefaultFields.userFields,
            expansions = "pinned_tweet_id"
        )

        println(res)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
        println(json.toString(3))

        val user = res.users[0]
        assertThat(user.id).isEqualTo(2244994945L)
        assertThat(user.screenName).isEqualTo("TwitterDev")
    }

}