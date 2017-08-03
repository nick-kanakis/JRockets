package gr.personal.consumer;

import gr.personal.Application;
import gr.personal.consumer.request.CommentRequest;
import gr.personal.consumer.request.FullnamesRequest;
import gr.personal.consumer.request.RedditRequest;
import gr.personal.oauth.Authentication;
import gr.personal.oauth.model.AccessToken;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.concurrent.TimeoutException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by Nick Kanakis on 2/8/2017.
 */
@RunWith(SpringRunner.class)
public class RestClientTest {

    //todo add unit tests

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RestClient client;
    @Mock
    private RedditRequest request;
    @Mock
    private Authentication authentication;


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


    }

}
