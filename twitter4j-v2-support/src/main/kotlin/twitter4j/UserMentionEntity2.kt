package twitter4j

data class UserMentionEntity2(
    val start: Int,
    val end: Int,
    val screenName: String,
    val id: Long
) {

    constructor(json: JSONObject) : this(
        json.getInt("start"),
        json.getInt("end"),
        json.getString("username"),
        json.optLong("id", -1L)
    )
}
