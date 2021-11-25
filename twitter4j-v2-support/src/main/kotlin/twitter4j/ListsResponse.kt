package twitter4j

import java.util.*
import kotlin.collections.HashMap

data class TwitterList(
    val id: Long,
    val name: String,
    // optional fields
    val ownerId: Long? = null,
    val createdAt: Date? = null,
    val followerCount: Int? = null,
    val memberCount: Int? = null,
    val isPrivate: Boolean? = null,
    val description: String? = null,
)

class ListsResponse : TwitterResponse {

    @Transient
    private var rateLimitStatus: RateLimitStatus? = null

    @Transient
    private var accessLevel = 0

    val lists: ArrayList<TwitterList> = ArrayList<TwitterList>()

    // includes.users
    val usersMap = HashMap<Long, User2>()


    constructor(res: HttpResponse, isJSONStoreEnabled: Boolean) {
        rateLimitStatus = RateLimitStatusJSONImpl.createFromResponseHeader(res)
        accessLevel = ParseUtil.toAccessLevel(res)

        parse(res.asJSONObject(), isJSONStoreEnabled)
    }

    constructor(json: JSONObject, isJSONStoreEnabled: Boolean) {
        parse(json, isJSONStoreEnabled)
    }

    private fun parse(jsonObject: JSONObject, isJSONStoreEnabled: Boolean) {

        if (isJSONStoreEnabled) {
            TwitterObjectFactory.registerJSONObject(this, jsonObject)
        }

        val includes = jsonObject.optJSONObject("includes")

        //--------------------------------------------------
        // create maps from includes
        //--------------------------------------------------
        V2Util.collectUsers(includes, usersMap)

        when (val data = jsonObject.get("data")) {
            is JSONObject -> {
                // {
                //   "data": {
                //     "id": "1441162269824405510",
                //     "name": "test v2 create list"
                //   }
                // }
                lists.add(parseTwitterList(data))
            }
            is JSONArray -> {
                // "data": [
                //   {
                //     "id": "1207354259852820480",
                //     "name": "Tweets by Twitter"
                //   },
                //   {
                //     "id": "1015240715587158021",
                //     "name": "Interests on Twitter"
                //   },
                //   ..
                // ]

                for (i in 0 until data.length()) {
                    val entry = data.getJSONObject(i)
                    lists.add(parseTwitterList(entry))
                }
            }
        }
    }

    private fun parseTwitterList(data: JSONObject) = TwitterList(
        // required fields
        id = data.getLong("id"),
        name = data.getString("name"),

        // optional fields
        ownerId = data.optLongOrNull("owner_id"),
        createdAt = V2Util.parseISO8601Date("created_at", data),
        followerCount = data.optIntOrNull("follower_count"),
        memberCount = data.optIntOrNull("member_count"),
        isPrivate = data.optBooleanOrNull("private"),
        description = data.optString("description", null)
    )

    override fun getRateLimitStatus(): RateLimitStatus? {
        return rateLimitStatus
    }

    override fun getAccessLevel(): Int {
        return accessLevel
    }

    override fun toString(): String {
        return "ListsResponse(rateLimitStatus=$rateLimitStatus, accessLevel=$accessLevel, lists=$lists, usersMap=$usersMap)"
    }

}
