package gr.personal.aggregator;

import gr.personal.utils.PropertyReader;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by nkanakis on 7/26/2017.
 */
public class AggregatorRunnable implements Runnable {

    @Autowired
    private Logger logger;
    @Autowired
    @Qualifier("CommentAggregator")
    private Aggregator commentAggregator;
    @Autowired
    @Qualifier("PostAggregator")
    private Aggregator postAggregator;
    private AtomicBoolean running =  new AtomicBoolean(true);

    @Override
    public void run() {
        //default subreddit is /r/all
        String subreddit = "all";
        try {
            subreddit = PropertyReader.fetchValue("reddit.subreddit");
        } catch (IOException e) {
            logger.warn("Unable to fetch properties", e);
        }

        /**
         * While tread is not interrupted by eg:
         * Thread.currentThread().interrupt()
         * and running is set true keep the infinite loop running
         */
        while (!Thread.currentThread().isInterrupted() && running.get()) {
            commentAggregator.forwardAggregate(subreddit);
            postAggregator.forwardAggregate(subreddit);
        }
    }

    public void stop() {
        logger.info("Stopping aggregator process");
        running.set(false);}

}
