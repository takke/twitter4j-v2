Change Log
==========

v0.1.2 (2020.08.xx)
-------------------
- Add `Configuration.v2Configuration.baseURL` to set another base URL
- Replace the method for storing json with Twitter4J style
- Add java example
- Rename `twitter4j-v2-support-example` to `twitter4j-v2-support-kotlin-example`
- Add vars to `Tweet`
  - repliedToTweetId, quotedTweetId, retweetId

v0.1.1 (2020.07.22)
-------------------
- Support /labs/2/users (Twitter.getUsers)
  - parse just a partial elements only

v0.1.0 (2020.07.20)
-------------------
- Initial release
- Support /labs/2/tweets (Twitter.getTweets)
  - parse just a partial elements only
