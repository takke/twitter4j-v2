package twitter4j

import twitter4j.TwitterException
import twitter4j.conf.Configuration

class UsersFactory {

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

}
