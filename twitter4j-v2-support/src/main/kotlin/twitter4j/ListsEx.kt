package twitter4j

/**
 * Lookup a specific list by ID
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/lists/list-lookup/api-reference/get-lists-id"
 */
@Throws(TwitterException::class)
fun Twitter.getList(
    /**
     * The ID of the List to lookup.
     */
    id: Long,
    expansions: String? = null,
    listFields: String? = null,
    userFields: String? = null,
): ListsResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    val params = ArrayList<HttpParameter>()

    if (expansions != null) {
        params.add(HttpParameter("expansions", expansions))
    }

    if (listFields != null) {
        params.add(HttpParameter("list.fields", listFields))
    }

    if (userFields != null) {
        params.add(HttpParameter("user.fields", userFields))
    }

    return V2ResponseFactory().createListsResponse(
        http.get(conf.v2Configuration.baseURL + "lists/" + id, params.toTypedArray(), auth, this),
        conf
    )
}

/**
 * Lookup a specific list by ID
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/lists/list-lookup/api-reference/get-users-id-owned_lists#Optional"
 */
@Throws(TwitterException::class)
fun Twitter.getOwnedLists(
    /**
     * The user ID whose owned Lists you would like to retrieve.
     */
    id: Long,
    expansions: String? = null,
    listFields: String? = null,
    maxResults: Int? = null,
    paginationToken: String? = null,
    userFields: String? = null,
): ListsResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    val params = ArrayList<HttpParameter>()

    if (expansions != null) {
        params.add(HttpParameter("expansions", expansions))
    }

    if (listFields != null) {
        params.add(HttpParameter("list.fields", listFields))
    }

    if (maxResults != null) {
        params.add(HttpParameter("max_results", maxResults))
    }

    if (paginationToken != null) {
        params.add(HttpParameter("pagination_token", paginationToken))
    }

    if (userFields != null) {
        params.add(HttpParameter("user.fields", userFields))
    }

    return V2ResponseFactory().createListsResponse(
        http.get(conf.v2Configuration.baseURL + "users/" + id + "/owned_lists", params.toTypedArray(), auth, this),
        conf
    )
}

/**
 * Lookup Tweets from a specified List
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/lists/list-tweets/api-reference/get-lists-id-tweets"
 */
@Throws(TwitterException::class)
fun Twitter.getListTweets(
    /**
     * The ID of the List whose Tweets you would like to retrieve.
     */
    id: Long,
    expansions: String? = null,
    maxResults: Int? = null,
    paginationToken: String? = null,
    tweetFields: String? = null,
    userFields: String? = null,
): TweetsResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    val params = ArrayList<HttpParameter>()

    if (expansions != null) {
        params.add(HttpParameter("expansions", expansions))
    }

    if (maxResults != null) {
        params.add(HttpParameter("max_results", maxResults))
    }

    if (paginationToken != null) {
        params.add(HttpParameter("pagination_token", paginationToken))
    }

    if (tweetFields != null) {
        params.add(HttpParameter("tweet.fields", tweetFields))
    }

    if (userFields != null) {
        params.add(HttpParameter("user.fields", userFields))
    }

    return V2ResponseFactory().createTweetsResponse(
        http.get(conf.v2Configuration.baseURL + "lists/" + id + "/tweets", params.toTypedArray(), auth, this),
        conf
    )
}

/**
 * Returns a list of members from a specified List
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/lists/list-members/api-reference/get-lists-id-members"
 */
