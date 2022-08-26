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

    private val twitter by lazy { V2TestUtil.createTwitterInstance() }
//    private val myId by lazy { twitter.verifyCredentials().id }

    @Test
    fun simplePoll() {

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
            assertThat(it.mediaKeys).isNull()
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
    fun place() {

        // {"data":[{"geo":{"place_id":"2e624efa0028615e"},"possibly_sensitive":false,"lang":"ja","source":"Twitter for Android","created_at":"2022-03-20T10:18:21.000Z","text":"位置情報のてすと","public_metrics":{"retweet_count":0,"reply_count":0,"like_count":1,"quote_count":0},"reply_settings":"everyone","author_id":"8379212","conversation_id":"1505488910205345795","id":"1505488910205345795"}],"includes":{"users":[{"created_at":"2007-08-23T10:06:53.000Z","public_metrics":{"followers_count":1681,"following_count":1065,"tweet_count":61212,"listed_count":126},"profile_image_url":"https:\/\/pbs.twimg.com\/profile_images\/423153841505193984\/yGKSJu78_normal.jpeg","name":"竹内裕昭🐧","protected":false,"entities":{"url":{"urls":[{"start":0,"end":23,"url":"https:\/\/t.co\/B8CEzNa8O2","expanded_url":"http:\/\/www.panecraft.net\/","display_url":"panecraft.net"}]},"description":{"mentions":[{"start":13,"end":22,"username":"TwitPane"}]}},"location":"北海道","id":"8379212","url":"https:\/\/t.co\/B8CEzNa8O2","verified":false,"description":"Twitterクライアント@TwitPane、mixiブラウザTkMixiViewer、英単語学習ソフト P-Study System 、MZ3\/4 などを開発。「ちょっぴり使いやすい」アプリを日々開発しています。ペーンクラフト代表","username":"takke"}],"places":[{"geo":{"type":"Feature","bbox":[139.540126666667,35.8720877777778,141.390018611111,43.0857888888889],"properties":{}},"country":"日本","country_code":"JP","full_name":"北海道 札幌市中央区","place_type":"city","name":"札幌市中央区","id":"2e624efa0028615e"}]}}
        val res = twitter.getTweets(
            1505488910205345795,
            mediaFields = V2DefaultFields.mediaFields,
            placeFields = V2DefaultFields.placeFields,
            pollFields = V2DefaultFields.pollFields,
            tweetFields = V2DefaultFields.tweetFields,
            userFields = V2DefaultFields.userFields,
            expansions = V2DefaultFields.expansions
        )

        assertThat(res.tweets.size).isEqualTo(1)
        res.tweets[0].let {
            assertThat(it.id).isEqualTo(1505488910205345795)

            val place = it.place(res.placesMap)!!
            println(place)

            val placeFromMap = res.placesMap[it.placeId]
            assertThat(place).isEqualTo(placeFromMap)

            assertThat(place.id).isEqualTo("2e624efa0028615e")
            assertThat(place.fullName).isNotEmpty   // "北海道 札幌市中央区" or translated one.
            assertThat(place.country).isNotEmpty    // "日本" or translated one.
            assertThat(place.countryCode).isEqualTo("JP")
            assertThat(place.name).isNotEmpty       // "札幌市中央区" or translated one.
            assertThat(place.placeType).isEqualTo("city")

            assertThat(place.geo?.type).isEqualTo("Feature")
        }
    }

    @Test
    fun minimumTweet() {

        // twurl -X GET "/labs/2/tweets?ids=656974073491156992"
        //       val res = TweetsResponse(JSONObject("{\"data\":[{\"id\":\"656974073491156992\",\"text\":\"We've got polls now! Which typeface do you prefer?\"}]}"))

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
            assertThat(it.mediaKeys).isNull()
        }
    }

    @Test
    fun repliedTo() {

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

            if (res.tweets.isEmpty()) {
                // 過去30日以内のツイートがないなどの場合には仕方ないのでテストしない
            } else {
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

    @Test
    fun getConversationId() {

        val res = twitter.getTweets(
            1514061441749245952L,
            mediaFields = null,
            placeFields = null,
            pollFields = null,
            tweetFields = "conversation_id",
            userFields = null,
            expansions = ""
        )

        assertThat(res.tweets.size).isEqualTo(1)
        res.tweets[0].let {
            assertThat(it.id).isEqualTo(1514061441749245952L)
            assertThat(it.conversationId).isEqualTo(1513995134370676736L)
        }
    }

    @Test
    fun media_image() {

        val res = twitter.getTweets(
            1541785063028842498L,
            tweetFields = "attachments",
            expansions = "attachments.media_keys",
            mediaFields = V2DefaultFields.mediaFields,
            placeFields = null,
            pollFields = null,
            userFields = null,
        )

        //{
        //   "data": [
        //      {
        //         "id": "1541785063028842498",
        //         "attachments": {
        //            "media_keys": [
        //               "3_1541785060919185408"
        //            ]
        //         },
        //         "text": "This player just won't let go of his old boots... 😆 \n\nWho is it? 🤷‍♂️\n\n#UCL https:\/\/t.co\/4dETe8vjyL"
        //      }
        //   ],
        //   "includes": {
        //      "media": [
        //         {
        //            "url": "https:\/\/pbs.twimg.com\/media\/FWWFWBwXkAApnXt.jpg",
        //            "type": "photo",
        //            "height": 712,
        //            "width": 632,
        //            "media_key": "3_1541785060919185408"
        //         }
        //      ]
        //   }
        //}

        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
        println(json.toString(3))

        println(res)

        assertThat(res.tweets[0].id).isEqualTo(1541785063028842498L)
        assertThat(res.tweets[0].mediaKeys!![0]).isEqualTo(MediaKey("3_1541785060919185408"))

        assertThat(res.mediaMap.size).isEqualTo(1)

        val photo = res.mediaMap[MediaKey("3_1541785060919185408")]!!.asPhoto
        assertThat(photo.type).isEqualTo(Media.Type.Photo)
        assertThat(photo.url).isEqualTo("https://pbs.twimg.com/media/FWWFWBwXkAApnXt.jpg")
        assertThat(photo.height).isEqualTo(712)
        assertThat(photo.width).isEqualTo(632)
    }

    @Test
    fun media_images() {

        val res = twitter.getTweets(
            1519966129946791936L,
            tweetFields = "attachments",
            expansions = "attachments.media_keys",
            mediaFields = V2DefaultFields.mediaFields,
            placeFields = null,
            pollFields = null,
            userFields = null,
        )

        // {
        //   "data": [
        //      {
        //         "text": "コスプレ体験エリアすごかったなぁ！#超会議2022 https:\/\/t.co\/BK6iAsT2qE",
        //         "attachments": {
        //            "media_keys": [
        //               "3_1519966116327849984",
        //               "3_1519966116353015809",
        //               "3_1519966116797612037",
        //               "3_1519966120467648512"
        //            ]
        //         },
        //         "id": "1519966129946791936"
        //      }
        //   ],
        //   "includes": {
        //      "media": [
        //         {
        //            "url": "https:\/\/pbs.twimg.com\/media\/FRgBITJaAAAu6rV.jpg",
        //            "media_key": "3_1519966116327849984",
        //            "height": 2048,
        //            "width": 1536,
        //            "type": "photo"
        //         },
        //         {
        //            "url": "https:\/\/pbs.twimg.com\/media\/FRgBITPaAAER7p0.jpg",
        //            "media_key": "3_1519966116353015809",
        //            "height": 2048,
        //            "width": 1536,
        //            "type": "photo"
        //         },
        //         {
        //            "url": "https:\/\/pbs.twimg.com\/media\/FRgBIU5aAAUNVA1.jpg",
        //            "media_key": "3_1519966116797612037",
        //            "height": 2048,
        //            "width": 1536,
        //            "type": "photo"
        //         },
        //         {
        //            "url": "https:\/\/pbs.twimg.com\/media\/FRgBIikaUAAx5mG.jpg",
        //            "media_key": "3_1519966120467648512",
        //            "height": 2048,
        //            "width": 1536,
        //            "type": "photo"
        //         }
        //      ]
        //   }
        // }

        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
        println(json.toString(3))

        println(res)

        assertThat(res.tweets[0].id).isEqualTo(1519966129946791936L)
        assertThat(res.tweets[0].mediaKeys!![0]).isEqualTo(MediaKey("3_1519966116327849984"))
        assertThat(res.tweets[0].mediaKeys!![1]).isEqualTo(MediaKey("3_1519966116353015809"))
        assertThat(res.tweets[0].mediaKeys!![2]).isEqualTo(MediaKey("3_1519966116797612037"))
        assertThat(res.tweets[0].mediaKeys!![3]).isEqualTo(MediaKey("3_1519966120467648512"))

        assertThat(res.mediaMap.size).isEqualTo(4)

        res.mediaMap[MediaKey("3_1519966116327849984")]!!.asPhoto.let { photo ->
            assertThat(photo.url).isEqualTo("https://pbs.twimg.com/media/FRgBITJaAAAu6rV.jpg")
            assertThat(photo.height).isEqualTo(2048)
            assertThat(photo.width).isEqualTo(1536)
        }
        res.mediaMap[MediaKey("3_1519966116353015809")]!!.asPhoto.let { photo ->
            assertThat(photo.url).isEqualTo("https://pbs.twimg.com/media/FRgBITPaAAER7p0.jpg")
            assertThat(photo.height).isEqualTo(2048)
            assertThat(photo.width).isEqualTo(1536)
        }
        res.mediaMap[MediaKey("3_1519966116797612037")]!!.asPhoto.let { photo ->
            assertThat(photo.url).isEqualTo("https://pbs.twimg.com/media/FRgBIU5aAAUNVA1.jpg")
            assertThat(photo.height).isEqualTo(2048)
            assertThat(photo.width).isEqualTo(1536)
        }
        res.mediaMap[MediaKey("3_1519966120467648512")]!!.asPhoto.let { photo ->
            assertThat(photo.url).isEqualTo("https://pbs.twimg.com/media/FRgBIikaUAAx5mG.jpg")
            assertThat(photo.height).isEqualTo(2048)
            assertThat(photo.width).isEqualTo(1536)
        }
    }

    @Test
    fun media_gif() {

        val res = twitter.getTweets(
            1323351722119495681L,
            tweetFields = "attachments",
            expansions = "attachments.media_keys",
            mediaFields = V2DefaultFields.mediaFields,
            placeFields = null,
            pollFields = null,
            userFields = null,
        )

        // {
        //   "data": [
        //      {
        //         "attachments": {
        //            "media_keys": [
        //               "16_1323351712476717057"
        //            ]
        //         },
        //         "text": "https:\/\/t.co\/aLA9mVBAfB",
        //         "id": "1323351722119495681"
        //      }
        //   ],
        //   "includes": {
        //      "media": [
        //         {
        //            "media_key": "16_1323351712476717057",
        //            "type": "animated_gif",
        //            "height": 278,
        //            "width": 498,
        //            "preview_image_url": "https:\/\/pbs.twimg.com\/tweet_video_thumb\/El19Xv8UYAEWxVd.jpg"
        //         }
        //      ]
        //   }
        // }

        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
        println(json.toString(3))

        println(res)

        assertThat(res.tweets[0].id).isEqualTo(1323351722119495681L)
        assertThat(res.tweets[0].mediaKeys!![0]).isEqualTo(MediaKey("16_1323351712476717057"))

        assertThat(res.mediaMap.size).isEqualTo(1)

        val animatedGif = res.mediaMap[MediaKey("16_1323351712476717057")]!!.asAnimatedGif
        assertThat(animatedGif.type).isEqualTo(Media.Type.AnimatedGif)
        assertThat(animatedGif.previewImageUrl).isEqualTo("https://pbs.twimg.com/tweet_video_thumb/El19Xv8UYAEWxVd.jpg")
        assertThat(animatedGif.height).isEqualTo(278)
        assertThat(animatedGif.width).isEqualTo(498)
    }

    @Test
    fun media_video() {

        val res = twitter.getTweets(
            1543286684846104576L,
            tweetFields = "attachments",
            expansions = "attachments.media_keys",
            mediaFields = V2DefaultFields.mediaFields,
            placeFields = null,
            pollFields = null,
            userFields = null,
        )

        // {
        //   "data": [
        //      {
        //         "id": "1543286684846104576",
        //         "attachments": {
        //            "media_keys": [
        //               "13_1335947635014905857"
        //            ]
        //         },
        //         "text": "All types of goals from all situations 🔥\n\nCristiano Ronaldo's best Champions League goals are something else! 🍿\n\nhttps:\/\/t.co\/lSzee8d61m"
        //      }
        //   ],
        //   "includes": {
        //      "media": [
        //         {
        //            "preview_image_url": "https:\/\/pbs.twimg.com\/media\/Eoo9YilXcAI6tNm.jpg",
        //            "duration_ms": 256240,
        //            "public_metrics": {
        //                "view_count": 238062
        //            },
        //            "type": "video",
        //            "width": 1920,
        //            "media_key": "13_1335947635014905857",
        //            "height": 1080,
        //            "variants": [
        //                {
        //                    "bit_rate": 832000,
        //                    "content_type": "video\/mp4",
        //                    "url": "https:\/\/video.twimg.com\/amplify_video\/1335947635014905857\/vid\/640x360\/pRCUFi8H_x8jL5KM.mp4?tag=13"
        //                },
        //                {
        //                    "bit_rate": 288000,
        //                    "content_type": "video\/mp4",
        //                    "url": "https:\/\/video.twimg.com\/amplify_video\/1335947635014905857\/vid\/480x270\/nLhybIFYjGkSZ3UW.mp4?tag=13"
        //                },
        //                {
        //                    "content_type": "application\/x-mpegURL",
        //                    "url": "https:\/\/video.twimg.com\/amplify_video\/1335947635014905857\/pl\/wSROnrJTqUNVDl7n.m3u8?tag=13"
        //                },
        //                {
        //                    "bit_rate": 2176000,
        //                    "content_type": "video\/mp4",
        //                    "url": "https:\/\/video.twimg.com\/amplify_video\/1335947635014905857\/vid\/1280x720\/z2ew8py8ODet_3Za.mp4?tag=13"
        //                }
        //            ]
        //         }
        //      ]
        //   }
        // }

        val json = JSONObject(TwitterObjectFactory.getRawJSON(res))
        println(json.toString(3))

        println(res)

        assertThat(res.tweets[0].id).isEqualTo(1543286684846104576L)
        assertThat(res.tweets[0].mediaKeys!![0]).isEqualTo(MediaKey("13_1335947635014905857"))

        assertThat(res.mediaMap.size).isEqualTo(1)

        val video = res.mediaMap[MediaKey("13_1335947635014905857")]!!.asVideo
        assertThat(video.type).isEqualTo(Media.Type.Video)

        assertThat(video.previewImageUrl).isEqualTo("https://pbs.twimg.com/media/Eoo9YilXcAI6tNm.jpg")
        assertThat(video.durationMs).isEqualTo(256240)
        assertThat(video.publicMetrics.viewCount).isGreaterThan(234761)
        assertThat(video.width).isEqualTo(1920)
        assertThat(video.height).isEqualTo(1080)

        assertThat(video.variants.size).isEqualTo(4)
        assertThat(video.variants.any {
            it == Media.Variant(
                832000,
                "video/mp4",
                "https://video.twimg.com/amplify_video/1335947635014905857/vid/640x360/pRCUFi8H_x8jL5KM.mp4?tag=13"
            )
        }).isTrue
        assertThat(video.variants.any {
            it == Media.Variant(
                288000,
                "video/mp4",
                "https://video.twimg.com/amplify_video/1335947635014905857/vid/480x270/nLhybIFYjGkSZ3UW.mp4?tag=13"
            )
        }).isTrue
        assertThat(video.variants.any {
            it == Media.Variant(
                null,
                "application/x-mpegURL",
                "https://video.twimg.com/amplify_video/1335947635014905857/pl/wSROnrJTqUNVDl7n.m3u8?tag=13"
            )
        }).isTrue
        assertThat(video.variants.any {
            it == Media.Variant(
                2176000,
                "video/mp4",
                "https://video.twimg.com/amplify_video/1335947635014905857/vid/1280x720/z2ew8py8ODet_3Za.mp4?tag=13"
            )
        }).isTrue
    }

}