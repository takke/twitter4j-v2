package twitter4j

import java.util.*

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
    nextToken: PaginationToken? = null,
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
    nextToken: PaginationToken? = null,
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
    nextToken: PaginationToken?,
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

    params.add(HttpParameter("query", query))

    V2Util.addHttpParamIfNotNull(params, "end_time", V2Util.dateToISO8601(endTime))
    V2Util.addHttpParamIfNotNull(params, "expansions", expansions)
    V2Util.addHttpParamIfNotNull(params, "max_results", maxResults)
    V2Util.addHttpParamIfNotNull(params, "media.fields", mediaFields)
    V2Util.addHttpParamIfNotNull(params, "next_token", nextToken)
    V2Util.addHttpParamIfNotNull(params, "place.fields", placeFields)
    V2Util.addHttpParamIfNotNull(params, "poll.fields", pollFields)
    V2Util.addHttpParamIfNotNull(params, "since_id", sinceId)
    V2Util.addHttpParamIfNotNull(params, "start_time", V2Util.dateToISO8601(startTime))
    V2Util.addHttpParamIfNotNull(params, "tweet.fields", tweetFields)
    V2Util.addHttpParamIfNotNull(params, "until_id", untilId)
    V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

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

