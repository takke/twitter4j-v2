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
- Kotlin 1.5.30
- Twitter4J 4.0.7


Supported APIs
--------------

| | |  End-point  |  twitter4j-v2 method  |
| ---- | ---- | ---- | ---- |
| **Tweets** | Tweet Lookup | [GET /2/tweets](https://developer.twitter.com/en/docs/twitter-api/tweets/lookup/api-reference/get-tweets)  |  [Twitter.getTweets()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/getTweets.kt)  | |
| | Search Tweets   | [GET /2/tweets/search/recent](https://developer.twitter.com/en/docs/twitter-api/tweets/search/api-reference/get-tweets-search-recent)  |  [Twitter.searchRecent()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/SearchEx.kt)  |
| |                 | [GET /2/tweets/search/all](https://developer.twitter.com/en/docs/twitter-api/tweets/search/api-reference/get-tweets-search-all)  |  [Twitter.searchAll()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/SearchEx.kt)  |
| | Tweet counts    | [GET /2/tweets/counts/recent](https://developer.twitter.com/en/docs/twitter-api/tweets/counts/api-reference/get-tweets-counts-recent) |  [Twitter.countRecent()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/CountsEx.kt)  |
| |                 | [GET /2/tweets/counts/all](https://developer.twitter.com/en/docs/twitter-api/tweets/counts/api-reference/get-tweets-counts-all) |  [Twitter.countAll()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/CountsEx.kt)  |
| | Timelines       | [GET /2/users/:id/tweets ](https://developer.twitter.com/en/docs/twitter-api/tweets/timelines/api-reference/get-users-id-tweets)|  [Twitter.getUserTweets()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/TimelinesEx.kt)  |
| |                 | [GET /2/users/:id/mentions](https://developer.twitter.com/en/docs/twitter-api/tweets/timelines/api-reference/get-users-id-mentions) | [Twitter.getUserMentions()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/TimelinesEx.kt) |
| |                 | [GET /2/users/by/username/:username/tweets](https://developer.twitter.com/en/docs/twitter-api/tweets/timelines/api-reference/get-users-by-username-username-tweets) | *end-point missing? (2021.9.15)* |
| |                 | [GET /2/users/by/username/:username/mentions](https://developer.twitter.com/en/docs/twitter-api/tweets/timelines/api-reference/get-users-by-username-username-mentions) | *end-point missing? (2021.9.15)* |
| | Filtered stream | [GET /2/tweets/search/stream](https://developer.twitter.com/en/docs/twitter-api/tweets/filtered-stream/api-reference/get-tweets-search-stream) |  N/A [#1](https://github.com/takke/twitter4j-v2/issues/1) |
| | Sampled stream  | [GET /2/tweets/sample/stream](https://developer.twitter.com/en/docs/twitter-api/tweets/sampled-stream/api-reference/get-tweets-sample-stream) |  N/A [#1](https://github.com/takke/twitter4j-v2/issues/1) |
| | Retweets        | [GET /2/tweets/:id/retweeted_by](https://developer.twitter.com/en/docs/twitter-api/tweets/retweets/api-reference/get-tweets-id-retweeted_by) |  [Twitter.getRetweetUsers()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/RetweetsEx.kt)  |
| |                 | [POST /2/users/:id/retweets](https://developer.twitter.com/en/docs/twitter-api/tweets/retweets/api-reference/post-users-id-retweets) |  [Twitter.retweet()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/RetweetsEx.kt)  |
| |                 | [DELETE /2/users/:id/retweets/:source_tweet_id](https://developer.twitter.com/en/docs/twitter-api/tweets/retweets/api-reference/delete-users-id-retweets-tweet_id) |  [Twitter.unretweet()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/RetweetsEx.kt)  |
| | Likes           | [GET /2/tweets/:id/liking_users](https://developer.twitter.com/en/docs/twitter-api/tweets/likes/api-reference/get-tweets-id-liking_users) |  [Twitter.getLikingUsers()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/LikesEx.kt)  |
| |                 | [GET /2/users/:id/liked_tweets](https://developer.twitter.com/en/docs/twitter-api/tweets/likes/api-reference/get-users-id-liked_tweets) |  [Twitter.getLikedTweets()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/LikesEx.kt)  |
| |                 | [POST /2/users/:id/likes](https://developer.twitter.com/en/docs/twitter-api/tweets/likes/api-reference/post-users-id-likes) |  [Twitter.likeTweet()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/LikesEx.kt)  |
| |                 | [DELETE /2/users/:id/likes/:tweet_id](https://developer.twitter.com/en/docs/twitter-api/tweets/likes/api-reference/delete-users-id-likes-tweet_id) |  [Twitter.unlikeTweet()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/LikesEx.kt)  |
| | Hide replies    | [PUT /2/tweets/:id/hidden](https://developer.twitter.com/en/docs/twitter-api/tweets/hide-replies/api-reference/put-tweets-id-hidden) |  N/A *(Twitter4J v4.0.7 does not support PUT methods that contain json parameters.)*  |
| **Users** | User lookup | [GET /2/users](https://developer.twitter.com/en/docs/twitter-api/users/lookup/api-reference/get-users)  |  [Twitter.getUsers()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/getUsers.kt)  |
| |                 | [GET /2/users/by](https://developer.twitter.com/en/docs/twitter-api/users/lookup/api-reference/get-users-by) | [Twitter.getUsersBy()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/getUsersBy.kt)  |
| | Follows         | [GET /2/users/:id/following](https://developer.twitter.com/en/docs/twitter-api/users/follows/api-reference/get-users-id-following) |  [Twitter.getFollowingUsers()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/FollowsEx.kt)  |
| |                 | [GET /2/users/:id/followers](https://developer.twitter.com/en/docs/twitter-api/users/follows/api-reference/get-users-id-followers) |  [Twitter.getFollowerUsers()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/FollowsEx.kt)  |
| |                 | [POST /2/users/:id/following](https://developer.twitter.com/en/docs/twitter-api/users/follows/api-reference/post-users-source_user_id-following) |  [Twitter.followUser()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/FollowsEx.kt)  |
| |                 | [DELETE /2/users/:source_user_id/following/:target_user_id](https://developer.twitter.com/en/docs/twitter-api/users/follows/api-reference/delete-users-source_id-following) |  [Twitter.unfollowUser()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/FollowsEx.kt)  |
| | Blocks          | [GET /2/users/:id/blocking](https://developer.twitter.com/en/docs/twitter-api/users/blocks/api-reference/get-users-blocking) |  [Twitter.getBlockingUsers()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/BlocksEx.kt)  |
| |                 | [POST /2/users/:id/blocking](https://developer.twitter.com/en/docs/twitter-api/users/blocks/api-reference/post-users-user_id-blocking) |  [Twitter.blockUser()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/BlocksEx.kt)  |
| |                 | [DELETE /2/users/:source_user_id/blocking/:target_user_id](https://developer.twitter.com/en/docs/twitter-api/users/blocks/api-reference/delete-users-user_id-blocking) |  [Twitter.unblockUser()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/BlocksEx.kt)  |
| | Mutes           | [POST /2/users/:id/muting](https://developer.twitter.com/en/docs/twitter-api/users/mutes/api-reference/post-users-user_id-muting) |  [Twitter.muteUser()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/MutesEx.kt)  |
| |                 | [DELETE /2/users/:source_user_id/muting/:target_user_id](https://developer.twitter.com/en/docs/twitter-api/users/mutes/api-reference/delete-users-user_id-muting) |  [Twitter.unmuteUser()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/MutesEx.kt)  |
| **Spaces** | Lookup Spaces | [GET /2/spaces](https://developer.twitter.com/en/docs/twitter-api/spaces/lookup/api-reference/get-spaces) |  N/A  |
| |                 | [GET /2/spaces/by/creator_ids](https://developer.twitter.com/en/docs/twitter-api/spaces/lookup/api-reference/get-spaces-by-creator-ids) |  N/A  |
| | Search Spaces   | [GET /2/spaces/search](https://developer.twitter.com/en/docs/twitter-api/spaces/search/api-reference/get-spaces-search) |  [Twitter.searchSpaces()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/SearchSpacesEx.kt)  |

v2 APIs table from [Twitter API v2: Early Access](https://developer.twitter.com/en/docs/twitter-api/early-access)

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