@Throws(TwitterException::class)
fun Twitter.getListMembers(
    /**
     * The ID of the List whose members you would like to retrieve.
     */
    id: Long,
    expansions: String? = null,
    maxResults: Int? = null,
    paginationToken: String? = null,
    tweetFields: String? = null,
    userFields: String? = null,
): UsersResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    val params = ArrayList<HttpParameter>()

    V2Util.addHttpParamIfNotNull(params, "expansions", expansions)
    V2Util.addHttpParamIfNotNull(params, "max_results", maxResults)
    V2Util.addHttpParamIfNotNull(params, "pagination_token", paginationToken)
    V2Util.addHttpParamIfNotNull(params, "tweet.fields", tweetFields)
    V2Util.addHttpParamIfNotNull(params, "user.fields", userFields)

    return V2ResponseFactory().createUsersResponse(
        http.get(conf.v2Configuration.baseURL + "lists/" + id + "/members", params.toTypedArray(), auth, this),
        conf
    )
}

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
): ListsResponse {

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

    return V2ResponseFactory().createListsResponse(
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

/**
 * Enables the authenticated user to add a member to a List they own.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/lists/manage-lists/api-reference/post-lists-id-members"
 */
@Throws(TwitterException::class)
fun Twitter.addListMember(
    /**
     * The ID of the List you are adding a member to.
     */
    listId: Long,
    /**
     * The ID of the user you wish to add as a member of the List.
     */
    userId: Long
): BooleanResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    val json = JSONObject()
    json.put("user_id", userId.toString())

    return V2ResponseFactory().createBooleanResponse(
        http.post(
            conf.v2Configuration.baseURL + "lists/" + listId + "/members",
            arrayOf(HttpParameter(json)),
            auth,
            this
        ),
        conf,
        "is_member"
    )
}

/**
 * Enables the authenticated user to remove a member from a List they own.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/lists/manage-lists/api-reference/delete-lists-id-members-user_id"
 */
@Throws(TwitterException::class)
fun Twitter.deleteListMember(
    /**
     * The ID of the List you are removing a member from.
     */
    listId: Long,
    /**
     * The ID of the user you wish to remove as a member of the List.
     */
    userId: Long
): BooleanResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    return V2ResponseFactory().createBooleanResponse(
        http.delete(conf.v2Configuration.baseURL + "lists/" + listId + "/members/" + userId, emptyArray(), auth, this),
        conf,
        "is_member"
    )
}

/**
 * Enables the authenticated user to follow a List.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/lists/manage-lists/api-reference/post-users-id-followed-lists"
 */
@Throws(TwitterException::class)
fun Twitter.followList(
    /**
     * The user ID who you are following a List on behalf of.
     */
    userId: Long,
    /**
     * The ID of the List that you would like the user id to follow.
     */
    listId: Long
): BooleanResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    val json = JSONObject()
    json.put("list_id", listId.toString())

    return V2ResponseFactory().createBooleanResponse(
        http.post(
            conf.v2Configuration.baseURL + "users/${userId}/followed_lists",
            arrayOf(HttpParameter(json)),
            auth,
            this
        ),
        conf,
        "following"
    )
}

/**
 * Enables the authenticated user to unfollow a List.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/lists/manage-lists/api-reference/delete-users-id-followed-lists-list_id"
 */
@Throws(TwitterException::class)
fun Twitter.unfollowList(
    /**
     * The user ID who you are unfollowing a List on behalf of.
     */
    userId: Long,
    /**
     * The ID of the List that you would like the user id to unfollow.
     */
    listId: Long,
): BooleanResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    return V2ResponseFactory().createBooleanResponse(
        http.delete(
            conf.v2Configuration.baseURL + "users/${userId}/followed_lists/${listId}",
            emptyArray(),
            auth,
            this
        ),
        conf,
        "following"
    )
}

/**
 * Enables the authenticated user to pin a List.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/lists/manage-lists/api-reference/post-users-id-pinned-lists"
 */
@Throws(TwitterException::class)
fun Twitter.pinList(
    /**
     * The user ID who you are pinning a List on behalf of.
     */
    userId: Long,
    /**
     * The ID of the List that you would like the user id to pin.
     */
    listId: Long
): BooleanResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    val json = JSONObject()
    json.put("list_id", listId.toString())

    return V2ResponseFactory().createBooleanResponse(
        http.post(
            conf.v2Configuration.baseURL + "users/${userId}/pinned_lists",
            arrayOf(HttpParameter(json)),
            auth,
            this
        ),
        conf,
        "pinned"
    )
}

/**
 * Enables the authenticated user to unpin a List.
 *
 * @throws TwitterException when Twitter service or network is unavailable
 * @see "https://developer.twitter.com/en/docs/twitter-api/lists/manage-lists/api-reference/delete-users-id-pinned-lists-list_id"
 */
@Throws(TwitterException::class)
fun Twitter.unpinList(
    /**
     * The user ID who you are unpin a List on behalf of.
     */
    userId: Long,
    /**
     * The ID of the List that you would like the user id to unpin.
     */
    listId: Long,
): BooleanResponse {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    return V2ResponseFactory().createBooleanResponse(
        http.delete(
            conf.v2Configuration.baseURL + "users/${userId}/pinned_lists/${listId}",
            emptyArray(),
            auth,
            this
        ),
        conf,
        "pinned"
    )
}

