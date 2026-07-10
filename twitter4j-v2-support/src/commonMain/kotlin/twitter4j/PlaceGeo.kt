package twitter4j

data class PlaceGeo(
    val type: String,
    val bbox: JSONArray,
    val properties: JSONObject,
) {
    constructor(json: JSONObject) : this(
        type = json.optString("type"),
        // KMP移行 Phase 6: core の optJSONArray/optJSONObject は nullable を返すようになったため !!（従来の非null前提を維持）
        bbox = json.optJSONArray("bbox")!!,
        properties = json.optJSONObject("properties")!!
    )
}