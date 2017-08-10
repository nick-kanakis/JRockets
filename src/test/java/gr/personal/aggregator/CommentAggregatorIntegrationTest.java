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

import static org.mockito.Mockito.times;

/**
 * Created by Nick Kanakis on 24/7/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Category(IntegrationTest.class)
public class CommentAggregatorIntegrationTest {
    private static final int NUM_OF_RUNS =100;

    @Autowired
    private CommentAggregator commentAggregator;

    @SpyBean
    private QueueService queueService;

    @Test
    public void testAggregate() throws Exception {
        ArgumentCaptor<JSONObject> queueCaptor = ArgumentCaptor.forClass(JSONObject.class);
        for (int i = 0; i < NUM_OF_RUNS; i++) {
            commentAggregator.forwardAggregate("all");
        }
        Mockito.verify(queueService, times(NUM_OF_RUNS)).enqueueComment(queueCaptor.capture());
        List<JSONObject> capturedModels = queueCaptor.getAllValues();

        List<String> fullnames = OuputValidator.checkIncrementalIds(capturedModels);
        Assert.assertTrue( OuputValidator.checkValidFullnames(fullnames)<=0);
    }
}
