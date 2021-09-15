Change Log
==========

v0.3.0 (2021.09.xx)
-------------------
- Search Tweets
  - Add Twitter.searchRecent() for "GET /2/tweets/search/recent"
  - Add Twitter.searchAll() for "GET /2/tweets/search/all"
- Tweet counts
  - Add Twitter.countRecent() for "GET /2/tweets/counts/recent"
  - Add Twitter.countAll() for "GET /2/tweets/counts/all"
- Timelines
  - Add Twitter.getUserTweets() for "GET /2/users/:id/tweets"
  - Add Twitter.getUserMentions() for "GET /2/users/:id/mentions"
- Retweets
  - Add Twitter.getRetweetUsers() for "GET /2/tweets/:id/retweeted_by"
  - Add Twitter.retweet() for "POST /2/users/:id/retweets"
  - Add Twitter.unretweet() for "DELETE /2/users/:id/retweets/:source_tweet_id"
- Likes
  - Add Twitter.getLikingUsers() for "GET /2/tweets/:id/liking_users"
  - Add Twitter.getLikedTweets() for "GET /2/users/:id/liked_tweets"
  - Add Twitter.likeTweet() for "POST /2/users/:id/likes"
  - Add Twitter.unlikeTweet() for "DELETE /2/users/:id/likes/:tweet_id"
- Follows
  - Add Twitter.getFollowingUsers() for "GET /2/users/:id/following"
  - Add Twitter.getFollowerUsers() for "GET /2/users/:id/followers"
  - Add Twitter.followUser() for "POST /2/users/:id/following"
  - Add Twitter.unfollowUser() for "DELETE /2/users/:source_user_id/following/:target_user_id"
- Blocks
  - Add Twitter.getBlockingUsers() for "GET /2/users/:id/blocking"
  - Add Twitter.blockUser() for "POST /2/users/:id/blocking"
  - Add Twitter.unblockUser() for "DELETE /2/users/:source_user_id/blocking/:target_user_id"
- Mutes
  - Add Twitter.muteUser() for "POST /2/users/:id/muting"
  - Add Twitter.unmuteUser() for "DELETE /2/users/:source_user_id/muting/:target_user_id"
- Bump Kotlin 1.5.30

v0.2.1 (2021.05.12)
-------------------
- Add Twitter.getUsersBy() for "GET /2/users/by"
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
- Add Twitter.getUsers() for "GET /labs/2/users"
  - parse just a partial elements only

v0.1.0 (2020.07.20)
-------------------
- Initial release
- Add Twitter.getTweets() for "GET /labs/2/tweets"
  - parse just a partial elements only
