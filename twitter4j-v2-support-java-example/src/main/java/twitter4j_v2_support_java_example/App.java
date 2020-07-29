package twitter4j_v2_support_java_example;

import twitter4j.GetTweetsKt;
import twitter4j.GetUsersKt;
import twitter4j.TweetsResponse;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.UsersResponse;

public final class App {

    public static void main(String[] args) throws TwitterException {

        //--------------------------------------------------
        // getTweets example
        //--------------------------------------------------
        Twitter twitter = new TwitterFactory().getInstance();
        final TweetsResponse tweets = GetTweetsKt.getTweets(twitter,
                new long[]{656974073491156992L},
                null, null, null, null, null, "");
        System.out.println("tweets = " + tweets);


        //--------------------------------------------------
        // getUsers example
        //--------------------------------------------------
        final long twitterDesignId = 87532773L;
        final UsersResponse users = GetUsersKt.getUsers(twitter, new long[]{twitterDesignId}, null, null, null, null, null, "");
        System.out.println("users = " + users);
    }
}
