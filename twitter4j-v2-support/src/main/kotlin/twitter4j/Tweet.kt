package twitter4j

data class Tweet(val id: Long, val text: String) {

    var source: String? = null

    var lang: String? = null
}
