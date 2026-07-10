package twitter4j

data class Place2(
    val id: String,
    val fullName: String?,
//  add val containedWithin
    val country: String?,
    val countryCode: String?,
    val geo: PlaceGeo?,
    val name: String?,
    val placeType: String?,
) {
    companion object {

        fun parse(json: JSONObject): Place2 {
            return Place2(
                json.getString("id"),
                name = json.optString("name", null),
                fullName = json.optString("full_name", null),
                placeType = json.optString("place_type", null),
                country = json.optString("country", null),
                countryCode = json.optString("country_code", null),
                geo = json.optJSONObject("geo")?.let { PlaceGeo(it) }
            )
        }
    }
}