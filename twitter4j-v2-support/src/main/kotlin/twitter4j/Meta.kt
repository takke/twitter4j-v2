package twitter4j

data class Meta(
    val resultCount: Int,
    val previousToken: PaginationToken?,
    val nextToken: PaginationToken?,
    val oldestId: Long?,
    val newestId: Long?
)