package twitter4j

class CreateTweetResponse : TwitterResponse {

    @Transient
    private var rateLimitStatus: RateLimitStatus? = null

    @Transient
    private var accessLevel = 0

    val id: Long

    val text: String


    constructor(res: HttpResponse, isJSONStoreEnabled: Boolean) {
        rateLimitStatus = RateLimitStatusJSONImpl.createFromResponseHeader(res)
        accessLevel = ParseUtil.toAccessLevel(res)

        val dataJson = parse(res.asJSONObject(), isJSONStoreEnabled)
        id = dataJson.getLong("id")
        text = dataJson.getString("text")
    }

    constructor(json: JSONObject, isJSONStoreEnabled: Boolean) {
        val dataJson = parse(json, isJSONStoreEnabled)
        id = dataJson.getLong("id")
        text = dataJson.getString("text")
    }

    private fun parse(jsonObject: JSONObject, isJSONStoreEnabled: Boolean): JSONObject {

        if (isJSONStoreEnabled) {
            TwitterObjectFactory.registerJSONObject(this, jsonObject)
        }

        // {
        //   "data": {
        //     "id": "1445880548472328192",
        //     "text": "Hello world!"
        //   }
        // }

        return jsonObject.getJSONObject("data")
    }

    override fun getRateLimitStatus(): RateLimitStatus? {
        return rateLimitStatus
    }

    override fun getAccessLevel(): Int {
        return accessLevel
    }

    override fun toString(): String {
        return "CreateTweetResponse(rateLimitStatus=$rateLimitStatus, accessLevel=$accessLevel, id=$id, text='$text')"
    }

}
