package twitter4j

data class UserMentionEntity2(
    val start_: Int,
    val end_: Int,
    val screenName: String,
    val id: Long
) : TweetEntity {

    constructor(json: JSONObject) : this(
        json.getInt("start"),
        json.getInt("end"),
        json.getString("username"),
        json.optLong("id", -1L)
    )

    override fun getText(): String {
        return screenName
    }

    override fun getStart(): Int {
        return start_
    }

    override fun getEnd(): Int {
        return end_
    }
}
