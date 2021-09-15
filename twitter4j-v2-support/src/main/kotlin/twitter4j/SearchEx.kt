package twitter4j

import java.text.SimpleDateFormat
import java.util.*
import java.util.TimeZone

/**
 * The recent search endpoint returns Tweets from the last seven days that match a search query.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/search/api-reference/get-tweets-search-recent"
 */
@Throws(TwitterException::class)
fun Twitter.searchRecent(
    query: String,
    endTime: Date? = null,
    expansions: String? = null,
    maxResults: Int? = null,
    mediaFields: String? = null,
    nextToken: String? = null,
    placeFields: String? = null,
    pollFields: String? = null,
    sinceId: Long? = null,
    startTime: Date? = null,
    tweetFields: String? = null,
    untilId: Long? = null,
    userFields: String? = null,
): TweetsResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    return searchTweetsIn(
        conf.v2Configuration.baseURL + "tweets/search/recent",
        query,
        endTime,
        expansions,
        maxResults,
        mediaFields,
        nextToken,
        placeFields,
        pollFields,
        sinceId,
        startTime,
        tweetFields,
        untilId,
        userFields
    )
}

/**
 * The full-archive search endpoint returns the complete history of public Tweets matching a search query; since the first Tweet was created March 26, 2006.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/search/api-reference/get-tweets-search-all"
 */
@Throws(TwitterException::class)
fun Twitter.searchAll(
    query: String,
    endTime: Date? = null,
    expansions: String? = null,
    maxResults: Int? = null,
    mediaFields: String? = null,
    nextToken: String? = null,
    placeFields: String? = null,
    pollFields: String? = null,
    sinceId: Long? = null,
    startTime: Date? = null,
    tweetFields: String? = null,
    untilId: Long? = null,
    userFields: String? = null,
): TweetsResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    return searchTweetsIn(
        conf.v2Configuration.baseURL + "tweets/search/all",
        query,
        endTime,
        expansions,
        maxResults,
        mediaFields,
        nextToken,
        placeFields,
        pollFields,
        sinceId,
        startTime,
        tweetFields,
        untilId,
        userFields
    )
}

@Throws(TwitterException::class)
private fun TwitterImpl.searchTweetsIn(
    url: String,
    query: String,
    endTime: Date?,
    expansions: String?,
    maxResults: Int?,
    mediaFields: String?,
    nextToken: String?,
    placeFields: String?,
    pollFields: String?,
    sinceId: Long?,
    startTime: Date?,
    tweetFields: String?,
    untilId: Long?,
    userFields: String?
): TweetsResponse {
    ensureAuthorizationEnabled()

    val params = ArrayList<HttpParameter>()

    if (endTime != null) {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        sdf.timeZone = TimeZone.getTimeZone("GMT")
        params.add(HttpParameter("end_time", sdf.format(endTime)))
    }

    params.add(HttpParameter("query", query))

    if (expansions != null) {
        params.add(HttpParameter("expansions", expansions))
    }

    if (maxResults != null) {
        params.add(HttpParameter("max_results", maxResults))
    }

    if (mediaFields != null) {
        params.add(HttpParameter("media.fields", mediaFields))
    }

    if (nextToken != null) {
        params.add(HttpParameter("pagination_token", nextToken))
    }

    if (placeFields != null) {
        params.add(HttpParameter("place.fields", placeFields))
    }

    if (pollFields != null) {
        params.add(HttpParameter("poll.fields", pollFields))
    }

    if (sinceId != null) {
        params.add(HttpParameter("since_id", sinceId))
    }

    if (startTime != null) {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        sdf.timeZone = TimeZone.getTimeZone("GMT")
        params.add(HttpParameter("start_time", sdf.format(startTime)))
    }

    if (tweetFields != null) {
        params.add(HttpParameter("tweet.fields", tweetFields))
    }

    if (untilId != null) {
        params.add(HttpParameter("until_id", untilId))
    }

    if (userFields != null) {
        params.add(HttpParameter("user.fields", userFields))
    }

    return V2ResponseFactory().createTweetsResponse(
        http.get(
            url,
            params.toTypedArray(),
            auth,
            this
        ),
        conf
    )
}

