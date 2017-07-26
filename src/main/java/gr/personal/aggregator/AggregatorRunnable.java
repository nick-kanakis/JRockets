package gr.personal.aggregator;

import gr.personal.utils.PropertyReader;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by nkanakis on 7/26/2017.
 */
public class AggregatorRunnable implements Runnable {
    private static final String SUBREDDIT = PropertyReader.fetchValue("reddit.subreddit");
    @Autowired
    private CommentAggregator commentAggregator;
    @Autowired
    private PostAggregator postAggregator;

    @Override
    public void run() {
        while (true) {
            commentAggregator.forwardAggregate(SUBREDDIT);
            postAggregator.forwardAggregate(SUBREDDIT);
        }
    }
}
