package gr.personal.consumer;

import gr.personal.consumer.model.Thing;
import gr.personal.consumer.request.RedditRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by Nick Kanakis on 2/8/2017.
 */
@RunWith(SpringRunner.class)
public class RedditConsumerTest {

    @Mock
    private RedditAPIClient client;

    @InjectMocks
    private RedditConsumer redditConsumer;


    @Test(expected = NumberFormatException.class)
    public void shouldFailToFetchByRange() throws Exception {
        Thing start = new Thing("t1_ ");
        Thing end = new Thing("t1_ ");
        redditConsumer.fetchByRange(start, end);
    }


    @Test
    public void testSimpleFetchByRange() throws Exception {
        JSONArray originalArray = generateModels(3);
        when(client.executeGetRequestWithDelayPolicy(any(RedditRequest.class))).thenReturn(originalArray);
        Thing start = new Thing("t1_1");
        Thing end = new Thing("t1_3");
        Assert.assertEquals(originalArray, redditConsumer.fetchByRange(start, end));
    }

    @Test
    public void testFetchByRangeWithLargeRange() throws Exception {
        when(client.executeGetRequestWithDelayPolicy(any(RedditRequest.class))).thenReturn( generateModels(2));
        Thing start = new Thing("t1_1");
        Thing end = new Thing("t1_8C");
        redditConsumer.fetchByRange(start, end);

        verify(client, times(3)).executeGetRequestWithDelayPolicy(any(RedditRequest.class));

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
