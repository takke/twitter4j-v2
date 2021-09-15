package twitter4j

import org.assertj.core.api.Assertions.*
import org.junit.Test
import kotlin.test.assertNotNull

@Throws(TwitterException::class)
fun Twitter.getMyLatestTweet(userId: Long): ResponseList<Status>? {

    if (this !is TwitterImpl) throw IllegalStateException("invalid twitter4j impl")

    ensureAuthorizationEnabled()

    val params = arrayListOf(
        HttpParameter("user_id", userId),
        HttpParameter("include_rts", false),
    )

    return factory.createStatusList(
        http.get(
            conf.restBaseURL + "statuses/user_timeline.json", params.toTypedArray(), auth, this
        )
    )
}

class GetTweetsTest {

    @Test
    fun simplePoll() {

        val twitter = V2TestUtil.createTwitterInstance()
        val res = twitter.getTweets(
            656974073491156992L,
            mediaFields = V2DefaultFields.mediaFields,
            placeFields = V2DefaultFields.placeFields,
            pollFields = V2DefaultFields.pollFields,
            tweetFields = V2DefaultFields.tweetFields,
            userFields = V2DefaultFields.userFields,
            expansions = V2DefaultFields.expansions
        )

        // twurl -X GET "/labs/2/tweets?ids=656974073491156992&expansions=attachments.poll_ids,attachments.media_keys,author_id,entities.mentions.username,geo.place_id,in_reply_to_user_id,referenced_tweets.id,referenced_tweets.id.author_id&media.fields=duration_ms,height,media_key,preview_image_url,type,url,width&place.fields=contained_within,country,country_code,full_name,geo,id,name,place_type&poll.fields=duration_minutes,end_datetime,id,options,voting_status&tweet.fields=attachments,author_id,context_annotations,created_at,entities,geo,id,in_reply_to_user_id,lang,possibly_sensitive,referenced_tweets,source,public_metrics,text,withheld&user.fields=created_at,description,entities,id,location,name,pinned_tweet_id,profile_image_url,protected,public_metrics,url,username,verified,withheld"
//        val res = TweetsResponse(JSONObject("{\"data\":[{\"text\":\"We've got polls now! Which typeface do you prefer?\",\"source\":\"Twitter Web Client\",\"lang\":\"en\",\"id\":\"656974073491156992\",\"created_at\":\"2015-10-21T23:23:19.000Z\",\"public_metrics\":{\"retweet_count\":66,\"reply_count\":9,\"like_count\":82,\"quote_count\":0},\"author_id\":\"87532773\",\"attachments\":{\"poll_ids\":[\"656974073113636864\"]},\"possibly_sensitive\":false}],\"includes\":{\"polls\":[{\"end_datetime\":\"2015-10-22T23:23:19.000Z\",\"options\":[{\"position\":1,\"label\":\"Roboto\",\"votes\":391},{\"position\":2,\"label\":\"San Francisco\",\"votes\":691}],\"id\":\"656974073113636864\",\"duration_minutes\":1440,\"voting_status\":\"closed\"}],\"users\":[{\"description\":\"The voice of Twitter’s product design team.\",\"location\":\"SF, NYC, BDR, LON, SEA, JP, DC\",\"protected\":false,\"created_at\":\"2009-11-04T21:06:16.000Z\",\"username\":\"TwitterDesign\",\"url\":\"\",\"verified\":true,\"name\":\"Twitter Design\",\"public_metrics\":{\"followers_count\":1801239,\"following_count\":171,\"tweet_count\":2485,\"listed_count\":5791},\"pinned_tweet_id\":\"1278374361368387584\",\"profile_image_url\":\"https://pbs.twimg.com/profile_images/453289910363906048/mybOhh4Z_normal.jpeg\",\"id\":\"87532773\"}]}}"))
        assertThat(res.tweets.size).isEqualTo(1)
        res.tweets[0].let {
            assertThat(it.id).isEqualTo(656974073491156992L)
            assertThat(it.text).isEqualTo("We've got polls now! Which typeface do you prefer?")
            assertThat(it.source).isEqualTo("Twitter Web Client")
            assertThat(it.lang).isEqualTo("en")
            assertThat(it.createdAt).isEqualTo("2015-10-21T23:23:19.000Z")

            assertThat(it.publicMetrics?.retweetCount).isCloseTo(66, within(5))
            assertThat(it.publicMetrics?.replyCount).isCloseTo(9, within(5))
            assertThat(it.publicMetrics?.likeCount).isCloseTo(81, within(5))
            assertThat(it.publicMetrics?.quoteCount).isCloseTo(0, within(5))

            assertThat(it.possiblySensitive).isEqualTo(false)
            assertThat(it.repliedToTweetId).isNull()
            assertThat(it.quotedTweetId).isNull()
        }

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

        val twitter = V2TestUtil.createTwitterInstance()
        val res = twitter.getTweets(
            656974073491156992L,
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
            assertThat(it.quotedTweetId).isNull()
        }
    }

    @Test
    fun repliedTo() {

        val twitter = V2TestUtil.createTwitterInstance()
        val res = twitter.getTweets(1288737678926573568L)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
        println(json.toString(3))

        assertThat(res.tweets.size).isEqualTo(1)
        res.tweets[0].let {
            assertThat(it.repliedToTweetId).isEqualTo(1288735517127790592)

            res.tweetsMap[it.repliedToTweetId]!!.let { repliedTweet ->
                assertThat(repliedTweet.id).isEqualTo(1288735517127790592)
            }
        }
    }

    @Test
    fun quoted() {

        val twitter = V2TestUtil.createTwitterInstance()
        val res = twitter.getTweets(1288745927654510592L)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
        println(json.toString(3))

        res.tweets[0].let {
            assertThat(it.quotedTweetId).isEqualTo(1288737678926573568)

            res.tweetsMap[it.quotedTweetId]!!.let { quotedTweet ->
                assertThat(quotedTweet.id).isEqualTo(1288737678926573568)
            }
        }
    }

    @Test
    fun retweet() {

        val twitter = V2TestUtil.createTwitterInstance()
        val res = twitter.getTweets(1288748212795236352L)

        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
        println(json.toString(3))

        res.tweets[0].let {
            assertThat(it.retweetId).isEqualTo(1288707607486541824)

            res.tweetsMap[it.retweetId]!!.let { quotedTweet ->
                assertThat(quotedTweet.id).isEqualTo(1288707607486541824)
            }
        }
    }

    @Test
    fun nonPublicOrganicMetrics() {

        val twitter = V2TestUtil.createTwitterInstance()
        val account = twitter.verifyCredentials()
        if (account == null) {
            fail("invalid account")
        } else {
            // exclude rts
            val timeline = twitter.getMyLatestTweet(account.id)

            val statusId = timeline!![0].id
            println("account id[${account.id}], status id[$statusId]")
            val res = twitter.getTweets(
                statusId,
                tweetFields = "non_public_metrics,organic_metrics,public_metrics",
                expansions = ""
            )

            val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
            println(json.toString(3))

            res.tweets[0].let {
                assertNotNull(it.nonPublicMetrics)
                assertNotNull(it.organicMetrics)
                assertNotNull(it.publicMetrics)

                println(it.nonPublicMetrics)
                println(it.organicMetrics)
            }
        }
    }

}