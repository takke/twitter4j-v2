package twitter4j

import twitter4j.auth.OAuth2Authorization
import twitter4j.conf.Configuration

class OAuth2TokenProvider(
    private val conf: Configuration
) {
    private val http = HttpClientFactory.getInstance(conf.httpClientConfiguration)

    data class Result(
        val tokenType: String,
        val expiresIn: Long,
        val accessToken: String,
        val scope: String,
        val refreshToken: String,
    )

    fun createAuthorizeUrl(clientId: String, redirectUri: String, scopes: Array<String>, challenge: String = "challenge"): String {

        val scope = scopes.joinToString("%20")

        return "https://twitter.com/i/oauth2/authorize?response_type=code&" +
                "client_id=$clientId&" +
                "redirect_uri=$redirectUri&" +
                "scope=$scope&" +
                "state=state&" +
                "code_challenge=$challenge&" +
                "code_challenge_method=plain"
    }

    fun getAccessToken(clientId: String, redirectUri: String, code: String, challenge: String = "challenge"): Result? {

        // wrap
        val oauth2 = OAuth2Authorization(conf)

        val params = arrayOf(
            HttpParameter("code", code),
            HttpParameter("grant_type", "authorization_code"),
            HttpParameter("client_id", clientId),
            HttpParameter("redirect_uri", redirectUri),
            HttpParameter("code_verifier", challenge),
        )

        val res: HttpResponse = http.post("https://api.twitter.com/2/oauth2/token", params, oauth2, null)
        if (res.getStatusCode() != 200) {
            throw TwitterException("Obtaining OAuth 2 Bearer Token failed.", res)
        }

        val json = res.asJSONObject() ?: return null
        return Result(
            json.getString("token_type"),
            json.getLong("expires_in"),
            json.getString("access_token"),
            json.getString("scope"),
            json.getString("refresh_token"),
        )
    }

    fun refreshToken(clientId: String, refreshToken: String): Result? {

        // wrap
        val oauth2 = OAuth2Authorization(conf)

        val params = arrayOf(
            HttpParameter("grant_type", "refresh_token"),
            HttpParameter("refresh_token", refreshToken),
            HttpParameter("client_id", clientId),
        )

        val res: HttpResponse = http.post("https://api.twitter.com/2/oauth2/token", params, oauth2, null)
        if (res.getStatusCode() != 200) {
            throw TwitterException("Obtaining OAuth 2 Bearer Token failed.", res)
        }

        val json = res.asJSONObject() ?: return null
        return Result(
            json.getString("token_type"),
            json.getLong("expires_in"),
            json.getString("access_token"),
            json.getString("scope"),
            json.getString("refresh_token"),
        )
    }

}