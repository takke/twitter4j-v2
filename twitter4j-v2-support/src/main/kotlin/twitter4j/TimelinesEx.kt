package twitter4j

import java.util.*

/**
 * Returns Tweets composed by a single user, specified by the requested user ID.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/timelines/api-reference/get-users-id-tweets"
 */
@Throws(TwitterException::class)
fun Twitter.getUserTweets(
    userId: Long,
    endTime: Date? = null,
    exclude: String? = null,
    expansions: String? = null,
    maxResults: Int? = null,
    mediaFields: String? = null,
    paginationToken: String? = null,
    placeFields: String? = null,
    pollFields: String? = null,
    sinceId: Long? = null,
    startTime: Date? = null,
    tweetFields: String? = null,
    untilId: Long? = null,
    userFields: String? = null,
): TweetsResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    return getUserTweetsIn(
        conf.v2Configuration.baseURL + "users/" + userId + "/tweets",
        endTime,
        exclude,
        expansions,
        maxResults,
        mediaFields,
        paginationToken,
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
 * Returns Tweets mentioning a single user specified by the requested user ID.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/timelines/api-reference/get-users-id-mentions"
 */
@Throws(TwitterException::class)
fun Twitter.getUserMentions(
    userId: Long,
    endTime: Date? = null,
    expansions: String? = null,
    maxResults: Int? = null,
    mediaFields: String? = null,
    paginationToken: String? = null,
    placeFields: String? = null,
    pollFields: String? = null,
    sinceId: Long? = null,
    startTime: Date? = null,
    tweetFields: String? = null,
    untilId: Long? = null,
    userFields: String? = null,
): TweetsResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    return getUserTweetsIn(
        conf.v2Configuration.baseURL + "users/" + userId + "/mentions",
        endTime,
        null,
        expansions,
        maxResults,
        mediaFields,
        paginationToken,
        placeFields,
        pollFields,
        sinceId,
        startTime,
        tweetFields,
        untilId,
        userFields
    )
}

// 2021/09/14 時点では 500 が返ってくるのでコメントアウトしておく
//
///**
// * Returns the most recent Tweets composed by a single user specified by the requested username.
// *
// * @throws TwitterException when Twitter service or network is unavailable
// * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/timelines/api-reference/get-users-by-username-username-tweets"
// */
//@Throws(TwitterException::class)
//fun Twitter.getUserTweetsBy(
//    username: String,
//    endTime: Date? = null,
//    exclude: String? = null,
//    expansions: String? = null,
//    maxResults: Int? = null,
//    mediaFields: String? = null,
//    paginationToken: String? = null,
//    placeFields: String? = null,
//    pollFields: String? = null,
//    sinceId: Long? = null,
//    startTime: Date? = null,
//    tweetFields: String? = null,
//    untilId: Long? = null,
//    userFields: String? = null,
//): TweetsResponse {
//
//    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")
//
//    return getUserTweetsIn(
//        conf.v2Configuration.baseURL + "users/by/username/" + username + "/tweets",
//        endTime,
//        exclude,
//        expansions,
//        maxResults,
//        mediaFields,
//        paginationToken,
//        placeFields,
//        pollFields,
//        sinceId,
//        startTime,
//        tweetFields,
//        untilId,
//        userFields
//    )
//}

@Throws(TwitterException::class)
private fun TwitterImpl.getUserTweetsIn(
    url: String,
    endTime: Date?,
    exclude: String?,
    expansions: String?,
    maxResults: Int?,
    mediaFields: String?,
    paginationToken: String?,
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

    V2Util.addHttpParamIfNotNull(params, "end_time", V2Util.dateToISO8601(endTime))
    V2Util.addHttpParamIfNotNull(params, "exclude", exclude)
    V2Util.addHttpParamIfNotNull(params, "expansions", expansions)
    V2Util.addHttpParamIfNotNull(params, "max_results", maxResults)
    V2Util.addHttpParamIfNotNull(params, "media.fields", mediaFields)
    V2Util.addHttpParamIfNotNull(params, "pagination_token", paginationToken)
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

