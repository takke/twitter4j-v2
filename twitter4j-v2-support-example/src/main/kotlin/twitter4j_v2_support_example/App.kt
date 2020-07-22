package twitter4j_v2_support_example

import twitter4j.JSONObject
import twitter4j.TweetsResponse
import twitter4j.TwitterFactory
import twitter4j.getTweets
import twitter4j.getUsers


fun main(args: Array<String>) {

    // twurl -X GET "/labs/2/tweets?ids=1284872930841640960&expansions=attachments.poll_ids,attachments.media_keys,author_id,entities.mentions.username,geo.place_id,in_reply_to_user_id,referenced_tweets.id,referenced_tweets.id.author_id&media.fields=duration_ms,height,media_key,preview_image_url,type,url,width&place.fields=contained_within,country,country_code,full_name,geo,id,name,place_type&poll.fields=duration_minutes,end_datetime,id,options,voting_status&tweet.fields=attachments,author_id,context_annotations,created_at,entities,geo,id,in_reply_to_user_id,lang,possibly_sensitive,referenced_tweets,source,public_metrics,text,withheld&user.fields=created_at,description,entities,id,location,name,pinned_tweet_id,profile_image_url,protected,public_metrics,url,username,verified,withheld"
    TweetsResponse(JSONObject("{\"data\":[{\"source\":\"Tween\",\"entities\":{\"urls\":[{\"start\":34,\"end\":57,\"url\":\"https://t.co/08Eoo3xlMo\",\"expanded_url\":\"https://github.com/takke/twitter4j-v2\",\"display_url\":\"github.com/takke/twitter4…\",\"images\":[{\"url\":\"https://pbs.twimg.com/news_img/1284872934327070722/gRYmCVd2?format=jpg&name=orig\",\"width\":400,\"height\":400},{\"url\":\"https://pbs.twimg.com/news_img/1284872934327070722/gRYmCVd2?format=jpg&name=150x150\",\"width\":150,\"height\":150}],\"status\":200,\"title\":\"takke/twitter4j-v2\",\"description\":\"Contribute to takke/twitter4j-v2 development by creating an account on GitHub.\",\"unwound_url\":\"https://github.com/takke/twitter4j-v2\"}]},\"id\":\"1284872930841640960\",\"created_at\":\"2020-07-19T15:29:13.000Z\",\"lang\":\"ja\",\"text\":\"それっぽいもの、作った // takke/twitter4j-v2 https://t.co/08Eoo3xlMo\",\"possibly_sensitive\":false,\"author_id\":\"8379212\",\"public_metrics\":{\"retweet_count\":2,\"reply_count\":3,\"like_count\":5,\"quote_count\":1}}],\"includes\":{\"users\":[{\"location\":\"北海道\",\"created_at\":\"2007-08-23T10:06:53.000Z\",\"username\":\"takke\",\"protected\":false,\"id\":\"8379212\",\"public_metrics\":{\"followers_count\":1661,\"following_count\":1042,\"tweet_count\":56518,\"listed_count\":129},\"entities\":{\"url\":{\"urls\":[{\"start\":0,\"end\":23,\"url\":\"https://t.co/YAuXOSh2C4\",\"expanded_url\":\"http://www.panecraft.net/\",\"display_url\":\"panecraft.net\"}]},\"description\":{\"mentions\":[{\"start\":13,\"end\":22,\"username\":\"TwitPane\"}]}},\"description\":\"Twitterクライアント@TwitPane、mixiブラウザTkMixiViewer、英単語学習ソフト P-Study System 、MZ3/4 などを開発。「ちょっぴり使いやすい」アプリを日々開発しています。ペーンクラフト代表\",\"url\":\"https://t.co/YAuXOSh2C4\",\"profile_image_url\":\"https://pbs.twimg.com/profile_images/423153841505193984/yGKSJu78_normal.jpeg\",\"name\":\"竹内裕昭\\uD83D\\uDC27\",\"verified\":false}]}}")
    ).let {
        println(it)
    }

    // twurl -X GET "/labs/2/tweets?ids=656974073491156992&expansions=attachments.poll_ids,attachments.media_keys,author_id,entities.mentions.username,geo.place_id,in_reply_to_user_id,referenced_tweets.id,referenced_tweets.id.author_id&media.fields=duration_ms,height,media_key,preview_image_url,type,url,width&place.fields=contained_within,country,country_code,full_name,geo,id,name,place_type&poll.fields=duration_minutes,end_datetime,id,options,voting_status&tweet.fields=attachments,author_id,context_annotations,created_at,entities,geo,id,in_reply_to_user_id,lang,possibly_sensitive,referenced_tweets,source,public_metrics,text,withheld&user.fields=created_at,description,entities,id,location,name,pinned_tweet_id,profile_image_url,protected,public_metrics,url,username,verified,withheld"
    TweetsResponse(JSONObject("{\"data\":[{\"text\":\"We've got polls now! Which typeface do you prefer?\",\"source\":\"Twitter Web Client\",\"lang\":\"en\",\"id\":\"656974073491156992\",\"created_at\":\"2015-10-21T23:23:19.000Z\",\"public_metrics\":{\"retweet_count\":66,\"reply_count\":9,\"like_count\":82,\"quote_count\":0},\"author_id\":\"87532773\",\"attachments\":{\"poll_ids\":[\"656974073113636864\"]},\"possibly_sensitive\":false}],\"includes\":{\"polls\":[{\"end_datetime\":\"2015-10-22T23:23:19.000Z\",\"options\":[{\"position\":1,\"label\":\"Roboto\",\"votes\":391},{\"position\":2,\"label\":\"San Francisco\",\"votes\":691}],\"id\":\"656974073113636864\",\"duration_minutes\":1440,\"voting_status\":\"closed\"}],\"users\":[{\"description\":\"The voice of Twitter’s product design team.\",\"location\":\"SF, NYC, BDR, LON, SEA, JP, DC\",\"protected\":false,\"created_at\":\"2009-11-04T21:06:16.000Z\",\"username\":\"TwitterDesign\",\"url\":\"\",\"verified\":true,\"name\":\"Twitter Design\",\"public_metrics\":{\"followers_count\":1801239,\"following_count\":171,\"tweet_count\":2485,\"listed_count\":5791},\"pinned_tweet_id\":\"1278374361368387584\",\"profile_image_url\":\"https://pbs.twimg.com/profile_images/453289910363906048/mybOhh4Z_normal.jpeg\",\"id\":\"87532773\"}]}}")
    ).let {
        println(it)
    }

    //--------------------------------------------------
    // getTweets example
    //--------------------------------------------------
    val twitter = TwitterFactory.getSingleton()
    twitter.getTweets(656974073491156992L).let {
        println(it)

        println(it.asJSONObject.toString(3))
    }

    val tweetIds: Array<Long> = arrayOf(656974073491156992L, 1284872930841640960L)
    twitter.getTweets(*tweetIds.toLongArray()).let {
        println(it)

        println(it.asJSONObject.toString(3))
    }

    //--------------------------------------------------
    // getUsers example
    //--------------------------------------------------
//    val takkeId = 8379212L
//    val twitterDevId = 2244994945L
    val twitterDesignId = 87532773L
    twitter.getUsers(twitterDesignId).let {
        println(it)

        println(it.asJSONObject.toString(3))
    }

    // minimum query
    twitter.getUsers(twitterDesignId,
            mediaFields = null,
            placeFields = null,
            pollFields = null,
            tweetFields = null,
            userFields = null,
            expansions = ""
    ).let {
        println(it)

        println(it.asJSONObject.toString(3))
    }

    // pinned_tweet_id only
    twitter.getUsers(twitterDesignId,
            mediaFields = null,
            placeFields = null,
            pollFields = null,
            tweetFields = null,
            userFields = null,
            expansions = "pinned_tweet_id"
    ).let {
        println(it)

        println(it.tweetsMap)

        println(it.asJSONObject.toString(3))
    }

}
