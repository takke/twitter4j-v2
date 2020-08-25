package twitter4j

import java.util.HashMap

class TweetsResponse : TwitterResponse {

    @Transient
    private var rateLimitStatus: RateLimitStatus? = null

    @Transient
    private var accessLevel = 0

    val tweets: List<Tweet> = mutableListOf()

    // includes.polls
    val pollsMap = HashMap<Long, Poll>()

    // includes.users
    val usersMap = HashMap<Long, User2>()

    // includes.tweets
    val tweetsMap = HashMap<Long, Tweet>()

    // errors
    val errors: List<ErrorInfo> = mutableListOf()


    constructor(res: HttpResponse, isJSONStoreEnabled: Boolean) {
        rateLimitStatus = RateLimitStatusJSONImpl.createFromResponseHeader(res)
        accessLevel = ParseUtil.toAccessLevel(res)

        parse(res.asJSONObject(), isJSONStoreEnabled)
    }

    constructor(json: JSONObject, isJSONStoreEnabled: Boolean = false) {

        parse(json, isJSONStoreEnabled)
    }

    private fun parse(jsonObject: JSONObject, isJSONStoreEnabled: Boolean) {
        val tweets = tweets as MutableList
        tweets.clear()

        val includes = jsonObject.optJSONObject("includes")

        //--------------------------------------------------
        // create maps from includes
        //--------------------------------------------------
        V2Util.collectPolls(includes, pollsMap)
        V2Util.collectUsers(includes, usersMap)
        V2Util.collectTweets(includes, tweetsMap)

        // TODO includes.places, includes.media ...

        //--------------------------------------------------
        // create tweets from data
        //--------------------------------------------------
        val dataArray = jsonObject.optJSONArray("data")
        if (dataArray != null) {
            for (i in 0 until dataArray.length()) {
                val data = dataArray.getJSONObject(i)

                tweets.add(Tweet.parse(data))
            }
        }

        //--------------------------------------------------
        // errors
        //--------------------------------------------------
        val errorsArray = jsonObject.optJSONArray("errors")
        if (errorsArray != null) {
            val errors = errors as MutableList
            for (i in 0 until errorsArray.length()) {
                errors.add(ErrorInfo(errorsArray.getJSONObject(i)))
            }
        }

        if (isJSONStoreEnabled) {
            TwitterObjectFactory.registerJSONObject(this, jsonObject)
        }
    }

    override fun getRateLimitStatus(): RateLimitStatus? {
        return rateLimitStatus
    }

    override fun getAccessLevel(): Int {
        return accessLevel
    }

    override fun toString(): String {
        return "TweetsResponse(rateLimitStatus=$rateLimitStatus, accessLevel=$accessLevel, tweets=$tweets, pollsMap=$pollsMap, usersMap=$usersMap, tweetsMap=$tweetsMap)"
    }


}