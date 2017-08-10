package gr.personal.consumer.request;

import gr.personal.oauth.Authentication;
import gr.personal.oauth.model.AccessToken;
import org.apache.tomcat.util.http.parser.Authorization;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Nick Kanakis on 10/8/2017.
 */
@RunWith(SpringRunner.class)
public class RedditRequestTest {

    private RedditRequest redditRequest;
    @Mock
    private Logger logger;
    @Mock
    private Authentication authentication;

    @Before
    public void setUp() throws Exception {
        redditRequest = new PostRequest("all", authentication);
        Mockito.when(authentication.getAccessToken()).thenReturn(new AccessToken("test",10));
    }

    @Test
    public void testGenerateHttpHeaders() throws Exception {
        HttpEntity httpEntity = redditRequest.generateHttpHeaders();
        HttpHeaders headers = httpEntity.getHeaders();

        Assert.assertNotNull(headers);
        Assert.assertTrue(headers.containsKey("Authorization"));
        Assert.assertTrue(headers.containsKey("User-agent"));
    }
}
