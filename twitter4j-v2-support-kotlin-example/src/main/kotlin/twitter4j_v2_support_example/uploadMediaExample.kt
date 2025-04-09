package twitter4j_v2_support_example

import twitter4j.*
import twitter4j.conf.ConfigurationBuilder
import java.io.File

fun main() {
    val conf = ConfigurationBuilder()
        .setJSONStoreEnabled(true)
        .build()

    val twitter = TwitterFactory(conf).instance

    twitter.v2.uploadMedia(File("test-upload.jpg")).let {
        twitter.v2.createTweet(
            mediaIds = arrayOf(it.mediaId),
            text = "Hello, World! From X API v2!"
        )
    }
}
