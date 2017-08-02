package gr.personal.consumer;

import gr.personal.consumer.model.Thing;
import gr.personal.consumer.request.FullnamesRequest;
import gr.personal.consumer.request.RedditRequest;
import gr.personal.oauth.Authentication;
import gr.personal.oauth.model.AccessToken;
import gr.personal.oauth.model.OAuthResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by Nick Kanakis on 2/8/2017.
 */
@RunWith(SpringRunner.class)
public class RedditConsumerTest {

    @Mock
    private RestClient client;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private RedditConsumer redditConsumer;


    @Before
    public void setUp() throws Exception {

    }

    @Test(expected = NumberFormatException.class)
    public void shouldFailToFetchByRange() throws Exception {
        Thing start = new Thing("t1_ ");
        Thing end = new Thing("t1_ ");
        redditConsumer.fetchByRange(start, end);
    }


    @Test
    //todo: make client respond dynamically in order to mimic the real Reddit API
    public void shouldFetchByRange() throws Exception {
        JSONArray originalArray = generateModels(3);
        when(client.executeGetRequestWithDelayPolicy(any(RedditRequest.class))).thenReturn(originalArray);
        Thing start = new Thing("t1_1");
        Thing end = new Thing("t1_3");
        Assert.assertEquals(originalArray, redditConsumer.fetchByRange(start, end));
    }

    private JSONArray generateModels(int numberOfModels) throws JSONException {
        JSONArray childrenArray = new JSONArray();
        for(int i =0; i<numberOfModels; i++){
            JSONObject model = new JSONObject();
            JSONObject data = new JSONObject();

            model.put("kind","t1");
            data.put("name",i+1);

            model.put("data", data);
            childrenArray.put(i,model);
        }
        return childrenArray;
    }
}
