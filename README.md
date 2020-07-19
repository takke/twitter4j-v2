# twitter4j-v2

twitter4j-v2 is a simple wrapper for Twitter API v2 that is designed to be used with Twitter4J.

How do I use it?
---

### Setup

##### Dependencies
```groovy
repositories {
    maven {
        url 'https://jitpack.io'
        content {
            includeGroup "com.github.takke.twitter4j-v2"
        }
    }
}

dependencies {
    implementation 'com.github.takke.twitter4j-v2:twitter4j-v2-support:-SNAPSHOT'
}
```

### Example

```kotlin

val twitter: Twitter = yourTwitterInstanceProvider.get()

val tweetsResponse = twitter.getTweets(arrayOf(tweetId),
                            mediaFields = null,
                            placeFields = null,
                            pollFields = "duration_minutes,end_datetime,id,options,voting_status",
                            tweetFields = "id,public_metrics",
                            userFields = null,
                            expansions = "attachments.poll_ids")

println(tweetsResponse.tweets[0].poll?.votingStatus)    // => CLOSED
```


Requirements
---
- Kotlin 1.3.+
- Twitter4J 4.0.7+


Developed By
---
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