package gr.personal.aggregator;

import gr.personal.helper.IntegrationTest;
import gr.personal.helper.OuputValidator;
import gr.personal.queue.QueueService;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;

/**
 * Created by nkanakis on 7/26/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Category(IntegrationTest.class)
public class AggregatorRunnableIntegrationTest {

    @Autowired
    private AggregatorRunnable aggregatorRunnable;
    @SpyBean
    private QueueService queueService;
    private long TIME_TO_RUN = 40000;

    @Test(timeout = 100000)
    public void testRun() throws Exception {
        ArgumentCaptor<JSONObject> commentCaptor = ArgumentCaptor.forClass(JSONObject.class);
        ArgumentCaptor<JSONObject> postCaptor = ArgumentCaptor.forClass(JSONObject.class);


        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(aggregatorRunnable);
        Thread.sleep(TIME_TO_RUN);
        aggregatorRunnable.stop();

        Mockito.verify(queueService, atLeastOnce()).enqueueComment(commentCaptor.capture());
        Mockito.verify(queueService, atLeastOnce()).enqueuePost(postCaptor.capture());
        List<JSONObject> capturedComments = commentCaptor.getAllValues();
        List<JSONObject> capturedPosts = commentCaptor.getAllValues();

        Assert.assertTrue(capturedComments.size() > 100);
        Assert.assertTrue(capturedPosts.size() > 100);
    }
}
