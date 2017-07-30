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
    private AtomicBoolean running =  new AtomicBoolean(true);

    @Override
    public void run() {
        /**
         * While tread is not interrupted by eg:
         * Thread.currentThread().interrupt()
         * and running is set true keep the infinite loop running
         */
        while (!Thread.currentThread().isInterrupted() && running.get()) {
            commentAggregator.forwardAggregate(SUBREDDIT);
            postAggregator.forwardAggregate(SUBREDDIT);
        }
    }

    public void stop() { running.set(false);}

}
