Change Log
==========

v1.3.2 (2022.08.xx)
-------------------
- Add variants to media (animated_gif)

v1.3.1 (2022.08.26)
-------------------
- Add variants to media
- Add alt_text to media
- Add `Tweet.hashtags` and `Tweet.mentions`
- Bump Kotlin 1.7.10

v1.3.0 (2022.07.05)
-------------------
- Add media support: `Tweet.mediaKeys`, `TweetResponse.mediaMap`, `Media`, `Photo`, `AnimatedGif` and `Video`
- Introduce `PaginationToken` for paging
- `User2.username` -> `User2.screenName`

v1.2.1 (2022.06.29)
-------------------
- Fix incorrect end-point of `Twitter.countAll`
- Fix problem when `expanded_url` or `display_url` fields are missing.
- Bump Kotlin 1.7.0

v1.2.0 (2022.05.21)
-------------------
- Add Twitter.getReverseChronologicalTimeline() for "GET /2/users/:id/timelines/reverse_chronological"
- Add exclude parameter to Twitter.getQuoteTweets()
- Bump Kotlin 1.6.21

v1.1.1 (2022.05.11)
-------------------
- Add Twitter.getMe() for "GET /2/users/me"
- Add Tweet.conversationId
- Fix issue of polls (#15)
- Bump Kotlin 1.6.0

v1.1.0 (2022.03.27)
-------------------
- Add OAuth2TokenProvider for OAuth 2.0 PKCE
- Bookmarks
  - Add Twitter.getBookmarks() for "GET /2/users/:id/bookmarks"
  - Add Twitter.addBookmark() for "POST /2/users/:id/bookmarks"
  - Add Twitter.deleteBookmark() for "DELETE /2/users/:id/bookmarks/:tweet_id"

v1.0.5 (2022.03.24)
-------------------
- Quote Tweets
  - Add Twitter.getQuoteTweets() for "GET /2/tweets/:id/quote_tweets"
- Add `place` object
- Bump Gradle 7.4.1

v1.0.4 (2021.12.21)
-------------------
- fix: invalid type of `taggedUserIds, quoteTweetId, excludeReplyUserIds` in createTweet()

v1.0.3 (2021.12.11)
-------------------
- fix: invalid type of `mediaIds` in createTweet()

v1.0.2 (2021.12.03)
-------------------
- fix: invalid type of `in_reply_to_tweet_id` in createTweet()

v1.0.1 (2021.11.30)
-------------------
- fix: replace pagination_token by next_token for searchRecent() and searchAll()

v1.0.0 (2021.11.26)
-------------------
- Lists lookup
  - Add Twitter.getList() for "GET /2/lists/:id"
  - Add Twitter.getOwnedLists() for "GET /2/users/:id/owned_lists"
- Lists Tweets lookup
  - Add Twitter.getListTweets() for "GET /2/lists/:id/tweets"
- List members
  - Add Twitter.getListMembers() for "GET /2/lists/:id/members"
  - Add Twitter.getListMemberships() for "GET /2/users/:id/list_memberships"
- Lists follows
  - Add Twitter.getListFollowers() for "GET /2/lists/:id/followers"
  - Add Twitter.getFollowedLists() for "GET /2/users/:id/followed_lists"
- Pinned Lists
  - Add Twitter.getPinnedLists() for "GET /2/users/:id/pinned_lists"
- Bump Kotlin 1.6.0

v0.4.0 (2021.11.12)
-------------------
- Manage Tweets
  - Add Twitter.createTweet() for "POST /2/tweets"
  - Add Twitter.deleteTweet() for "DELETE /2/tweets/:id"

v0.3.2 (2021.10.04)
-------------------
- Manage Lists
  - Add Twitter.createList() for "POST /2/lists"
  - Add Twitter.deleteList() for "DELETE /2/lists/:id"
- Manage List members
  - Add Twitter.addListMember() for "POST /2/lists/:id/members"
  - Add Twitter.deleteListMember() for "DELETE /2/lists/:id/members/:user_id"
- Manage List follows
  - Add Twitter.followList() for "POST /2/users/:id/followed_lists"
  - Add Twitter.unfollowList() for "DELETE /2/users/:id/followed_lists/:list_id"
- Manage pinned List
  - Add Twitter.pinList() for "POST /2/users/:id/pinned_lists"
  - Add Twitter.unpinList() for "DELETE /2/users/pinned_lists/:list_id"

v0.3.1 (2021.09.24)
-------------------
- Mutes
  - Add Twitter.getMutingUsers() for "GET /2/users/:id/muting"

v0.3.0 (2021.09.17)
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
- Spaces lookup
  - Add Twitter.getSpaces() for "GET /2/spaces"
  - Add Twitter.getSpacesByCreatorIds() for "GET /2/spaces/search"
- Search Spaces
  - Add Twitter.searchSpaces() for "GET /2/spaces/search"
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
