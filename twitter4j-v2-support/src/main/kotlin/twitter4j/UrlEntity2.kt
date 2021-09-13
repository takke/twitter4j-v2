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
        url = json.getString("url"),
        start = json.getInt("start"),
        end = json.getInt("end"),
        expandedUrl = json.getString("expanded_url"),
        displayUrl = json.getString("display_url"),
        images = json.optJSONArray("images")?.let { imagesArray ->
            mutableListOf<ImageUrl2>().also { images ->
                for (i in 0 until imagesArray.length()) {
                    images.add(ImageUrl2(imagesArray.getJSONObject(i)))
                }
            }
        },
        status = json.optInt("status", -1).takeIf { it != -1 },
        title = json.optString("title", null),
        description = json.optString("description", null),
        unwoundUrl = json.optString("unwound_url", null)
    )

}
