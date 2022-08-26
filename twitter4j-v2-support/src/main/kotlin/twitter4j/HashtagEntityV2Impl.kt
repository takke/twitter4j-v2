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

    override fun getStart(): Int = start_

    override fun getEnd(): Int = end_

    override fun getText(): String = text_
}
