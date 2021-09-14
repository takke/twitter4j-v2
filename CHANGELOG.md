Change Log
==========

v0.3.0 (2021.09.xx)
-------------------
- Support "GET /2/users/:id/following" (Twitter.getFollowingUsers)
- Support "GET /2/users/:id/followers" (Twitter.getFollowerUsers)
- Support "POST /2/users/:id/following" (Twitter.followUser)
- Support "DELETE /2/users/:source_user_id/following/:target_user_id" (Twitter.unfollowUser)
- Support "GET /2/users/:id/blocking" (Twitter.getBlockingUsers)
- Support "GET /2/tweets/:id/retweeted_by" (Twitter.getRetweetUsers)
- Support "POST /2/users/:id/retweets" (Twitter.retweet)
- Support "DELETE /2/users/:id/retweets/:source_tweet_id" (Twitter.unretweet)
- Support "GET /2/tweets/:id/liking_users" (Twitter.getLikingUsers)
- Support "GET /2/users/:id/liked_tweets" (Twitter.getLikedTweets)
- Support "POST /2/users/:id/likes" (Twitter.likeTweet)
- Support "DELETE /2/users/:id/likes/:tweet_id" (Twitter.unlikeTweet)
- Bump Kotlin 1.5.30

v0.2.1 (2021.05.12)
-------------------
- Support "GET /2/users/by" (Twitter.getUsersBy)
- Migrate repository from `bintray` to `github.io`
- Bump Kotlin 1.5.0

v0.2.0 (2020.08.13)
-------------------
- Change the default URLs to Twitter API v2 endpoints
- Update docs for Twitter API v2

v0.1.2 (2020.08.06)
-------------------
- Add `Configuration.v2Configuration.baseURL` to set another base URL
- Replace the method for storing json with Twitter4J style
- Add java example
- Rename `twitter4j-v2-support-example` to `twitter4j-v2-support-kotlin-example`
- Add vars to `Tweet`
  - repliedToTweetId, quotedTweetId, retweetId

v0.1.1 (2020.07.22)
-------------------
- Support "GET /labs/2/users" (Twitter.getUsers)
  - parse just a partial elements only

v0.1.0 (2020.07.20)
-------------------
- Initial release
- Support "GET /labs/2/tweets" (Twitter.getTweets)
  - parse just a partial elements only
