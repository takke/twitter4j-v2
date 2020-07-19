package twitter4j

import java.util.*

data class Tweet(val id: Long, val text: String) {

    var source: String? = null

    var lang: String? = null

    var createdAt: Date? = null

    var publicMetrics: PublicMetrics? = null
}
