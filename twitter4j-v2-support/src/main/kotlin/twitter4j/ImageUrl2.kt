package twitter4j

data class ImageUrl2(val url: String, val width: Int, val height: Int) {

    constructor(json: JSONObject) : this(
        json.getString("url"),
        json.getInt("width"),
        json.getInt("height")
    )
}
