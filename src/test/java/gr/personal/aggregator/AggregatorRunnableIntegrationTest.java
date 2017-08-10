package gr.personal.aggregator;

import gr.personal.helper.IntegrationTest;
import gr.personal.helper.OuputValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by nkanakis on 7/26/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Category(IntegrationTest.class)
public class AggregatorRunnableIntegrationTest {

    @Autowired
    private AggregatorRunnable aggregatorRunnable;
    private long TIME_TO_RUN = 40000;

    @Test(timeout = 100000)
    public void testRun() throws Exception {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(aggregatorRunnable);
        Thread.sleep(TIME_TO_RUN);
        aggregatorRunnable.stop();
        //todo: change it when queue is added
        List<String> postFullnames = OuputValidator.checkIncrementalIds("posts.txt", false);
        Assert.assertTrue( OuputValidator.checkValidFullnames(postFullnames)<=0);
        List<String> commentFullnames = OuputValidator.checkIncrementalIds("comments.txt", true);
        Assert.assertTrue( OuputValidator.checkValidFullnames(commentFullnames)<=0);

    }
}
