package twitter4j

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class UsersResponseTest {

    @Test
    fun minimumTweet() {

        val res = UsersResponse(
            JSONObject(
                "{\n" +
                        "   \"data\": [\n" +
                        "      {\n" +
                        "         \"id\": \"87532773\",\n" +
                        "         \"username\": \"TwitterDesign\",\n" +
                        "         \"name\": \"Twitter Design\"\n" +
                        "      }\n" +
                        "   ]\n" +
                        "}"
            )
        )
        assertThat(res.users.size).isEqualTo(1)
        assertThat(res.users[0].id).isEqualTo(87532773)
        assertThat(res.users[0].screenName).isEqualTo("TwitterDesign")
        assertThat(res.users[0].name).isEqualTo("Twitter Design")
    }
}