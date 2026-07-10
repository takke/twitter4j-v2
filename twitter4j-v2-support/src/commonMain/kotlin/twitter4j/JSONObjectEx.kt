package twitter4j

internal fun JSONObject.optLongOrNull(name: String): Long? {
    return optLong(name, -1L).takeIf { it != -1L }
}

internal fun JSONObject.optIntOrNull(name: String): Int? {
    return optInt(name, -1).takeIf { it != -1 }
}

internal fun JSONObject.optBooleanOrNull(name: String): Boolean? {
    return if (!has(name)) null else optBoolean(name, false)
}
