package gr.personal.aggregator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Nick Kanakis on 25/7/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AggregatorExecutorTest {

    @Autowired
    private AggregatorExecutor aggregatorExecutor;

    @Before
    public void setUp() throws Exception {
        aggregatorExecutor.start();
    }

    @After
    public void tearDown() throws Exception {
        aggregatorExecutor.stop();
    }

    @Test(timeout = 30000)
    public void testAggregate() throws Exception {
        //todo add test case
    }
}
