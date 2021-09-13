package twitter4j

data class UserMentionEntity2(val start: Int, val end: Int, val username: String) {

    constructor(json: JSONObject) : this(
        json.getInt("start"),
        json.getInt("end"),
        json.getString("username")
    )
}
