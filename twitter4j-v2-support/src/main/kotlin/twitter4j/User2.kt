package twitter4j

import java.util.*

data class User2(
        val id: Long,
        val location: String,
        val createdAt: Date,
        val username: String,
        val protected: Boolean,
        val publicMetrics: PublicMetrics,
        val urlEntities: List<UrlEntity2>? = mutableListOf(),
        val descriptionEntity: DescriptionEntity?,
        val description: String,
        val url: String,
        val profileImageUrl: String,
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
            val urlEntities: MutableList<UrlEntity2>? = entities?.optJSONObject("url")?.optJSONArray("urls")?.let { urlsArray ->
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
                    location = json.getString("location"),
                    createdAt = ParseUtil.getDate("created_at", json, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"),
                    username = json.getString("username"),
                    protected = json.getBoolean("protected"),
                    publicMetrics = PublicMetrics(json.getJSONObject("public_metrics")),
                    urlEntities = urlEntities,
                    descriptionEntity = descriptionEntity,
                    description = json.getString("description"),
                    url = json.getString("url"),
                    profileImageUrl = json.getString("profile_image_url"),
                    name = json.getString("name"),
                    verified = json.getBoolean("verified"),
                    pinnedTweetId = json.optLong("pinned_tweet_id", -1L).takeIf { it != -1L }
            )
        }
    }

}
