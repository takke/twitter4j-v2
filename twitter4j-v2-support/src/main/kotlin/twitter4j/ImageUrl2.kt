package twitter4j

data class ImageUrl2(val url: String, val width: Int, val height: Int) {

    companion object {
        fun parse(json: JSONObject): ImageUrl2 {
            return ImageUrl2(
                    json.getString("url"),
                    json.getInt("width"),
                    json.getInt("height")
            )
        }
    }
}
