package twitter4j

import twitter4j.conf.Configuration

class V2ResponseFactory {

    /**
     * parse simple boolean response on json like:
     * {
     *   "data": {
     *     "${key}": true
     *   }
     * }
     */
    @Throws(TwitterException::class)
    fun parseBooleanResponse(res: HttpResponse, conf: Configuration, key: String): BooleanResponse {

        try {
            if (conf.isJSONStoreEnabled) {
                TwitterObjectFactory.clearThreadLocalMap()
            }

            return BooleanResponse(res, conf.isJSONStoreEnabled, key)
        } catch (ex: JSONException) {
            throw TwitterException(ex)
        }
    }

}
