package twitter4j

/**
 * Returns details about multiple Spaces. Up to 100 comma-separated Spaces IDs can be looked up using this endpoint.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/spaces/lookup/api-reference/get-spaces"
 */
@Throws(TwitterException::class)
fun Twitter.getSpaces(
    vararg spaceIds: String,
    expansions: String? = null,
    spaceFields: String? = null,
    userFields: String? = null,
): SpacesResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    val params = arrayListOf(
        HttpParameter("ids", spaceIds.joinToString(",")),
    )

    V2Util.addHttpParamIfNotNull(params, "expansions", expansions)
    V2Util.addHttpParamIfNotNull(params, "space.fields", spaceFields)
    V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

    return V2ResponseFactory().createSpacesResponse(
        http.get(
            conf.v2Configuration.baseURL + "spaces",
            params.toTypedArray(),
            auth,
            this
        ),
        conf
    )
}

/**
 * Returns live or scheduled Spaces created by the specified user IDs. Up to 100 comma-separated IDs can be looked up using this endpoint.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/spaces/lookup/api-reference/get-spaces-by-creator-ids"
 */
@Throws(TwitterException::class)
fun Twitter.getSpacesByCreatorIds(
    vararg userIds: Long,
    expansions: String? = null,
    spaceFields: String? = null,
    userFields: String? = null,
): SpacesResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    val params = arrayListOf(
        HttpParameter("user_ids", userIds.joinToString(",")),
    )

    V2Util.addHttpParamIfNotNull(params, "expansions", expansions)
    V2Util.addHttpParamIfNotNull(params, "space.fields", spaceFields)
    V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

    return V2ResponseFactory().createSpacesResponse(
        http.get(
            conf.v2Configuration.baseURL + "spaces/by/creator_ids",
            params.toTypedArray(),
            auth,
            this
        ),
        conf
    )
}

