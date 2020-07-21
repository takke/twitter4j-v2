package twitter4j

data class DescriptionEntity(val mentions: List<UserMentionEntity2>?) {

    constructor(json: JSONObject) : this(

            json.optJSONArray("mentions")?.let { mentionsArray ->
                mutableListOf<UserMentionEntity2>().also {
                    for (i in 0 until mentionsArray.length()) {
                        val v = mentionsArray.getJSONObject(i)
                        it.add(UserMentionEntity2(v))
                    }
                }
            }
    )
}
