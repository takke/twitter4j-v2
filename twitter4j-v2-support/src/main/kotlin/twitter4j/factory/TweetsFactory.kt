package twitter4j.factory

import twitter4j.HttpResponse
import twitter4j.JSONException
import twitter4j.TweetsResponse
import twitter4j.TwitterException
import twitter4j.conf.Configuration

class TweetsFactory {

    @Throws(TwitterException::class)
    fun createTweetsResponse(res: HttpResponse, conf: Configuration): TweetsResponse {

        return try {
            val tweets = TweetsResponse(res)

            // TODO implement JSON Store feature

            tweets
        } catch (ex: JSONException) {
            throw TwitterException(ex)
        }

    }

}
