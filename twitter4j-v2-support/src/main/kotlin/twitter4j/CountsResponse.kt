package twitter4j

import java.util.*

class CountsResponse : TwitterResponse {

    @Transient
    private var rateLimitStatus: RateLimitStatus? = null

    @Transient
    private var accessLevel = 0

    data class Count(
        val end: Date,
        val start: Date,
        val tweetCount: Int,
    )


    val counts: List<Count> = mutableListOf()

    var totalTweetCount: Int = 0


    constructor(res: HttpResponse, isJSONStoreEnabled: Boolean) {
        rateLimitStatus = RateLimitStatusJSONImpl.createFromResponseHeader(res)
        accessLevel = ParseUtil.toAccessLevel(res)

        parse(res.asJSONObject(), isJSONStoreEnabled)
    }

    constructor(json: JSONObject, isJSONStoreEnabled: Boolean = false) {

        parse(json, isJSONStoreEnabled)
    }

    private fun parse(jsonObject: JSONObject, isJSONStoreEnabled: Boolean) {
        val counts = counts as MutableList
        counts.clear()

        //--------------------------------------------------
        // create counts from data
        //--------------------------------------------------
        val dataArray = jsonObject.optJSONArray("data")
        if (dataArray != null) {
            for (i in 0 until dataArray.length()) {
                val data = dataArray.getJSONObject(i)

                counts.add(
                    Count(
                        ParseUtil.getDate(data.getString("end"), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"),
                        ParseUtil.getDate(data.getString("start"), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"),
                        data.getInt("tweet_count")
                    )
                )
            }
        }

        //--------------------------------------------------
        // meta
        //--------------------------------------------------
        totalTweetCount = jsonObject.getJSONObject("meta").getInt("total_tweet_count")

        if (isJSONStoreEnabled) {
            TwitterObjectFactory.registerJSONObject(this, jsonObject)
        }
    }

    override fun getRateLimitStatus(): RateLimitStatus? {
        return rateLimitStatus
    }

    override fun getAccessLevel(): Int {
        return accessLevel
    }

    override fun toString(): String {
        return "CountsResponse(rateLimitStatus=$rateLimitStatus, accessLevel=$accessLevel, counts=$counts, totalTweetCount=$totalTweetCount)"
    }

}