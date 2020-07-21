[ ![Download](https://api.bintray.com/packages/takke/maven/twitter4j-v2/images/download.svg) ](https://bintray.com/takke/maven/twitter4j-v2/_latestVersion)

twitter4j-v2
============

twitter4j-v2 is a simple wrapper for Twitter API v2 that is designed to be used with Twitter4J.

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
    implementation 'jp.takke.twitter4j-v2:twitter4j-v2-support:0.1.0'
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

println(tweetsResponse.tweets[0].poll)
// => Poll(id=656974073113636864, options=[PollOption(position=1, label=Roboto, votes=391), 
//    PollOption(position=2, label=San Francisco, votes=691)], 
//    votingStatus=CLOSED, endDatetime=Fri Oct 23 08:23:19 GMT+09:00 2015, durationMinutes=1440)
```

Requirements
------------
- Kotlin 1.3.+
- Twitter4J 4.0.7+


Supported APIs
--------------

|  End-point  |  twitter4j-v2 method  |
| ---- | ---- |
|  Tweets and Users v2  |  -  |
|  [/labs/2/tweets](https://developer.twitter.com/en/docs/labs/tweets-and-users/api-reference/get-tweets)  |  [Twitter.getTweets()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/getTweets.kt)  |
|  [/labs/2/users](https://developer.twitter.com/en/docs/labs/tweets-and-users/api-reference/get-users)  |  [Twitter.getUsers()](https://github.com/takke/twitter4j-v2/blob/master/twitter4j-v2-support/src/main/kotlin/twitter4j/getUsers.kt)  |




Developed By
------------
Hiroaki TAKEUCHI (<a href="https://twitter.com/takke">@takke</a>) - takke30 at gmail.com


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
