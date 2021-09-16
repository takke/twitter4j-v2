package twitter4j

import java.text.SimpleDateFormat
import java.util.*
import java.util.TimeZone

object V2Util {

    fun collectPolls(includes: JSONObject?, pollsMap: HashMap<Long, Poll>) {

        includes?.optJSONArray("polls")?.let { polls ->
            for (i in 0 until polls.length()) {
                val pollString = polls.getString(i)

                val poll = Poll(JSONObject(pollString)).also {
                    // original json
                    it.jsonText = pollString
                }

                pollsMap[poll.id] = poll
            }
        }
    }

    fun collectUsers(includes: JSONObject?, usersMap: HashMap<Long, User2>) {

        includes?.optJSONArray("users")?.let { users ->
            for (i in 0 until users.length()) {
                val user = User2.parse(users.getJSONObject(i))
                usersMap[user.id] = user
            }
        }
    }

    fun collectTweets(includes: JSONObject?, tweetsMap: HashMap<Long, Tweet>) {

        includes?.optJSONArray("tweets")?.let { tweets ->
            for (i in 0 until tweets.length()) {
                val tweet = Tweet.parse(tweets.getJSONObject(i))
                tweetsMap[tweet.id] = tweet
            }
        }
    }

    fun collectErrors(jsonObject: JSONObject, errors: List<ErrorInfo>) {
        val errorsArray = jsonObject.optJSONArray("errors")
        if (errorsArray != null) {
            val errors1 = errors as MutableList
            for (i in 0 until errorsArray.length()) {
                errors1.add(ErrorInfo(errorsArray.getJSONObject(i)))
            }
        }
    }

    fun parseMeta(jsonObject: JSONObject): Meta? {
        if (jsonObject.has("meta")) {
            val metaObject = jsonObject.optJSONObject("meta")
            return Meta(
                metaObject.getInt("result_count"),
                metaObject.optString("previous_token", null),
                metaObject.optString("next_token", null),
                metaObject.optLong("oldest_id"),
                metaObject.optLong("newest_id")
            )
        }
        return null
    }

    fun dateToISO8601(date: Date): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        sdf.timeZone = TimeZone.getTimeZone("GMT")
        return sdf.format(date)
    }

    fun parseISO8601Date(key: String, data: JSONObject?): Date? {
        return ParseUtil.getDate(key, data, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    }

}
