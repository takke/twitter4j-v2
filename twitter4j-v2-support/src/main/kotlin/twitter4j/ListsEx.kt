package twitter4j

/**
 * Enables the authenticated user to create a List.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/lists/manage-lists/api-reference/post-lists"
 */
@Throws(TwitterException::class)
fun Twitter.createList(
    /**
     * The name of the List you wish to create.
     */
    name: String,
    /**
     * Description of the List.
     */
    description: String? = null,
    /**
     * Determine whether the List should be private.
     */
    private: Boolean? = null,
): ListResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    val json = JSONObject()
    json.put("name", name)

    if (description != null) {
        json.put("description", description)
    }

    if (private != null) {
        json.put("private", private)
    }

    return V2ResponseFactory().createListResponse(
        http.post(conf.v2Configuration.baseURL + "lists", arrayOf(HttpParameter(json)), auth, this),
        conf
    )
}

/**
 * Enables the authenticated user to delete a List that they own.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/lists/manage-lists/api-reference/delete-lists-id"
 */
@Throws(TwitterException::class)
fun Twitter.deleteList(
    /**
     * The ID of the List to be deleted.
     */
    id: Long
): BooleanResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    return V2ResponseFactory().createBooleanResponse(
        http.delete(conf.v2Configuration.baseURL + "lists/" + id, emptyArray(), auth, this),
        conf,
        "deleted"
    )
}
