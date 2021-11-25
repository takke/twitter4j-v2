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
    var repliedToTweetId: Long? = null,
    var quotedTweetId: Long? = null,
    var retweetId: Long? = null
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

    fun poll(pollsMap: HashMap<Long, Poll>): Poll? {
        return pollId?.let {
            pollsMap[pollId!!] ?: return null
        }
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

            return t
        }
    }
}
