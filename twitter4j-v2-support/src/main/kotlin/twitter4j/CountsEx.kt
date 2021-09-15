package twitter4j

import java.text.SimpleDateFormat
import java.util.*
import java.util.TimeZone

/**
 * The recent Tweet counts endpoint returns count of Tweets from the last seven days that match a search query.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/counts/api-reference/get-tweets-counts-recent"
 */
@Throws(TwitterException::class)
fun Twitter.countRecent(
    query: String,
    endTime: Date? = null,
    granularity: String? = null,
    sinceId: Long? = null,
    startTime: Date? = null,
    untilId: Long? = null
): CountsResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    return countTweetsIn(
        conf.v2Configuration.baseURL + "tweets/counts/recent",
        query,
        endTime,
        granularity,
        sinceId,
        startTime,
        untilId,
    )
}

@Throws(TwitterException::class)
private fun TwitterImpl.countTweetsIn(
    url: String,
    query: String,
    endTime: Date?,
    granularity: String? = null,
    sinceId: Long?,
    startTime: Date?,
    untilId: Long?
): CountsResponse {
    ensureAuthorizationEnabled()

    val params = ArrayList<HttpParameter>()

    params.add(HttpParameter("query", query))

    if (endTime != null) {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        sdf.timeZone = TimeZone.getTimeZone("GMT")
        params.add(HttpParameter("end_time", sdf.format(endTime)))
    }

    if (granularity != null) {
        params.add(HttpParameter("granularity", granularity))
    }

    if (sinceId != null) {
        params.add(HttpParameter("since_id", sinceId))
    }

    if (startTime != null) {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        sdf.timeZone = TimeZone.getTimeZone("GMT")
        params.add(HttpParameter("start_time", sdf.format(startTime)))
    }

    if (untilId != null) {
        params.add(HttpParameter("until_id", untilId))
    }

    return V2ResponseFactory().createCountsResponse(
        http.get(
            url,
            params.toTypedArray(),
            auth,
            this
        ),
        conf
    )
}

