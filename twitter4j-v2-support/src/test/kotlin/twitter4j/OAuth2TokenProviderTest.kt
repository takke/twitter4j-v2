package twitter4j

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import twitter4j.conf.ConfigurationBuilder
import twitter4j.conf.V2CustomConfiguration

class OAuth2TokenProviderTest {

    private val challenge = "abc123def456"

    @Test
    fun createAuthorizeUrl() {

        val conf = V2CustomConfiguration()

        val scopes = arrayOf(
            "tweet.read",
            "tweet.write",
            "tweet.moderate.write",
            "users.read",
            "follows.read",
            "follows.write",
            "offline.access",
            "space.read",
            "mute.read",
            "mute.write",
            "like.read",
            "like.write",
            "list.read",
            "list.write",
            "block.read",
            "block.write",
            "bookmark.read",
            "bookmark.write",
        )

        val tokenProvider = OAuth2TokenProvider(ConfigurationBuilder().build())
        val url = tokenProvider.createAuthorizeUrl(conf.clientId, conf.redirectUri, scopes, challenge)

        println("url: $url")

        assertThat(url).isEqualTo(
            "https://twitter.com/i/oauth2/authorize?response_type=code&client_id=${conf.clientId}&redirect_uri=${conf.redirectUri}&" +
                    "scope=tweet.read%20tweet.write%20tweet.moderate.write%20users.read%20follows.read%20follows.write%20offline.access%20space.read%20mute.read%20mute.write%20like.read%20like.write%20list.read%20list.write%20block.read%20block.write%20bookmark.read%20bookmark.write&" +
                    "state=state&code_challenge=${challenge}&code_challenge_method=plain"
        )
    }

    @Test
    fun getAccessToken() {

        // replace code from url that redirected
        val code = "xxxxx"

        val conf = V2CustomConfiguration()

        val tokenProvider = OAuth2TokenProvider(ConfigurationBuilder().build())
        val result = tokenProvider.getAccessToken(conf.clientId, conf.redirectUri, code, challenge)
        println(result)

        assertThat(result).isNotNull
    }

    @Test
    fun refreshToken() {

        // replace below from refreshToken value of the result of getAccessToken
        val refreshToken = "xxxxx"

        val conf = V2CustomConfiguration()

        val tokenProvider = OAuth2TokenProvider(ConfigurationBuilder().build())
        val result = tokenProvider.refreshToken(conf.clientId, refreshToken)
        println(result)

        assertThat(result).isNotNull
    }

}
