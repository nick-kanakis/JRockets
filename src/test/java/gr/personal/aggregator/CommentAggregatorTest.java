package gr.personal.aggregator;

import gr.personal.consumer.RedditConsumer;
import gr.personal.queue.QueueService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Nick Kanakis on 2/8/2017.
 */
@RunWith(SpringRunner.class)
public class CommentAggregatorTest {

    @InjectMocks
    private CommentAggregator commentAggregator;
    @Mock
    private RedditConsumer redditConsumer;
    @Mock
    private QueueService queueService;

    @Before
    public void setUp() throws Exception {
        when(redditConsumer.fetchInitialComment(anyString())).thenReturn(generateModels(1));
        when(redditConsumer.fetchForward(anyString())).thenReturn(generateModels(2));
    }

    @Test
    public void testForwardAggregate() throws Exception {
        for (int i = 0; i < 10 ; i++) {
            commentAggregator.forwardAggregate("all");
        }
        verify(queueService, times(10)).enqueueComment(any(JSONArray.class));
        verify(redditConsumer, times(1)).fetchInitialComment("all");
        verify(redditConsumer, times(9)).fetchForward(anyString());
    }

    @Test(expected = RuntimeException.class)
    public void testReversedAggregate() throws Exception {
        //todo add test when reversedAggregation is implemented
        commentAggregator.reversedAggregate(anyString());
    }

    private JSONArray generateModels(int numberOfModels) throws JSONException {
        JSONArray childrenArray = new JSONArray();
        for(int i =0; i<numberOfModels; i++){
            JSONObject model = new JSONObject();
            JSONObject data = new JSONObject();

            model.put("kind","t1");
            data.put("name","t1_"+i+1);

            model.put("data", data);
            childrenArray.put(i,model);
        }
        return childrenArray;
    }
}
