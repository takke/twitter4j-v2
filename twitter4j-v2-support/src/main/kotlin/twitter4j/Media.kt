package twitter4j

sealed class Media {

    abstract val mediaKey: MediaKey

    abstract val type: Type

    val asPhoto: Photo get() = this as Photo

    enum class Type {
        Photo,
        AnimatedGif,
        Video,
        Unknown,
    }

    data class Photo(
        override val mediaKey: MediaKey,
        override val type: Type,
        val url: String,
        val width: Int,
        val height: Int,
    ) : Media()

    data class AnimatedGif(
        override val mediaKey: MediaKey,
        override val type: Type,
        val previewImageUrl: String,
        val width: Int,
        val height: Int,
    ) : Media()

    data class Video(
        override val mediaKey: MediaKey,
        override val type: Type,
        val previewImageUrl: String,
        val width: Int,
        val height: Int,
        val durationMs: Int,
        val publicMetrics: PublicMetrics
    ) : Media()

    data class InvalidMedia(
        override val mediaKey: MediaKey,
        override val type: Type,
    ) : Media()

    data class PublicMetrics(
        val viewCount: Int,
    ) {
        constructor(json: JSONObject) : this(
            viewCount = ParseUtil.getInt("retweet_count", json)
        )
    }

    companion object {

        fun parse(json: JSONObject): Media {

            val type = when (json.getString("type")) {
                "photo" -> Type.Photo
                else -> Type.Unknown
            }

            val mediaKey = MediaKey(json.getString("media_key"))

            return when (type) {
                Type.Photo -> {
                    Photo(
                        mediaKey, type,
                        json.getString("url"),
                        json.getInt("width"),
                        json.getInt("height")
                    )
                }

                else -> {
                    InvalidMedia(mediaKey, type)
                }
            }
        }

    }
}
