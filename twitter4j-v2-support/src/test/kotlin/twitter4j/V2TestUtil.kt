package twitter4j

import twitter4j.conf.ConfigurationBuilder

object V2TestUtil {

    fun createTwitterInstance(): Twitter {
        val conf = ConfigurationBuilder()
            .setJSONStoreEnabled(true)
            .build()
        return TwitterFactory(conf).instance
//        return TwitterFactory.getSingleton()
    }
}