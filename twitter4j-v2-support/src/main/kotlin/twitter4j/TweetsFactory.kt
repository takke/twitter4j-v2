package twitter4j

import twitter4j.TwitterException
import twitter4j.conf.Configuration

class TweetsFactory {

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

}
