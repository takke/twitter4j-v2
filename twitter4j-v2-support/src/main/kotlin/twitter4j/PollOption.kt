package twitter4j

data class PollOption(
    val position: Int,
    val label: String,
    val votes: Int
)