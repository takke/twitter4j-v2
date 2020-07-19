package twitter4j

class TweetsResponse(res: HttpResponse) : TwitterResponse {

    @Transient
    private var rateLimitStatus: RateLimitStatus? = null

    @Transient
    private var accessLevel = 0

    private var jsonObject: JSONObject

    // convert to json object
    val asJSONObject: JSONObject get() = jsonObject

    // convert to original string for debugging purpose
    val asJSONString: String get() = jsonObject.toString()

    init {
        rateLimitStatus = RateLimitStatusJSONImpl.createFromResponseHeader(res)
        accessLevel = ParseUtil.toAccessLevel(res)

        jsonObject = res.asJSONObject()
    }

    override fun getRateLimitStatus(): RateLimitStatus? {
        return rateLimitStatus
    }

    override fun getAccessLevel(): Int {
        return accessLevel
    }


}