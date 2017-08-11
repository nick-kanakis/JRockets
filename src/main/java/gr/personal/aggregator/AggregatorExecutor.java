package gr.personal.aggregator;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Nick Kanakis on 25/7/2017.
 *
 * Creates a new single thread that runs in the background an AggregatorRunnable instance.
 */
@Component
public class AggregatorExecutor {
    @Autowired
    private Logger logger;

    @Autowired
    private AggregatorRunnable aggregator;
    private ExecutorService executorService;

//@PostConstruct
//TODO: Disable postConstructor in testing.
    public void start(){
        logger.info("Start data aggregation from Reddit API ");
        ExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.execute(aggregator);
    }

    public void stop() {
        logger.info("Stop data aggregation from Reddit API. ");
        executorService.shutdown();
    }
}
