package twitter4j

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
