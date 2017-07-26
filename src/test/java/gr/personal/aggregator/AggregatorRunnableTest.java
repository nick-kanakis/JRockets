package gr.personal.aggregator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by nkanakis on 7/26/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class AggregatorRunnableTest {

    @Autowired
    private AggregatorRunnable aggregatorRunnable;
    private long TIME_TO_RUN = 10000;

    @Test
    public void testRun() throws Exception {
        //todo: add test
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(aggregatorRunnable);

    }
}
