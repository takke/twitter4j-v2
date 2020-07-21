package twitter4j

import twitter4j.TwitterException
import twitter4j.conf.Configuration

class UsersFactory {

    @Throws(TwitterException::class)
    fun createUsersResponse(res: HttpResponse, conf: Configuration): UsersResponse {

        return try {
            val users = UsersResponse(res)

            // TODO implement JSON Store feature

            users
        } catch (ex: JSONException) {
            throw TwitterException(ex)
        }

    }

}
