package twitter4j

class BooleanResponse : TwitterResponse {

    @Transient
    private var rateLimitStatus: RateLimitStatus? = null

    @Transient
    private var accessLevel = 0

    val result: Boolean


    constructor(res: HttpResponse, isJSONStoreEnabled: Boolean, key: String) {
        rateLimitStatus = RateLimitStatusJSONImpl.createFromResponseHeader(res)
        accessLevel = ParseUtil.toAccessLevel(res)

        result = parse(res.asJSONObject(), isJSONStoreEnabled, key)
    }

    constructor(json: JSONObject, isJSONStoreEnabled: Boolean, key: String) {
        result = parse(json, isJSONStoreEnabled, key)
    }

    private fun parse(jsonObject: JSONObject, isJSONStoreEnabled: Boolean, key: String): Boolean {

        if (isJSONStoreEnabled) {
            TwitterObjectFactory.registerJSONObject(this, jsonObject)
        }

        // {
        //   "data": {
        //     "liked": true
        //   }
        // }

        return jsonObject.getJSONObject("data").getBoolean(key)
    }

    override fun getRateLimitStatus(): RateLimitStatus? {
        return rateLimitStatus
    }

    override fun getAccessLevel(): Int {
        return accessLevel
    }

    override fun toString(): String {
        return "BooleanResponse(rateLimitStatus=$rateLimitStatus, accessLevel=$accessLevel, result=$result)"
    }

}
