package gr.personal.aggregator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    @Autowired
    private AggregatorRunnable aggregator;
    private ExecutorService executorService;

    public void start(){
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(aggregator);
    }

    public void stop() {
        executorService.shutdown();
    }
}
