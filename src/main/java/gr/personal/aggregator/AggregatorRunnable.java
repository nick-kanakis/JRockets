package gr.personal.aggregator;

import gr.personal.utils.PropertyReader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by nkanakis on 7/26/2017.
 */
public class AggregatorRunnable implements Runnable {
    private static final String SUBREDDIT = PropertyReader.fetchValue("reddit.subreddit");
    @Autowired
    private CommentAggregator commentAggregator;
    @Autowired
    private PostAggregator postAggregator;
    private AtomicBoolean running =  new AtomicBoolean(false);;

    @Override
    public void run() {
        while (running.get()) {
            commentAggregator.forwardAggregate(SUBREDDIT);
            postAggregator.forwardAggregate(SUBREDDIT);
        }
    }

    public void stop() { running.set(false);}

}
