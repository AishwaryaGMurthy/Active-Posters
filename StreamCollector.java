package twitter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import twitter4j.*;
import twitter4j.json.DataObjectFactory;
public class StreamCollector {
	public static void main(String[] args) throws TwitterException,
	InterruptedException, IOException {

// Declare an integer `TOTAL_TWEETS' representing the number of tweets
// you wish to collect >
int TOTAL_TWEETS=1000;
// Declare an array of strings called `topics' of topics you wish to
// filter for >
String[] topics=new String[100];
// We use these to write to our file
FileWriter fstream = new FileWriter("stream.json");
final BufferedWriter out = new BufferedWriter(fstream);

// A queue to act as an intermediary between the asynchronous stream and
// synchronous processing -- we set it to be rather large just so we
// don't miss any tweets.
final BlockingQueue<String> queue = new LinkedBlockingQueue<String>(
		15000);

// Our twitter stream
twitter4j.TwitterStream twitterStream = new TwitterStreamFactory()
		.getInstance();

// A listener which we use to process incoming tweets
StatusListener listener = new StatusListener() {
	@Override
	public void onStatus(Status status) {
		// Convert the status object into a JSON string and put it into
		// the queue
		queue.offer(DataObjectFactory.getRawJSON(status));
		// Print out just the text of the status into the console so
		// that we can observe the running of our program >
		System.out.println("@"+status.getUser().getScreenName()+"-"+status.getText());
	}

	@Override
	public void onDeletionNotice(
			StatusDeletionNotice statusDeletionNotice) {
		System.out.println("Got a status deletion notice id:"
				+ statusDeletionNotice.getStatusId());
	}

	@Override
	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		System.out.println("Got track limitation notice:"
				+ numberOfLimitedStatuses);
	}

	@Override
	public void onScrubGeo(long userId, long upToStatusId) {
		System.out.println("Got scrub_geo event userId:" + userId
				+ " upToStatusId:" + upToStatusId);
	}

	@Override
	public void onStallWarning(StallWarning warning) {
		System.out.println("Got stall warning:" + warning);
	}

	@Override
	public void onException(Exception ex) {
		ex.printStackTrace();
	}
};

// Create a filter query to filter for the topics we are interested in
final FilterQuery query = new FilterQuery();
query.track(topics);

// Add our listener to the stream
twitterStream.addListener(listener);

// Start the stream
twitterStream.filter(query);

// Write a loop which extracts the JSON strings from the queue and write
// them into a file >
for(int i=0;i<TOTAL_TWEETS;i++)
{
	out.write(queue.take());
}
// Remember to shut things down properly
twitterStream.cleanUp();
out.close();
fstream.close();

}
}
