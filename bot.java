// Tweet,reply to a tweet, Showing the latest timeline, Get particular tweets,Retweet
// you can do the abouve things using this

package bot;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import java.util.List;
import twitter4j.*;

public class Bot{

    public static void main(String[] args) 
    {  //call the function which you want
         NewTweet();
        gettimline();
        gettweet();
        reply();
    }
       
        public static void NewTweet()
        {   //making a tweet from your account
            Twitter twitter = TwitterFactory.getSingleton();
            String mytweet="Hello, this UPES DevOps Bot";
            try
            {
            Status status = twitter.updateStatus(mytweet);
                System.out.println("successful"+status.getText());
            }
            catch(TwitterException e){
                e.printStackTrace();
            }
    }
    public static void gettimline(){
         try {
            // gets Twitter instance with default credentials
            Twitter twitter = new TwitterFactory().getInstance();
            User user = twitter.verifyCredentials();
            List<Status> statuses = twitter.getHomeTimeline();
            System.out.println("Showing @" + user.getScreenName() + "'s home timeline.");//getting all the latest tweets from your timeline
            for (Status status : statuses) {
                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
            }
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
         }
     public static void gettweet(){
         Twitter twitter = new TwitterFactory().getInstance();
        try {
            Query query = new Query("#DevOpsAtUPES");//searching for tweets
            QueryResult result;
            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) {
                    long Id = tweet.getId();
                //  System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText()+" "+"tweet id:"+ Id);
            //  twitter.retweetStatus(Id);
             
             twitter.retweetStatus(Id);//retweet type1
              
                    //retweetStatus(Id);
              
                }
            } while ((query = result.nextQuery()) != null);
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }
     }
        public static  Status retweetStatus(long Id) throws TwitterException {//retweet type 2
         Twitter twitter = new TwitterFactory().getInstance();
  return twitter.retweetStatus(Id);
}
    
    public static void reply(){
         Twitter twitter = TwitterFactory.getSingleton();
            
        try {
    long inReplyToStatusId = 1252942945612775425l;                         
    StatusUpdate statusUpdate = new StatusUpdate("Bot replying to my tweet attempt");   // that message which you want to reply
    statusUpdate.inReplyToStatusId(inReplyToStatusId);
    Status status = twitter.updateStatus(statusUpdate);
    System.out.println("Successfully updated the status to [" + status.getText() + "].");
    
     }
  catch(TwitterException e) {
      System.err.println("Send tweet: " + e + " Status code: " + e.getStatusCode());
  }
     }
}
