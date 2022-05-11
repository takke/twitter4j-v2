[![v2](https://img.shields.io/endpoint?url=https%3A%2F%2Ftwbadges.glitch.me%2Fbadges%2Fv2)](https://developer.twitter.com/en/docs/twitter-api)
![CI](https://github.com/takke/twitter4j-v2/workflows/CI/badge.svg)

twitter4j-v2
============

twitter4j-v2 is a simple wrapper for [Twitter API v2](https://developer.twitter.com/en/docs/twitter-api/early-access) that is designed to be used with [Twitter4J](https://github.com/Twitter4J/Twitter4J).

More information is [here](https://github.com/takke/twitter4j-v2/wiki/Design-Policy).


How do I use it?
----------------

### Setup

##### Dependencies
```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation "io.github.takke:jp.takke.twitter4j-v2:$twitter4jV2Version"
}

// the old way: until v1.0.3
repositories {
    maven {
        url 'https://takke.github.io/maven'
        content {
            includeGroup "jp.takke.twitter4j-v2"
        }
    }
}
dependencies {
    implementation "jp.takke.twitter4j-v2:twitter4j-v2-support:$twitter4jV2SupportVersion"
}
```

see [search.maven.org](https://search.maven.org/artifact/io.github.takke/jp.takke.twitter4j-v2)

### Example

```kotlin
val twitter: Twitter = yourTwitterInstanceProvider.get()
val tweetId = 656974073491156992L

val tweetsResponse = twitter.getTweets(tweetId,
                            mediaFields = null,
                            placeFields = null,
                            pollFields = "duration_minutes,end_datetime,id,options,voting_status",
                            tweetFields = "id,public_metrics",
                            userFields = null,
                            expansions = "attachments.poll_ids")

println(tweetsResponse.tweets[0].poll(tweetsResponse.pollsMap))
// or
println(tweetsResponse.pollsMap[tweetsResponse.tweets[0].pollId])

// => Poll(id=656974073113636864, options=[PollOption(position=1, label=Roboto, votes=391), 
//    PollOption(position=2, label=San Francisco, votes=691)], 
//    votingStatus=CLOSED, endDatetime=Fri Oct 23 08:23:19 GMT+09:00 2015, durationMinutes=1440)
```

See more examples in [kotlin](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support-kotlin-example/src/main/kotlin/twitter4j_v2_support_example/) and [java](https://github.com/takke/twitter4j-v2/tree/master/twitter4j-v2-support-java-example/src/main/java/twitter4j_v2_support_java_example).

Requirements
------------
- Kotlin 1.6.21
- Twitter4J 4.0.7


Supported APIs
--------------

| | |  End-point  |  twitter4j-v2 method  |
| ---- | ---- | ---- | ---- |
| **Tweets** | Tweet Lookup  | [GET /2/tweets](https://developer.twitter.com/en/docs/twitter-api/tweets/lookup/api-reference/get-tweets "Returns a variety of information about the Tweet specified by the requested ID or list of IDs.")  |  [Twitter.getTweets()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/getTweets.kt)  |
| | Manage Tweets            | [POST /2/tweets](https://developer.twitter.com/en/docs/twitter-api/tweets/manage-tweets/api-reference/post-tweets "Creates a Tweet on behalf of an authenticated user.") |  [Twitter.createTweet()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/ManageTweetsEx.kt)  |
| |                          | [DELETE /2/tweets/:id](https://developer.twitter.com/en/docs/twitter-api/tweets/manage-tweets/api-reference/delete-tweets-id "Allows a user or authenticated user ID to delete a Tweet.") |  [Twitter.deleteTweet()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/ManageTweetsEx.kt)  |
| | Timelines                | [GET /2/users/:id/tweets](https://developer.twitter.com/en/docs/twitter-api/tweets/timelines/api-reference/get-users-id-tweets "Returns Tweets composed by a single user, specified by the requested user ID.")|  [Twitter.getUserTweets()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/TimelinesEx.kt)  |
| |                          | [GET /2/users/:id/mentions](https://developer.twitter.com/en/docs/twitter-api/tweets/timelines/api-reference/get-users-id-mentions "Returns Tweets mentioning a single user specified by the requested user ID. ") | [Twitter.getUserMentions()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/TimelinesEx.kt) |
| | Search Tweets            | [GET /2/tweets/search/recent](https://developer.twitter.com/en/docs/twitter-api/tweets/search/api-reference/get-tweets-search-recent "The recent search endpoint returns Tweets from the last seven days that match a search query.")  |  [Twitter.searchRecent()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/SearchEx.kt)  |
| |                          | [GET /2/tweets/search/all](https://developer.twitter.com/en/docs/twitter-api/tweets/search/api-reference/get-tweets-search-all "The full-archive search endpoint returns the complete history of public Tweets matching a search query; since the first Tweet was created March 26, 2006.")  |  [Twitter.searchAll()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/SearchEx.kt)  |
| | Tweet counts             | [GET /2/tweets/counts/recent](https://developer.twitter.com/en/docs/twitter-api/tweets/counts/api-reference/get-tweets-counts-recent "The recent Tweet counts endpoint returns count of Tweets from the last seven days that match a search query.") |  [Twitter.countRecent()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/CountsEx.kt)  |
| |                          | [GET /2/tweets/counts/all](https://developer.twitter.com/en/docs/twitter-api/tweets/counts/api-reference/get-tweets-counts-all "The full-archive search endpoint returns the complete history of public Tweets matching a search query; since the first Tweet was created March 26, 2006.") |  [Twitter.countAll()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/CountsEx.kt)  |
| | Filtered stream          | [GET /2/tweets/search/stream](https://developer.twitter.com/en/docs/twitter-api/tweets/filtered-stream/api-reference/get-tweets-search-stream "Streams Tweets in real-time based on a specific set of filter rules.") |  N/A [#1](https://github.com/takke/twitter4j-v2/issues/1) |
| | Volume stream            | [GET /2/tweets/sample/stream](https://developer.twitter.com/en/docs/twitter-api/tweets/sampled-stream/api-reference/get-tweets-sample-stream "Streams about 1% of all Tweets in real-time.") |  N/A [#1](https://github.com/takke/twitter4j-v2/issues/1) |
| | Retweets lookup          | [GET /2/tweets/:id/retweeted_by](https://developer.twitter.com/en/docs/twitter-api/tweets/retweets/api-reference/get-tweets-id-retweeted_by "Allows you to get information about who has Retweeted a Tweet.") |  [Twitter.getRetweetUsers()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/RetweetsEx.kt)  |
| | Quote Tweets lookup      | [GET /2/tweets/:id/quote_tweets](https://developer.twitter.com/en/docs/twitter-api/tweets/quote-tweets/api-reference/get-tweets-id-quote_tweets "Returns Quote Tweets for a Tweet specified by the requested Tweet ID.") |  [Twitter.getQuoteTweets()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/QuoteTweetsEx.kt)  |
| | Manage Retweets          | [POST /2/users/:id/retweets](https://developer.twitter.com/en/docs/twitter-api/tweets/retweets/api-reference/post-users-id-retweets "Causes the user ID identified in the path parameter to Retweet the target Tweet.") |  [Twitter.retweet()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/RetweetsEx.kt)  |
| |                          | [DELETE /2/users/:id/retweets/:source_tweet_id](https://developer.twitter.com/en/docs/twitter-api/tweets/retweets/api-reference/delete-users-id-retweets-tweet_id "Allows a user or authenticated user ID to remove the Retweet of a Tweet.") |  [Twitter.unretweet()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/RetweetsEx.kt)  |
| | Likes lookup             | [GET /2/tweets/:id/liking_users](https://developer.twitter.com/en/docs/twitter-api/tweets/likes/api-reference/get-tweets-id-liking_users "Allows you to get information about a Tweet’s liking users.") |  [Twitter.getLikingUsers()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/LikesEx.kt)  |
| |                          | [GET /2/users/:id/liked_tweets](https://developer.twitter.com/en/docs/twitter-api/tweets/likes/api-reference/get-users-id-liked_tweets "Allows you to get information about a user’s liked Tweets.") |  [Twitter.getLikedTweets()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/LikesEx.kt)  |
| | Manage Likes             | [POST /2/users/:id/likes](https://developer.twitter.com/en/docs/twitter-api/tweets/likes/api-reference/post-users-id-likes "Causes the user ID identified in the path parameter to Like the target Tweet.") |  [Twitter.likeTweet()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/LikesEx.kt)  |
| |                          | [DELETE /2/users/:id/likes/:tweet_id](https://developer.twitter.com/en/docs/twitter-api/tweets/likes/api-reference/delete-users-id-likes-tweet_id "Allows a user or authenticated user ID to unlike a Tweet.") |  [Twitter.unlikeTweet()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/LikesEx.kt)  |
| | Bookmarks                | [GET /2/users/:id/bookmarks](https://developer.twitter.com/en/docs/twitter-api/tweets/bookmarks/api-reference/get-users-id-bookmarks "Allows you to get an authenticated user's 800 most recent bookmarked Tweets.") | [Twitter.getBookmarks()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/BookmarksEx.kt) |
| |                          | [POST /2/users/:id/bookmarks](https://developer.twitter.com/en/docs/twitter-api/tweets/bookmarks/api-reference/post-users-id-bookmarks "Causes the user ID identified in the path parameter to Bookmark the target Tweet provided in the request body.") | [Twitter.addBookmark()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/BookmarksEx.kt) |
| |                          | [DELETE /2/users/:id/bookmarks/:tweet_id](https://developer.twitter.com/en/docs/twitter-api/tweets/bookmarks/api-reference/delete-users-id-bookmarks-tweet_id "Allows a user or authenticated user ID to remove a Bookmark of a Tweet.") | [Twitter.deleteBookmark()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/BookmarksEx.kt) |
| | Hide replies             | [PUT /2/tweets/:id/hidden](https://developer.twitter.com/en/docs/twitter-api/tweets/hide-replies/api-reference/put-tweets-id-hidden "Hides or unhides a reply to a Tweet.") |  N/A *(Twitter4J v4.0.7 does not support PUT methods that contain json parameters.)*  |
| **Users** | Users lookup   | [GET /2/users](https://developer.twitter.com/en/docs/twitter-api/users/lookup/api-reference/get-users "Returns a variety of information about one or more users specified by the requested IDs.")  |  [Twitter.getUsers()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/UsersEx.kt)  |
| |                          | [GET /2/users/by](https://developer.twitter.com/en/docs/twitter-api/users/lookup/api-reference/get-users-by "Returns a variety of information about one or more users specified by their usernames.") | [Twitter.getUsersBy()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/UsersEx.kt)  |
| |                          | [GET /2/users/me](https://developer.twitter.com/en/docs/twitter-api/users/lookup/api-reference/get-users-me "Returns information about an authorized user.") | [Twitter.getMe()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/UsersEx.kt)  |
| | Follows lookup           | [GET /2/users/:id/following](https://developer.twitter.com/en/docs/twitter-api/users/follows/api-reference/get-users-id-following "Returns a list of users the specified user ID is following.") |  [Twitter.getFollowingUsers()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/FollowsEx.kt)  |
| |                          | [GET /2/users/:id/followers](https://developer.twitter.com/en/docs/twitter-api/users/follows/api-reference/get-users-id-followers "Returns a list of users who are followers of the specified user ID.") |  [Twitter.getFollowerUsers()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/FollowsEx.kt)  |
| | Manage follows           | [POST /2/users/:id/following](https://developer.twitter.com/en/docs/twitter-api/users/follows/api-reference/post-users-source_user_id-following "Allows a user ID to follow another user.") |  [Twitter.followUser()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/FollowsEx.kt)  |
| |                          | [DELETE /2/users/:source_user_id/following/:target_user_id](https://developer.twitter.com/en/docs/twitter-api/users/follows/api-reference/delete-users-source_id-following "Allows a user ID to unfollow another user.") |  [Twitter.unfollowUser()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/FollowsEx.kt)  |
| | Blocks lookup            | [GET /2/users/:id/blocking](https://developer.twitter.com/en/docs/twitter-api/users/blocks/api-reference/get-users-blocking "Returns a list of users who are blocked by the specified user ID.") |  [Twitter.getBlockingUsers()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/BlocksEx.kt)  |
| | Manage blocks            | [POST /2/users/:id/blocking](https://developer.twitter.com/en/docs/twitter-api/users/blocks/api-reference/post-users-user_id-blocking "Causes the user (in the path) to block the target user.") |  [Twitter.blockUser()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/BlocksEx.kt)  |
| |                          | [DELETE /2/users/:source_user_id/blocking/:target_user_id](https://developer.twitter.com/en/docs/twitter-api/users/blocks/api-reference/delete-users-user_id-blocking "Allows a user or authenticated user ID to unblock another user.") |  [Twitter.unblockUser()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/BlocksEx.kt)  |
| | Mutes lookup             | [GET /2/users/:id/muting](https://developer.twitter.com/en/docs/twitter-api/users/mutes/api-reference/get-users-muting "Returns a list of users who are muted by the specified user ID.") | [Twitter.getMutingUsers()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/MutesEx.kt) |
| | Manage mutes             | [POST /2/users/:id/muting](https://developer.twitter.com/en/docs/twitter-api/users/mutes/api-reference/post-users-user_id-muting "Allows an authenticated user ID to mute the target user.") |  [Twitter.muteUser()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/MutesEx.kt)  |
| |                          | [DELETE /2/users/:source_user_id/muting/:target_user_id](https://developer.twitter.com/en/docs/twitter-api/users/mutes/api-reference/delete-users-user_id-muting "Allows an authenticated user ID to unmute the target user.") |  [Twitter.unmuteUser()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/MutesEx.kt)  |
| **Spaces** | Spaces lookup | [GET /2/spaces](https://developer.twitter.com/en/docs/twitter-api/spaces/lookup/api-reference/get-spaces "Returns details about multiple Spaces. ") |  [Twitter.getSpaces()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/SpacesLookupEx.kt)  |
| |                          | [GET /2/spaces/by/creator_ids](https://developer.twitter.com/en/docs/twitter-api/spaces/lookup/api-reference/get-spaces-by-creator-ids "Returns live or scheduled Spaces created by the specified user IDs.") |  [Twitter.getSpacesByCreatorIds()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/SpacesLookupEx.kt)  |
| | Spaces search            | [GET /2/spaces/search](https://developer.twitter.com/en/docs/twitter-api/spaces/search/api-reference/get-spaces-search "Return live or scheduled Spaces matching your specified search terms. ") |  [Twitter.searchSpaces()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/SearchSpacesEx.kt)  |
| **Lists** | Lists lookup   | [GET /2/lists/:id](https://developer.twitter.com/en/docs/twitter-api/lists/list-lookup/api-reference/get-lists-id "Lookup a specific list by ID") | [Twitter.getList()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/ListsEx.kt) |
| |                          | [GET /2/users/:id/owned_lists](https://developer.twitter.com/en/docs/twitter-api/lists/list-lookup/api-reference/get-users-id-owned_lists "Lookup a user's owned List") | [Twitter.getOwnedLists()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/ListsEx.kt) |
| | Manage Lists             | [POST /2/lists](https://developer.twitter.com/en/docs/twitter-api/lists/manage-lists/api-reference/post-lists "Creates a new List on behalf of an authenticated user") |  [Twitter.createList()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/ListsEx.kt)  |
| |                          | [DELETE /2/lists/:id](https://developer.twitter.com/en/docs/twitter-api/lists/manage-lists/api-reference/delete-lists-id "Deletes a List the authenticated user owns") |  [Twitter.deleteList()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/ListsEx.kt)  |
| |                          | [PUT /2/lists/:id](https://developer.twitter.com/en/docs/twitter-api/lists/manage-lists/api-reference/put-lists-id "Updates the metadata for a List the authenticated user owns") | N/A (Twitter4J v4.0.7 does not support PUT methods that contain json parameters.) |
| | Lists Tweets lookup      | [GET /2/lists/:id/tweets](https://developer.twitter.com/en/docs/twitter-api/lists/list-tweets/api-reference/get-lists-id-tweets "Lookup Tweets from a specified List") | [Twitter.getListTweets()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/ListsEx.kt) | |
| | List members             | [GET /2/lists/:id/members](https://developer.twitter.com/en/docs/twitter-api/lists/list-members/api-reference/get-lists-id-members "Returns a list of members from a specified List") | [Twitter.getListMembers()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/ListsEx.kt) |
| |                          | [GET /2/users/:id/list_memberships](https://developer.twitter.com/en/docs/twitter-api/lists/list-members/api-reference/get-users-id-list_memberships "Returns all Lists a specified user is a member of") | [Twitter.getListMemberships()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/ListsEx.kt) |
| |                          | [POST /2/lists/members](https://developer.twitter.com/en/docs/twitter-api/lists/manage-lists/api-reference/post-lists-id-members "Add a member to a List that the authenticated user owns") | [Twitter.addListMember()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/ListsEx.kt) |
| |                          | [DELETE /2/lists/members/:user_id](https://developer.twitter.com/en/docs/twitter-api/lists/manage-lists/api-reference/delete-lists-id-members-user_id "Removes a member from a List the authenticated user owns") | [Twitter.deleteListMember()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/ListsEx.kt) |
| | Lists follows            | [GET /2/lists/:id/followers](https://developer.twitter.com/en/docs/twitter-api/lists/list-follows/api-reference/get-lists-id-followers "Returns all followers of a specified List") | [Twitter.getListFollowers()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/ListsEx.kt) |
| |                          | [GET /2/users/:id/followed_lists](https://developer.twitter.com/en/docs/twitter-api/lists/list-follows/api-reference/get-users-id-followed_lists "Returns all Lists a specified user follows") | [Twitter.getFollowedLists()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/ListsEx.kt) |
| |                          | [POST /2/users/:id/followed_lists](https://developer.twitter.com/en/docs/twitter-api/lists/manage-lists/api-reference/post-users-id-followed-lists "Follows a List on behalf of an authenticated user") | [Twitter.followList()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/ListsEx.kt) |
| |                          | [DELETE /2/users/followed_lists/:list_id](https://developer.twitter.com/en/docs/twitter-api/lists/manage-lists/api-reference/delete-users-id-followed-lists-list_id "Unfollows a List on behalf of an authenticated user") | [Twitter.unfollowList()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/ListsEx.kt) |
| | Pinned Lists             | [GET /2/users/:id/pinned_lists](https://developer.twitter.com/en/docs/twitter-api/lists/pinned-lists/api-reference/get-users-id-pinned_lists "Returns the pinned Lists of the authenticated user") | [Twitter.getPinnedLists()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/ListsEx.kt) |
| |                          | [POST /2/users/:id/pinned_lists](https://developer.twitter.com/en/docs/twitter-api/lists/manage-lists/api-reference/post-users-id-pinned-lists "Pins a List on behalf of an authenticated user") | [Twitter.pinList()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/ListsEx.kt) |
| |                          | [DELETE /2/users/pinned_lists/:list_id](https://developer.twitter.com/en/docs/twitter-api/lists/manage-lists/api-reference/delete-users-id-pinned-lists-list_id "Unpins a List on behalf of an authenticated user") | [Twitter.unpinList()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/ListsEx.kt) |

v2 APIs table from [Twitter API endpoint map](https://developer.twitter.com/en/docs/twitter-api/migrate/twitter-api-endpoint-map)

See also [Response Field Mapping](https://github.com/takke/twitter4j-v2/wiki/Response-Field-Mapping)



Developed By
------------
TAKEUCHI Hiroaki (<a href="https://twitter.com/takke">@takke</a>) - takke30 at gmail.com


License
-------

    Copyright 2020 takke

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
