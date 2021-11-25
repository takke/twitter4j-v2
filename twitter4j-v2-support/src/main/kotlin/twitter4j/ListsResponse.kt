package twitter4j

import java.util.*
import kotlin.collections.HashMap

class ListsResponse : TwitterResponse {

    @Transient
    private var rateLimitStatus: RateLimitStatus? = null

    @Transient
    private var accessLevel = 0

    val id: Long

    val name: String

    // optional fields
    var ownerId: Long? = null

    var createdAt: Date? = null

    var followerCount: Int? = null

    var memberCount: Int? = null

    var isPrivate: Boolean? = null

    var description: String? = null

    // includes.users
    val usersMap = HashMap<Long, User2>()


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

        val includes = jsonObject.optJSONObject("includes")

        //--------------------------------------------------
        // create maps from includes
        //--------------------------------------------------
        V2Util.collectUsers(includes, usersMap)

        // {
        //   "data": {
        //     "id": "1441162269824405510",
        //     "name": "test v2 create list"
        //   }
        // }

        val data = jsonObject.getJSONObject("data")

        // optional fields
        ownerId = data.optLongOrNull("owner_id")
        createdAt = V2Util.parseISO8601Date("created_at", data)
        followerCount = data.optIntOrNull("follower_count")
        memberCount = data.optIntOrNull("member_count")
        isPrivate = data.optBooleanOrNull("private")
        description = data.optString("description", null)

        // required fields
        return data
    }

    override fun getRateLimitStatus(): RateLimitStatus? {
        return rateLimitStatus
    }

    override fun getAccessLevel(): Int {
        return accessLevel
    }

    override fun toString(): String {
        return "ListsResponse(rateLimitStatus=$rateLimitStatus, accessLevel=$accessLevel, id=$id, name='$name', ownerId=$ownerId, createdAt=$createdAt, followerCount=$followerCount, memberCount=$memberCount, isPrivate=$isPrivate, description=$description, usersMap=$usersMap)"
    }

}
