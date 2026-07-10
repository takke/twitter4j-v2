package twitter4j

import twitter4j.conf.ConfigurationBuilder

object V2TestUtil {

    fun createTwitterInstance(): Twitter {
        val conf = ConfigurationBuilder()
            .setJSONStoreEnabled(true)
            .build()
        return TwitterFactory(conf).instance
    }

    fun createOAuth2TwitterInstance(): Twitter {
        val conf = ConfigurationBuilder()
            .setOAuthConsumerKey("dummy")
            .setOAuthConsumerSecret("dummy")
            .setOAuth2TokenType("dummy")
            .setApplicationOnlyAuthEnabled(true)
            .setJSONStoreEnabled(true)
            .build()

        // set "oauth2.accessToken" property on twitter4j.properties

        return TwitterFactory(conf).instance
    }
}