package twitter4j

import java.util.*

data class Tweet(
    val id: Long,
    val text: String,
    var source: String? = null,
    var lang: String? = null,
    var createdAt: Date? = null,
    var publicMetrics: PublicMetrics? = null,
    var nonPublicMetrics: NonPublicMetrics? = null,
    var organicMetrics: OrganicMetrics? = null,
    var possiblySensitive: Boolean = false,
    var urls: List<UrlEntity2>? = mutableListOf(),
    var authorId: Long? = null,
    var pollId: Long? = null,
    var placeId: String? = null,
    var repliedToTweetId: Long? = null,
    var quotedTweetId: Long? = null,
    var retweetId: Long? = null,
    var conversationId: Long? = null,
    var mediaKeys: Array<MediaKey>? = null
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

    data class NonPublicMetrics(
        val impressionCount: Int,
        val urlLinkClicks: Int,
        val userProfileClicks: Int
    ) {
        constructor(json: JSONObject) : this(
            impressionCount = ParseUtil.getInt("impression_count", json),
            urlLinkClicks = ParseUtil.getInt("url_link_clicks", json),
            userProfileClicks = ParseUtil.getInt("user_profile_clicks", json)
        )
    }

    data class OrganicMetrics(
        val impressionCount: Int,
        val urlLinkClicks: Int,
        val userProfileClicks: Int,
        val retweetCount: Int,
        val replyCount: Int,
        val likeCount: Int
    ) {
        constructor(json: JSONObject) : this(
            impressionCount = ParseUtil.getInt("impression_count", json),
            urlLinkClicks = ParseUtil.getInt("url_link_clicks", json),
            userProfileClicks = ParseUtil.getInt("user_profile_clicks", json),
            retweetCount = ParseUtil.getInt("retweet_count", json),
            replyCount = ParseUtil.getInt("reply_count", json),
            likeCount = ParseUtil.getInt("like_count", json)
        )
    }

    fun place(placeMap: HashMap<String, Place2>): Place2? {
        return placeId?.let {
            placeMap[placeId!!] ?: return null
        }
    }

    fun poll(pollsMap: HashMap<Long, Poll>): Poll? {
        return pollId?.let {
            pollsMap[pollId!!] ?: return null
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Tweet

        if (id != other.id) return false
        if (text != other.text) return false
        if (source != other.source) return false
        if (lang != other.lang) return false
        if (createdAt != other.createdAt) return false
        if (publicMetrics != other.publicMetrics) return false
        if (nonPublicMetrics != other.nonPublicMetrics) return false
        if (organicMetrics != other.organicMetrics) return false
        if (possiblySensitive != other.possiblySensitive) return false
        if (urls != other.urls) return false
        if (authorId != other.authorId) return false
        if (pollId != other.pollId) return false
        if (placeId != other.placeId) return false
        if (repliedToTweetId != other.repliedToTweetId) return false
        if (quotedTweetId != other.quotedTweetId) return false
        if (retweetId != other.retweetId) return false
        if (conversationId != other.conversationId) return false
        if (mediaKeys != null) {
            if (other.mediaKeys == null) return false
            if (!mediaKeys.contentEquals(other.mediaKeys)) return false
        } else if (other.mediaKeys != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + text.hashCode()
        result = 31 * result + (source?.hashCode() ?: 0)
        result = 31 * result + (lang?.hashCode() ?: 0)
        result = 31 * result + (createdAt?.hashCode() ?: 0)
        result = 31 * result + (publicMetrics?.hashCode() ?: 0)
        result = 31 * result + (nonPublicMetrics?.hashCode() ?: 0)
        result = 31 * result + (organicMetrics?.hashCode() ?: 0)
        result = 31 * result + possiblySensitive.hashCode()
        result = 31 * result + (urls?.hashCode() ?: 0)
        result = 31 * result + (authorId?.hashCode() ?: 0)
        result = 31 * result + (pollId?.hashCode() ?: 0)
        result = 31 * result + (placeId?.hashCode() ?: 0)
        result = 31 * result + (repliedToTweetId?.hashCode() ?: 0)
        result = 31 * result + (quotedTweetId?.hashCode() ?: 0)
        result = 31 * result + (retweetId?.hashCode() ?: 0)
        result = 31 * result + (conversationId?.hashCode() ?: 0)
        result = 31 * result + (mediaKeys?.contentHashCode() ?: 0)
        return result
    }

    companion object {

        fun parse(data: JSONObject): Tweet {

            val t = Tweet(
                data.getLong("id"),
                data.getString("text")
            )

            t.source = data.optString("source", null)
            t.lang = data.optString("lang", null)
            t.createdAt = V2Util.parseISO8601Date("created_at", data)
            data.optJSONObject("public_metrics")?.let {
                t.publicMetrics = PublicMetrics(it)
            }
            data.optJSONObject("non_public_metrics")?.let {
                t.nonPublicMetrics = NonPublicMetrics(it)
            }
            data.optJSONObject("organic_metrics")?.let {
                t.organicMetrics = OrganicMetrics(it)
            }

            // entities.urls
            data.optJSONObject("entities")?.let { entities ->
                entities.optJSONArray("urls")?.let { urlsArray ->
                    val urls = t.urls as MutableList
                    for (iUrl in 0 until urlsArray.length()) {
                        val url = urlsArray.getJSONObject(iUrl)
                        urls.add(UrlEntity2(url))
                    }
                }
            }

            // author_id
            data.optLongOrNull("author_id")?.let { authorId ->
                t.authorId = authorId
            }

            // poll
            val attachments = data.optJSONObject("attachments")
            val pollId = attachments?.optJSONArray("poll_ids")?.let {
                if (it.length() == 0) {
                    null
                } else {
                    ParseUtil.getLong(it.getString(0))
                }
            }
            if (pollId != null) {
                t.pollId = pollId
            }

            // media_keys
            val mediaKeys = attachments?.optJSONArray("media_keys")?.let {
                val length = it.length()
                if (length == 0) {
                    null
                } else {
                    Array(length) { i ->
                        MediaKey(it.getString(i))
                    }
                }
            }
            if (mediaKeys != null) {
                t.mediaKeys = mediaKeys
            }

            // place
            val geo = data.optJSONObject("geo")
            t.placeId = geo?.optString("place_id")

            t.possiblySensitive = data.optBoolean("possibly_sensitive", false)

            // referenced_tweets
            data.optJSONArray("referenced_tweets")?.let { referencedTweetsArray ->
                for (i in 0 until referencedTweetsArray.length()) {
                    val referencedTweet = referencedTweetsArray.getJSONObject(i)
                    when (referencedTweet.getString("type")) {
                        "replied_to" -> {
                            t.repliedToTweetId = referencedTweet.getLong("id")
                        }
                        "quoted" -> {
                            t.quotedTweetId = referencedTweet.getLong("id")
                        }
                        "retweeted" -> {
                            t.retweetId = referencedTweet.getLong("id")
                        }
                    }
                }
            }

            // conversation_id
            t.conversationId = data.optLongOrNull("conversation_id")

            return t
        }
    }
}