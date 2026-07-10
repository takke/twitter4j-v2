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

    // KMP移行 Phase 6: core の TweetEntity は Kotlin プロパティ（text/start/end）になったため override val 化。
    override val text: String get() = screenName

    override val start: Int get() = start_

    override val end: Int get() = end_
}
