package twitter4j

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

}
