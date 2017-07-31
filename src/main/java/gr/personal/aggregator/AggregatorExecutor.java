package gr.personal.aggregator;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Nick Kanakis on 25/7/2017.
 */
@Component
public class AggregatorExecutor {
    //todo: replace all loggers with injectionpoint loggers.
    private static Logger logger;
    @Autowired
    private AggregatorRunnable aggregator;
    private ExecutorService executorService;

    @PostConstruct
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
