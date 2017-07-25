package gr.personal.aggregator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Nick Kanakis on 25/7/2017.
 */
@Component
public class AggregatorExecutor {
    //TODO: remove it and replace it with a factory bean.
    @Autowired
    private PostAggregator postAggregator;

    public void aggregate(){
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(postAggregator);

    }
}
