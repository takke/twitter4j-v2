package twitter4j

class FollowResponse : TwitterResponse {

    @Transient
    private var rateLimitStatus: RateLimitStatus? = null

    @Transient
    private var accessLevel = 0

    data class Result(val following: Boolean, val pendingFollow: Boolean)

    val result: Result


    constructor(res: HttpResponse, isJSONStoreEnabled: Boolean) {
        rateLimitStatus = RateLimitStatusJSONImpl.createFromResponseHeader(res)
        accessLevel = ParseUtil.toAccessLevel(res)

        result = parse(res.asJSONObject(), isJSONStoreEnabled)
    }

    constructor(json: JSONObject, isJSONStoreEnabled: Boolean) {
        result = parse(json, isJSONStoreEnabled)
    }

    private fun parse(jsonObject: JSONObject, isJSONStoreEnabled: Boolean): Result {

        if (isJSONStoreEnabled) {
            TwitterObjectFactory.registerJSONObject(this, jsonObject)
        }

        // {
        //   "data": {
        //     "following": true,
        //     "pending_follow": false
        //   }
        // }

        val data = jsonObject.getJSONObject("data")
        return Result(
            data.getBoolean("following"),
            data.getBoolean("pending_follow")
        )
    }

    override fun getRateLimitStatus(): RateLimitStatus? {
        return rateLimitStatus
    }

    override fun getAccessLevel(): Int {
        return accessLevel
    }

    override fun toString(): String {
        return "FollowResponse(rateLimitStatus=$rateLimitStatus, accessLevel=$accessLevel, result=$result)"
    }

}
