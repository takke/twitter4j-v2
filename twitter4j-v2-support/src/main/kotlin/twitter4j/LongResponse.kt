package twitter4j

class LongResponse : TwitterResponse {
    @Transient
    private var rateLimitStatus: RateLimitStatus? = null

    @Transient
    private var accessLevel = 0

    val mediaId: Long

    constructor(res: HttpResponse, isJSONStoreEnabled: Boolean, key: String) {
        rateLimitStatus = RateLimitStatusJSONImpl.createFromResponseHeader(res)
        accessLevel = ParseUtil.toAccessLevel(res)

        mediaId = parse(res.asJSONObject(), isJSONStoreEnabled, key)
    }

    constructor(json: JSONObject, isJSONStoreEnabled: Boolean, key: String) {
        mediaId = parse(json, isJSONStoreEnabled, key)
    }

    private fun parse(jsonObject: JSONObject, isJSONStoreEnabled: Boolean, key: String): Long {

        if (isJSONStoreEnabled) {
            TwitterObjectFactory.registerJSONObject(this, jsonObject)
        }

        return jsonObject.getJSONObject("data").getLong(key)
    }

    override fun getRateLimitStatus(): RateLimitStatus? {
        return rateLimitStatus
    }

    override fun getAccessLevel(): Int {
        return accessLevel
    }
}