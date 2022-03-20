package twitter4j

data class PlaceGeo(
    val type: String,
    val bbox: JSONArray,
    val properties: JSONObject,
) {
    constructor(json: JSONObject) : this(
        type = json.optString("type"),
        bbox = json.optJSONArray("bbox"),
        properties = json.optJSONObject("properties")
    )
}