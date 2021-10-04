package twitter4j

class ListResponse : TwitterResponse {

    @Transient
    private var rateLimitStatus: RateLimitStatus? = null

    @Transient
    private var accessLevel = 0

    val id: Long

    val name: String


    constructor(res: HttpResponse, isJSONStoreEnabled: Boolean) {
        rateLimitStatus = RateLimitStatusJSONImpl.createFromResponseHeader(res)
        accessLevel = ParseUtil.toAccessLevel(res)

        val dataJson = parse(res.asJSONObject(), isJSONStoreEnabled)
        id = dataJson.getLong("id")
        name = dataJson.getString("name")
    }

    constructor(json: JSONObject, isJSONStoreEnabled: Boolean) {
        val dataJson = parse(json, isJSONStoreEnabled)
        id = dataJson.getLong("id")
        name = dataJson.getString("name")
    }

    private fun parse(jsonObject: JSONObject, isJSONStoreEnabled: Boolean): JSONObject {

        if (isJSONStoreEnabled) {
            TwitterObjectFactory.registerJSONObject(this, jsonObject)
        }

        // {
        //   "data": {
        //     "id": "1441162269824405510",
        //     "name": "test v2 create list"
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
        return "ListResponse(rateLimitStatus=$rateLimitStatus, accessLevel=$accessLevel, id=$id, name='$name')"
    }

}
