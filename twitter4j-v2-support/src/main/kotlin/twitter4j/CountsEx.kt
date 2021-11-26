package twitter4j

import java.util.*

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
        null,
        sinceId,
        startTime,
        untilId,
    )
}

/**
 * The full-archive search endpoint returns the complete history of public Tweets matching a search query; since the first Tweet was created March 26, 2006.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/counts/api-reference/get-tweets-counts-all"
 */
@Throws(TwitterException::class)
fun Twitter.countAll(
    query: String,
    endTime: Date? = null,
    granularity: String? = null,
    nextToken: String? = null,
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
        nextToken,
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
    nextToken: String? = null,
    sinceId: Long?,
    startTime: Date?,
    untilId: Long?
): CountsResponse {
    ensureAuthorizationEnabled()

    val params = ArrayList<HttpParameter>()

    params.add(HttpParameter("query", query))

    if (endTime != null) {
        params.add(HttpParameter("end_time", V2Util.dateToISO8601(endTime)))
    }

    V2Util.addHttpParamIfNotNull(params, "granularity", granularity)
    V2Util.addHttpParamIfNotNull(params, "next_token", nextToken)
    V2Util.addHttpParamIfNotNull(params, "since_id", sinceId)

    if (startTime != null) {
        params.add(HttpParameter("start_time", V2Util.dateToISO8601(startTime)))
    }

    V2Util.addHttpParamIfNotNull(params, "until_id", untilId)

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

