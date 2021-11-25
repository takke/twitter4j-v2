package twitter4j

import twitter4j.conf.Configuration

class V2ResponseFactory {

    @Throws(TwitterException::class)
    fun createTweetsResponse(res: HttpResponse, conf: Configuration): TweetsResponse {

        try {
            if (conf.isJSONStoreEnabled) {
                TwitterObjectFactory.clearThreadLocalMap()
            }

            return TweetsResponse(res, conf.isJSONStoreEnabled)
        } catch (ex: JSONException) {
            throw TwitterException(ex)
        }
    }

    @Throws(TwitterException::class)
    fun createUsersResponse(res: HttpResponse, conf: Configuration): UsersResponse {

        try {
            if (conf.isJSONStoreEnabled) {
                TwitterObjectFactory.clearThreadLocalMap()
            }

            return UsersResponse(res, conf.isJSONStoreEnabled)
        } catch (ex: JSONException) {
            throw TwitterException(ex)
        }
    }

    /**
     * parse simple boolean response on json like:
     * {
     *   "data": {
     *     "${key}": true
     *   }
     * }
     */
    @Throws(TwitterException::class)
    fun createBooleanResponse(res: HttpResponse, conf: Configuration, key: String): BooleanResponse {

        try {
            if (conf.isJSONStoreEnabled) {
                TwitterObjectFactory.clearThreadLocalMap()
            }

            return BooleanResponse(res, conf.isJSONStoreEnabled, key)
        } catch (ex: JSONException) {
            throw TwitterException(ex)
        }
    }

    @Throws(TwitterException::class)
    fun createFollowResponse(res: HttpResponse, conf: Configuration): FollowResponse {

        try {
            if (conf.isJSONStoreEnabled) {
                TwitterObjectFactory.clearThreadLocalMap()
            }

            return FollowResponse(res, conf.isJSONStoreEnabled)
        } catch (ex: JSONException) {
            throw TwitterException(ex)
        }
    }

    @Throws(TwitterException::class)
    fun createCountsResponse(res: HttpResponse, conf: Configuration): CountsResponse {

        try {
            if (conf.isJSONStoreEnabled) {
                TwitterObjectFactory.clearThreadLocalMap()
            }

            return CountsResponse(res, conf.isJSONStoreEnabled)
        } catch (ex: JSONException) {
            throw TwitterException(ex)
        }
    }

    @Throws(TwitterException::class)
    fun createSpacesResponse(res: HttpResponse, conf: Configuration): SpacesResponse {

        try {
            if (conf.isJSONStoreEnabled) {
                TwitterObjectFactory.clearThreadLocalMap()
            }

            return SpacesResponse(res, conf.isJSONStoreEnabled)
        } catch (ex: JSONException) {
            throw TwitterException(ex)
        }
    }

    @Throws(TwitterException::class)
    fun createListsResponse(res: HttpResponse, conf: Configuration): ListsResponse {

        try {
            if (conf.isJSONStoreEnabled) {
                TwitterObjectFactory.clearThreadLocalMap()
            }

            return ListsResponse(res, conf.isJSONStoreEnabled)
        } catch (ex: JSONException) {
            throw TwitterException(ex)
        }
    }

    @Throws(TwitterException::class)
    fun createCreateTweetResponse(res: HttpResponse, conf: Configuration): CreateTweetResponse {

        try {
            if (conf.isJSONStoreEnabled) {
                TwitterObjectFactory.clearThreadLocalMap()
            }

            return CreateTweetResponse(res, conf.isJSONStoreEnabled)
        } catch (ex: JSONException) {
            throw TwitterException(ex)
        }
    }

}
