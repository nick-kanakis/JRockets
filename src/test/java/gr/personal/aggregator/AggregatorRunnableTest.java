package gr.personal.aggregator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by nkanakis on 7/26/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class AggregatorRunnableTest {

    @Autowired
    private AggregatorRunnable aggregatorRunnable;

    @Test
    public void testRun() throws Exception {
        //todo add test cases
        aggregatorRunnable.run();
    }
}
