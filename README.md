[![v2](https://img.shields.io/endpoint?url=https%3A%2F%2Ftwbadges.glitch.me%2Fbadges%2Fv2)](https://developer.twitter.com/en/docs/twitter-api)
[ ![Download](https://api.bintray.com/packages/takke/maven/twitter4j-v2/images/download.svg) ](https://bintray.com/takke/maven/twitter4j-v2/_latestVersion)
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
        url 'http://dl.bintray.com/takke/maven'
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
- Kotlin 1.4.0-rc
- Twitter4J 4.0.7


Supported APIs
--------------

|  End-point  |  twitter4j-v2 method  |
| ---- | ---- |
|  **Tweets**  |  -  |
|  Lookup <br>[GET /2/tweets](https://developer.twitter.com/en/docs/twitter-api/tweets/lookup/api-reference/get-tweets)  |  [Twitter.getTweets()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/getTweets.kt)  |
|  Recent search <br>[GET /2/tweets/search/recent](https://developer.twitter.com/en/docs/twitter-api/tweets/search/api-reference/get-tweets-search-recent)  |  N/A  |
|  [Filtered stream](https://developer.twitter.com/en/docs/twitter-api/tweets/filtered-stream/introduction)  |  N/A  |
|  [Sampled stream](https://developer.twitter.com/en/docs/twitter-api/tweets/sampled-stream/introduction)  |  N/A  |
|  Hide replies <br>[PUT /2/tweets/:id/hidden](https://developer.twitter.com/en/docs/twitter-api/tweets/hide-replies/api-reference/put-tweets-id-hidden) |  N/A  |
|  **Users**  |  -  |
|  Lookup <br>[GET /2/users](https://developer.twitter.com/en/docs/twitter-api/users/lookup/api-reference/get-users)  |  [Twitter.getUsers()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/getUsers.kt)  |

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
