package twitter4j

data class UrlEntity2(
        val url: String,
        val start: Int,
        val end: Int,
        val expandedUrl: String,
        val displayUrl: String,
        val images: List<ImageUrl2>?,
        val status: Int?,
        val title: String?,
        val description: String?,
        val unwoundUrl: String?
) {
    constructor(json: JSONObject) : this(
            json.getString("url"),
            json.getInt("start"),
            json.getInt("end"),
            json.getString("expanded_url"),
            json.getString("display_url"),
            json.getJSONArray("images")?.let { imagesArray ->
                mutableListOf<ImageUrl2>().also { images ->
                    for (i in 0 until imagesArray.length()) {
                        images.add(ImageUrl2(imagesArray.getJSONObject(i)))
                    }
                }
            },
            json.optInt("status", -1).takeIf { it != -1 },
            json.optString("title", null),
            json.optString("description", null),
            json.optString("unwound_url", null)
    )

}
