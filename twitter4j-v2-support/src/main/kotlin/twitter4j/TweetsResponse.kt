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

        val includes = jsonObject.optJSONObject("includes")

        //--------------------------------------------------
        // create map of polls from includes.polls
        //--------------------------------------------------
        val pollsMap = V2Util.collectPolls(includes)

        //--------------------------------------------------
        // create map of users from includes.users
        //--------------------------------------------------
        val usersMap = V2Util.collectUsers(includes)


        // TODO includes.tweets, includes.places, includes.media ...

        //--------------------------------------------------
        // create tweets from data
        //--------------------------------------------------
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
                t.publicMetrics = Tweet.PublicMetrics(it)
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
            data.optLong("author_id", -1L).takeIf { it != -1L }?.let { authorId ->
                t.author = usersMap[authorId]
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
                t.pollJsonString = pollsMap[pollId]
            }

            t.possiblySensitive = data.optBoolean("possibly_sensitive", false)

            tweets.add(t)
        }
    }

    override fun getRateLimitStatus(): RateLimitStatus? {
        return rateLimitStatus
    }

    override fun getAccessLevel(): Int {
        return accessLevel
    }

    override fun toString(): String {
        return "TweetsResponse(rateLimitStatus=$rateLimitStatus, accessLevel=$accessLevel, tweets=$tweets)"
    }


}