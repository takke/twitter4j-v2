package twitter4j

data class Meta(
    val resultCount: Int,
    val previousToken: String?,
    val nextToken: String?
)