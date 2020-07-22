package twitter4j

import java.util.HashMap

class TweetsResponse : TwitterResponse {

    @Transient
    private var rateLimitStatus: RateLimitStatus? = null

    @Transient
    private var accessLevel = 0

    private var jsonObject: JSONObject

    // convert to json object
    val asJSONObject: JSONObject get() = jsonObject

    val tweets: List<Tweet> = mutableListOf()

    // includes.polls
    val pollsMap = HashMap<Long, Poll>()

    // includes.users
    val usersMap = HashMap<Long, User2>()

    // includes.tweets
    val tweetsMap = HashMap<Long, Tweet>()


    constructor(res: HttpResponse) {
        rateLimitStatus = RateLimitStatusJSONImpl.createFromResponseHeader(res)
        accessLevel = ParseUtil.toAccessLevel(res)
        jsonObject = res.asJSONObject()

        parse()
    }

    constructor(json: JSONObject) {
        jsonObject = json

        parse()
    }

    private fun parse() {
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
        val dataArray = jsonObject.getJSONArray("data")
        for (i in 0 until dataArray.length()) {
            val data = dataArray.getJSONObject(i)

            val t = Tweet.parse(data)

            tweets.add(t)
        }
    }

    override fun getRateLimitStatus(): RateLimitStatus? {
        return rateLimitStatus
    }

    override fun getAccessLevel(): Int {
        return accessLevel
    }

    override fun toString(): String {
        return "TweetsResponse(rateLimitStatus=$rateLimitStatus, accessLevel=$accessLevel, tweets=$tweets)"
    }


}