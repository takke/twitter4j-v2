package twitter4j

data class ErrorInfo(
        val resourceType: String,
        val field: String,
        val parameter: String,
        val value: String,
        val title: String,
        val section: String,
        val detail: String,
        val type: String
) {
    constructor(json: JSONObject) : this(
            resourceType = json.optString("resource_type"),
            field = json.optString("field"),
            parameter = json.optString("parameter"),
            value = json.optString("value"),
            title = json.optString("title"),
            section = json.optString("section"),
            detail = json.optString("detail"),
            type = json.optString("type"),
    )
}
