package twitter4j

class TweetsResponse : TwitterResponse {

    @Transient
    private var rateLimitStatus: RateLimitStatus? = null

    @Transient
    private var accessLevel = 0

    val tweets: List<Tweet> = mutableListOf()

    // includes.polls
    val pollsMap = HashMap<Long, Poll>()

    // includes.media
    val mediaMap = HashMap<MediaKey, Media>()

    // includes.users
    val usersMap = HashMap<Long, User2>()

    // includes.tweets
    val tweetsMap = HashMap<Long, Tweet>()

    // includes.places
    val placesMap = HashMap<String, Place2>()

    // meta
    var meta: Meta? = null

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
        V2Util.collectPlaces(includes, placesMap)
        V2Util.collectTweets(includes, tweetsMap)
        V2Util.collectMediaKeys(includes, mediaMap)

        // TODO includes.places ...

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
        // meta
        //--------------------------------------------------
        meta = V2Util.parseMeta(jsonObject)

        //--------------------------------------------------
        // errors
        //--------------------------------------------------
        V2Util.collectErrors(jsonObject, errors)

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
        return "TweetsResponse(rateLimitStatus=$rateLimitStatus, accessLevel=$accessLevel, tweets=$tweets, pollsMap=$pollsMap, mediaMap=$mediaMap, usersMap=$usersMap, tweetsMap=$tweetsMap, placesMap=$placesMap, meta=$meta, errors=$errors)"
    }


}