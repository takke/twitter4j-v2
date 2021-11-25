package twitter4j

internal fun JSONObject.optLongOrNull(name: String): Long? {
    return optLong(name, -1L).takeIf { it != -1L }
}