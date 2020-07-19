package twitter4j

class TweetsResponse : TwitterResponse {

    @Transient
    private var rateLimitStatus: RateLimitStatus? = null

    @Transient
    private var accessLevel = 0

    private var jsonObject: JSONObject

    // convert to json object
    val asJSONObject: JSONObject get() = jsonObject

    val tweets: List<Tweet> = mutableListOf()


    constructor(res: HttpResponse) {
        rateLimitStatus = RateLimitStatusJSONImpl.createFromResponseHeader(res)
        accessLevel = ParseUtil.toAccessLevel(res)
        jsonObject = res.asJSONObject()

        parse()
    }

    constructor(json: JSONObject) {
        jsonObject = json

        parse()
    }

    private fun parse() {
        val tweets = tweets as MutableList
        tweets.clear()

        // data
        val dataArray = jsonObject.getJSONArray("data")
        for (i in 0 until dataArray.length()) {
            val data = dataArray.getJSONObject(i)
            val t = Tweet(
                    data.getLong("id"),
                    data.getString("text")
            )

            t.source = data.optString("source", null)
            t.lang = data.optString("lang", null)
            t.createdAt = ParseUtil.getDate("created_at", data, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            data.optJSONObject("public_metrics")?.let {
                t.publicMetrics = PublicMetrics(it)
            }

            // TODO author_id

            // TODO attachments.poll_ids

            t.possiblySensitive = data.optBoolean("possibly_sensitive", false)

            tweets.add(t)
        }

        // includes.polls

        // TODO includes.users, ...
    }

    override fun getRateLimitStatus(): RateLimitStatus? {
        return rateLimitStatus
    }

    override fun getAccessLevel(): Int {
        return accessLevel
    }


}