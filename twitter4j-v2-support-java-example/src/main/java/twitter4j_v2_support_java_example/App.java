package twitter4j_v2_support_java_example;

import twitter4j.TweetsResponse;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterV2;
import twitter4j.TwitterV2ExKt;
import twitter4j.UsersResponse;
import twitter4j.V2DefaultFields;

public final class App {

    public static void main(String[] args) throws TwitterException {

        //--------------------------------------------------
        // getTweets example
        //--------------------------------------------------
        Twitter twitter = new TwitterFactory().getInstance();
        final TwitterV2 v2 = TwitterV2ExKt.getV2(twitter);
        final TweetsResponse tweets = v2.getTweets(
                new long[]{1519966129946791936L},
                V2DefaultFields.mediaFields, null, null, "attachments", null, "attachments.media_keys");
        System.out.println("tweets = " + tweets);


        //--------------------------------------------------
        // getUsers example
        //--------------------------------------------------
        final long twitterDesignId = 87532773L;
        final UsersResponse users = v2.getUsers(new long[]{twitterDesignId}, null, null, "");
        System.out.println("users = " + users);
    }
}
