package twitter4j

import java.util.*

data class User2(
    val id: Long,
    val location: String?,
    val createdAt: Date?,
    /**
     * Known as screen-name
     */
    val username: String,
    val protected: Boolean,
    val publicMetrics: PublicMetrics?,
    val urlEntities: List<UrlEntity2>? = mutableListOf(),
    val descriptionEntity: DescriptionEntity?,
    val description: String?,
    val url: String?,
    val profileImageUrl: String?,
    val name: String,
    val verified: Boolean,
    val pinnedTweetId: Long?
) {
    data class PublicMetrics(
        val followersCount: Int,
        val followingCount: Int,
        val tweetCount: Int,
        val listedCount: Int
    ) {

        constructor(json: JSONObject) : this(
            followersCount = ParseUtil.getInt("followers_count", json),
            followingCount = ParseUtil.getInt("following_count", json),
            tweetCount = ParseUtil.getInt("tweet_count", json),
            listedCount = ParseUtil.getInt("listed_count", json)
        )

    }

    companion object {

        fun parse(json: JSONObject): User2 {

            // entities
            val entities = json.optJSONObject("entities")

            // entities.url.urls
            val urlEntities: MutableList<UrlEntity2>? =
                entities?.optJSONObject("url")?.optJSONArray("urls")?.let { urlsArray ->
                    mutableListOf<UrlEntity2>().also {

                        for (i in 0 until urlsArray.length()) {
                            val v = urlsArray.getJSONObject(i)
                            it.add(UrlEntity2(v))
                        }
                    }
                }

            // entities.description.mentions
            val descriptionEntity: DescriptionEntity? = entities?.optJSONObject("description")?.let { descriptionJson ->
                DescriptionEntity(descriptionJson)
            }

            return User2(json.getLong("id"),
                location = json.optString("location", null),
                createdAt = V2Util.parseISO8601Date("created_at", json),
                username = json.getString("username"),
                protected = json.optBoolean("protected", false),
                publicMetrics = json.optJSONObject("public_metrics")?.let { PublicMetrics(it) },
                urlEntities = urlEntities,
                descriptionEntity = descriptionEntity,
                description = json.optString("description", null),
                url = json.optString("url", null),
                profileImageUrl = json.optString("profile_image_url", null),
                name = json.getString("name"),
                verified = json.optBoolean("verified", false),
                pinnedTweetId = json.optLong("pinned_tweet_id", -1L).takeIf { it != -1L }
            )
        }
    }

}
