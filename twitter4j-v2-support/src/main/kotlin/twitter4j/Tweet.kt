package twitter4j

import java.util.*

data class Tweet(
        val id: Long,
        val text: String,
        var source: String? = null,
        var lang: String? = null,
        var createdAt: Date? = null,
        var publicMetrics: PublicMetrics? = null,
        var possiblySensitive: Boolean = false,
        var urls: List<UrlEntity2>? = mutableListOf(),
        var author: User2? = null,
        var pollId: Long? = null
) {

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

    fun poll(pollsMap: HashMap<Long, String>): Poll? {
        pollId ?: return null

        val pollJsonString = pollsMap[pollId!!] ?: return null
        return Poll(JSONObject(pollJsonString))
    }

}
