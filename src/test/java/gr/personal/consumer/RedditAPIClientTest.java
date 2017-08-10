package gr.personal.consumer;

import gr.personal.consumer.request.CommentRequest;
import gr.personal.consumer.request.RedditRequest;
import gr.personal.oauth.Authentication;
import gr.personal.oauth.model.AccessToken;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by Nick Kanakis on 2/8/2017.
 */
@RunWith(SpringRunner.class)
public class RedditAPIClientTest {

    @InjectMocks
    private RedditAPIClient client;
    @Mock
    private RedditRequest request;
    @Mock
    private Authentication authentication;
    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
        request = new CommentRequest("all", authentication);
        when(authentication.getAccessToken()).thenReturn(new AccessToken("testToken", 1000));

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testExecuteGetRequestWithDelayPolicy() throws Exception {
       String body ="{\"data\":{\"modhash\": \"\",\"children\": [{\"kind\": \"t3\",\"data\": {\"name\":\"t3_6rbvyo\",\"id\":\"6rbvyo\"}}]}}";

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Ratelimit-Remaining", "100");
        headers.add("X-Ratelimit-Reset", "100");

        ResponseEntity<String> response = new ResponseEntity<>(body, headers,HttpStatus.ACCEPTED);
        when(restTemplate.exchange(anyString(),eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class)))
                .thenReturn(response);

        Assert.assertNotNull(client.executeGetRequestWithDelayPolicy(request));
        Assert.assertNotNull(client.executeGetRequestWithDelayPolicy(request));

        verify(restTemplate, times(2)).exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class));
    }

    @Test
    public void testExecuteGetRequestWithoutDelayPolicy() throws Exception {
        String body ="{\"data\":{\"modhash\": \"\",\"children\": [{\"kind\": \"t3\",\"data\": {\"name\":\"t3_6rbvyo\",\"id\":\"6rbvyo\"}}]}}";

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Ratelimit-Remaining", "100");
        headers.add("X-Ratelimit-Reset", "100");

        ResponseEntity<String> response = new ResponseEntity<>(body, headers,HttpStatus.ACCEPTED);
        when(restTemplate.exchange(anyString(),eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class)))
                .thenReturn(response);

        Assert.assertNotNull(client.executeGetRequestWithoutDelayPolicy(request));
        Assert.assertNotNull(client.executeGetRequestWithoutDelayPolicy(request));
        verify(restTemplate, times(2)).exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class));
    }

}
