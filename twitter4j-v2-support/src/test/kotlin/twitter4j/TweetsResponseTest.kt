package twitter4j

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import twitter4j.conf.ConfigurationBuilder

class TweetsResponseTest {

    @Test
    fun simplePoll() {

        val twitter = createTwitterInstance()
        val res = twitter.getTweets(656974073491156992L,
                mediaFields = "duration_ms,height,media_key,preview_image_url,type,url,width",
                placeFields = "contained_within,country,country_code,full_name,geo,id,name,place_type",
                pollFields = "duration_minutes,end_datetime,id,options,voting_status",
                tweetFields = "attachments,author_id,context_annotations,created_at,entities,geo,id,in_reply_to_user_id,lang,possibly_sensitive,referenced_tweets,source,public_metrics,text,withheld",
                userFields = "created_at,description,entities,id,location,name,pinned_tweet_id,profile_image_url,protected,public_metrics,url,username,verified,withheld",
                expansions = "attachments.poll_ids,attachments.media_keys,author_id,entities.mentions.username,geo.place_id,in_reply_to_user_id,referenced_tweets.id,referenced_tweets.id.author_id"
        )

        // twurl -X GET "/labs/2/tweets?ids=656974073491156992&expansions=attachments.poll_ids,attachments.media_keys,author_id,entities.mentions.username,geo.place_id,in_reply_to_user_id,referenced_tweets.id,referenced_tweets.id.author_id&media.fields=duration_ms,height,media_key,preview_image_url,type,url,width&place.fields=contained_within,country,country_code,full_name,geo,id,name,place_type&poll.fields=duration_minutes,end_datetime,id,options,voting_status&tweet.fields=attachments,author_id,context_annotations,created_at,entities,geo,id,in_reply_to_user_id,lang,possibly_sensitive,referenced_tweets,source,public_metrics,text,withheld&user.fields=created_at,description,entities,id,location,name,pinned_tweet_id,profile_image_url,protected,public_metrics,url,username,verified,withheld"
//        val res = TweetsResponse(JSONObject("{\"data\":[{\"text\":\"We've got polls now! Which typeface do you prefer?\",\"source\":\"Twitter Web Client\",\"lang\":\"en\",\"id\":\"656974073491156992\",\"created_at\":\"2015-10-21T23:23:19.000Z\",\"public_metrics\":{\"retweet_count\":66,\"reply_count\":9,\"like_count\":82,\"quote_count\":0},\"author_id\":\"87532773\",\"attachments\":{\"poll_ids\":[\"656974073113636864\"]},\"possibly_sensitive\":false}],\"includes\":{\"polls\":[{\"end_datetime\":\"2015-10-22T23:23:19.000Z\",\"options\":[{\"position\":1,\"label\":\"Roboto\",\"votes\":391},{\"position\":2,\"label\":\"San Francisco\",\"votes\":691}],\"id\":\"656974073113636864\",\"duration_minutes\":1440,\"voting_status\":\"closed\"}],\"users\":[{\"description\":\"The voice of Twitter’s product design team.\",\"location\":\"SF, NYC, BDR, LON, SEA, JP, DC\",\"protected\":false,\"created_at\":\"2009-11-04T21:06:16.000Z\",\"username\":\"TwitterDesign\",\"url\":\"\",\"verified\":true,\"name\":\"Twitter Design\",\"public_metrics\":{\"followers_count\":1801239,\"following_count\":171,\"tweet_count\":2485,\"listed_count\":5791},\"pinned_tweet_id\":\"1278374361368387584\",\"profile_image_url\":\"https://pbs.twimg.com/profile_images/453289910363906048/mybOhh4Z_normal.jpeg\",\"id\":\"87532773\"}]}}"))
        assertThat(res.tweets.size).isEqualTo(1)
        assertThat(res.tweets[0].id).isEqualTo(656974073491156992L)
        assertThat(res.tweets[0].text).isEqualTo("We've got polls now! Which typeface do you prefer?")
        assertThat(res.tweets[0].source).isEqualTo("Twitter Web Client")
        assertThat(res.tweets[0].lang).isEqualTo("en")
        assertThat(res.tweets[0].createdAt).isEqualTo("2015-10-21T23:23:19.000Z")

        assertThat(res.tweets[0].publicMetrics?.retweetCount).isEqualTo(66)
        assertThat(res.tweets[0].publicMetrics?.replyCount).isEqualTo(9)
        assertThat(res.tweets[0].publicMetrics?.likeCount).isEqualTo(82)
        assertThat(res.tweets[0].publicMetrics?.quoteCount).isEqualTo(0)

        assertThat(res.tweets[0].possiblySensitive).isEqualTo(false)

        // poll
        val poll = res.tweets[0].poll(res.pollsMap)!!
        assertThat(poll.options.size).isEqualTo(2)
        assertThat(poll.options[0].position).isEqualTo(1)
        assertThat(poll.options[0].label).isEqualTo("Roboto")
        assertThat(poll.options[0].votes).isEqualTo(391)
        assertThat(poll.options[1].position).isEqualTo(2)
        assertThat(poll.options[1].label).isEqualTo("San Francisco")
        assertThat(poll.options[1].votes).isEqualTo(691)
        assertThat(poll.id).isEqualTo(656974073113636864)
        assertThat(poll.durationMinutes).isEqualTo(1440)
        assertThat(poll.votingStatus).isEqualTo(Poll.VotingStatus.CLOSED)
        assertThat(poll.endDatetime).isEqualTo("2015-10-22T23:23:19.000Z")
    }

    @Test
    fun minimumTweet() {

        // twurl -X GET "/labs/2/tweets?ids=656974073491156992"
 //       val res = TweetsResponse(JSONObject("{\"data\":[{\"id\":\"656974073491156992\",\"text\":\"We've got polls now! Which typeface do you prefer?\"}]}"))

        val twitter = createTwitterInstance()
        val res = twitter.getTweets(656974073491156992L,
                mediaFields = null,
                placeFields = null,
                pollFields = null,
                tweetFields = null,
                userFields = null,
                expansions = ""
        )

        assertThat(res.tweets.size).isEqualTo(1)
        res.tweets[0].let {
            assertThat(it.id).isEqualTo(656974073491156992L)
            assertThat(it.text).isEqualTo("We've got polls now! Which typeface do you prefer?")
            assertThat(it.source).isNull()
            assertThat(it.lang).isNull()
            assertThat(it.publicMetrics).isNull()
            assertThat(it.possiblySensitive).isFalse
            assertThat(it.urls).isEmpty()
            assertThat(it.authorId).isNull()
            assertThat(it.pollId).isNull()
            assertThat(it.repliedToTweetId).isNull()
        }
    }

    @Test
    fun repliedTo() {

        val twitter = createTwitterInstance()
        val res = twitter.getTweets(1288737678926573568L)

//        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
//        println(json.toString(3))

        assertThat(res.tweets.size).isEqualTo(1)
        res.tweets[0].let {
            assertThat(it.repliedToTweetId).isEqualTo(1288735517127790592)
        }

    }

    private fun createTwitterInstance(): Twitter {
        val conf = ConfigurationBuilder()
                .setJSONStoreEnabled(true)
                .build()
        return TwitterFactory(conf).instance
//        return TwitterFactory.getSingleton()
    }
}