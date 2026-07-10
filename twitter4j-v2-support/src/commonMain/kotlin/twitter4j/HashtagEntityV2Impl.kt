package twitter4j

data class HashtagEntityV2Impl(
    val start_: Int,
    val end_: Int,
    val text_: String
) : HashtagEntity {
    constructor(json: JSONObject) : this(
        json.getInt("start"),
        json.getInt("end"),
        json.getString("tag")
    )

    // KMP移行 Phase 6: core の TweetEntity/HashtagEntity は Kotlin プロパティになったため override val 化。
    override val start: Int get() = start_

    override val end: Int get() = end_

    override val text: String get() = text_
}
