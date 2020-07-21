package twitter4j

data class UrlEntity2(
        val url: String,
        val start: Int,
        val end: Int,
        val expandedUrl: String,
        val displayUrl: String,
        val images: List<ImageUrl2>,
        val status: Int,
        val title: String,
        val description: String,
        val unwoundUrl: String
) {
    companion object {
        fun parse(json: JSONObject): UrlEntity2 {

            val images: MutableList<ImageUrl2> = mutableListOf()
            json.getJSONArray("images").let { imagesArray ->
                for (i in 0 until imagesArray.length()) {
                    images.add(ImageUrl2.parse(imagesArray.getJSONObject(i)))
                }
            }

            return UrlEntity2(
                    json.getString("url"),
                    json.getInt("start"),
                    json.getInt("end"),
                    json.getString("expanded_url"),
                    json.getString("display_url"),
                    images,
                    json.getInt("status"),
                    json.getString("title"),
                    json.getString("description"),
                    json.getString("unwound_url")
            )
        }
    }
}
