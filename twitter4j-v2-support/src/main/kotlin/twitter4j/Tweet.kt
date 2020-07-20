package twitter4j

import java.util.*

data class Tweet(val id: Long, val text: String) {

    data class PublicMetrics(
            val retweetCount: Int,
            val replyCount: Int,
            val likeCount: Int,
            val quoteCount: Int
    ) {

        constructor(json: JSONObject) : this(
                retweetCount = ParseUtil.getInt("retweet_count", json),
                replyCount = ParseUtil.getInt("reply_count", json),
                likeCount = ParseUtil.getInt("like_count", json),
                quoteCount = ParseUtil.getInt("quote_count", json)
        )

    }

    var source: String? = null

    var lang: String? = null

    var createdAt: Date? = null

    var publicMetrics: PublicMetrics? = null

    var possiblySensitive: Boolean = false

    // for convenient of serialization
    var pollJsonString: String? = null

    val poll: Poll?
        get() = if (pollJsonString == null) null else Poll.parse(JSONObject(pollJsonString!!))
}