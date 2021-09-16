package twitter4j

class SpacesResponse : TwitterResponse {

    @Transient
    private var rateLimitStatus: RateLimitStatus? = null

    @Transient
    private var accessLevel = 0

    val spaces: List<Space> = mutableListOf()

    var meta: Meta? = null

    // includes.users
    val usersMap = HashMap<Long, User2>()

    // errors
    val errors: List<ErrorInfo> = mutableListOf()


    constructor(res: HttpResponse, isJSONStoreEnabled: Boolean) {
        rateLimitStatus = RateLimitStatusJSONImpl.createFromResponseHeader(res)
        accessLevel = ParseUtil.toAccessLevel(res)

        parse(res.asJSONObject(), isJSONStoreEnabled)
    }

    constructor(json: JSONObject, isJSONStoreEnabled: Boolean = false) {

        parse(json, isJSONStoreEnabled)
    }

    private fun parse(jsonObject: JSONObject, isJSONStoreEnabled: Boolean) {
        val spaces = spaces as MutableList
        spaces.clear()

        //--------------------------------------------------
        // create maps from includes
        //--------------------------------------------------
        val includes = jsonObject.optJSONObject("includes")
        V2Util.collectUsers(includes, usersMap)

        //--------------------------------------------------
        // create spaces from data
        //--------------------------------------------------
        val dataArray = jsonObject.optJSONArray("data")
        if (dataArray != null) {
            for (i in 0 until dataArray.length()) {
                val data = dataArray.getJSONObject(i)

                spaces.add(Space.parse(data))
            }
        }

        //--------------------------------------------------
        // meta
        //--------------------------------------------------
        meta = V2Util.parseMeta(jsonObject)

        //--------------------------------------------------
        // errors
        //--------------------------------------------------
        V2Util.collectErrors(jsonObject, errors)

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
        return "SpacesResponse(rateLimitStatus=$rateLimitStatus, accessLevel=$accessLevel, spaces=$spaces, meta=$meta, usersMap=$usersMap, errors=$errors)"
    }

}