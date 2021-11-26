package twitter4j

/**
 * The recent search endpoint returns Tweets from the last seven days that match a search query.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/tweets/search/api-reference/get-tweets-search-recent"
 */
@Throws(TwitterException::class)
fun Twitter.searchSpaces(
    query: String,
    state: Space.State,
    expansions: String? = null,
    maxResults: Int? = null,
    spaceFields: String? = null,
    userFields: String? = null,
): SpacesResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    val params = arrayListOf(
        HttpParameter("query", query),
        HttpParameter("state", state.rawValue),
    )

    V2Util.addHttpParamIfNotNull(params, "expansions", expansions)
    V2Util.addHttpParamIfNotNull(params, "max_results", maxResults)
    V2Util.addHttpParamIfNotNull(params, "space.fields", spaceFields)
    V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

    return V2ResponseFactory().createSpacesResponse(
        http.get(
            conf.v2Configuration.baseURL + "spaces/search",
            params.toTypedArray(),
            auth,
            this
        ),
        conf
    )
}

