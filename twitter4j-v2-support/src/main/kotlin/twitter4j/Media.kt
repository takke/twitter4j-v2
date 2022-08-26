package twitter4j

sealed class Media {

    abstract val mediaKey: MediaKey
    abstract val type: Type
    abstract val altText: String?

    // cast
    val asPhoto: Photo get() = this as Photo
    val asAnimatedGif: AnimatedGif get() = this as AnimatedGif
    val asVideo: Video get() = this as Video

    enum class Type {
        Photo,
        AnimatedGif,
        Video,
        Unknown,
    }

    data class Photo(
        override val mediaKey: MediaKey,
        override val type: Type,
        override val altText: String?,
        val url: String,
        val width: Int,
        val height: Int,
    ) : Media()

    data class AnimatedGif(
        override val mediaKey: MediaKey,
        override val type: Type,
        override val altText: String?,
        val previewImageUrl: String,
        val width: Int,
        val height: Int,
    ) : Media()

    data class Video(
        override val mediaKey: MediaKey,
        override val type: Type,
        override val altText: String?,
        val previewImageUrl: String,
        val width: Int,
        val height: Int,
        val durationMs: Int,
        val publicMetrics: PublicMetrics,
        val variants: Array<Variant>
    ) : Media() {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Video

            if (mediaKey != other.mediaKey) return false
            if (type != other.type) return false
            if (previewImageUrl != other.previewImageUrl) return false
            if (width != other.width) return false
            if (height != other.height) return false
            if (durationMs != other.durationMs) return false
            if (publicMetrics != other.publicMetrics) return false
            if (!variants.contentEquals(other.variants)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = mediaKey.hashCode()
            result = 31 * result + type.hashCode()
            result = 31 * result + previewImageUrl.hashCode()
            result = 31 * result + width
            result = 31 * result + height
            result = 31 * result + durationMs
            result = 31 * result + publicMetrics.hashCode()
            result = 31 * result + variants.contentHashCode()
            return result
        }
    }

    data class Variant(
        val bitRate: Int?,
        val contentType: String,
        val url: String
    )

    data class UnknownMedia(
        override val mediaKey: MediaKey,
        override val type: Type,
        override val altText: String?,
    ) : Media()

    data class PublicMetrics(
        val viewCount: Int,
    ) {
        constructor(json: JSONObject?) : this(
            viewCount = ParseUtil.getInt("view_count", json)
        )
    }

    companion object {

        fun parse(json: JSONObject): Media {

            val type = when (json.getString("type")) {
                "photo" -> Type.Photo
                "animated_gif" -> Type.AnimatedGif
                "video" -> Type.Video
                else -> Type.Unknown
            }

            val mediaKey = MediaKey(json.getString("media_key"))
            val altText = if (json.has("alt_text")) json.getString("alt_text") else null

            return when (type) {
                Type.Photo -> {
                    Photo(
                        mediaKey, type, altText,
                        json.getString("url"),
                        json.getInt("width"),
                        json.getInt("height")
                    )
                }

                Type.AnimatedGif -> {
                    AnimatedGif(
                        mediaKey, type, altText,
                        json.getString("preview_image_url"),
                        json.getInt("width"),
                        json.getInt("height")
                    )
                }

                Type.Video -> {
                    val variantsJson = json.optJSONArray("variants")
                    val variants = Array(variantsJson?.length() ?: 0) { index ->
                        val v = variantsJson!!.getJSONObject(index)
                        Variant(
                            if (v.has("bit_rate")) v.getInt("bit_rate") else null,
                            v.getString("content_type"),
                            v.getString("url")
                        )
                    }

                    Video(
                        mediaKey, type, altText,
                        json.getString("preview_image_url"),
                        json.getInt("width"),
                        json.getInt("height"),
                        json.getInt("duration_ms"),
                        PublicMetrics(json.optJSONObject("public_metrics")),
                        variants
                    )
                }

                else -> {
                    UnknownMedia(mediaKey, type, altText)
                }
            }
        }

    }
}
