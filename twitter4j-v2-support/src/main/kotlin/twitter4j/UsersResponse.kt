package twitter4j

class UsersResponse : TwitterResponse {

    @Transient
    private var rateLimitStatus: RateLimitStatus? = null

    @Transient
    private var accessLevel = 0

    private var jsonObject: JSONObject

    // convert to json object
    val asJSONObject: JSONObject get() = jsonObject

    val users: List<User2> = mutableListOf()


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
        val users = users as MutableList
        users.clear()

        val includes = jsonObject.optJSONObject("includes")

        //--------------------------------------------------
        // create map of polls from includes.polls
        //--------------------------------------------------
        val pollsMap = V2Util.collectPolls(includes)

        //--------------------------------------------------
        // create map of users from includes.users
        //--------------------------------------------------
        val usersMap = V2Util.collectUsers(includes)


        // TODO includes.tweets, includes.places, includes.media ...

        //--------------------------------------------------
        // create tweets from data
        //--------------------------------------------------
        val dataArray = jsonObject.getJSONArray("data")
        for (i in 0 until dataArray.length()) {
            val data = dataArray.getJSONObject(i)

            users.add(User2.parse(data))
        }
    }

    override fun getRateLimitStatus(): RateLimitStatus? {
        return rateLimitStatus
    }

    override fun getAccessLevel(): Int {
        return accessLevel
    }

    override fun toString(): String {
        return "UsersResponse(rateLimitStatus=$rateLimitStatus, accessLevel=$accessLevel, users=$users)"
    }


}