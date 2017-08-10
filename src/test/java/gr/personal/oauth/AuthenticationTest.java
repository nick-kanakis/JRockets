package gr.personal.oauth;

import gr.personal.oauth.model.AccessToken;
import gr.personal.oauth.model.OAuthResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URL;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * Created by nkanakis on 8/10/2017.
 */
@RunWith(SpringRunner.class)
public class AuthenticationTest {

    @InjectMocks
    private Authentication authentication;

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private Logger logger;
    private OAuthResponse oAuthResponse;

    @Before
    public void setUp() throws Exception {
        oAuthResponse = new OAuthResponse();
        oAuthResponse.setAccess_token("1234");
        oAuthResponse.setExpires_in(1234L);
        oAuthResponse.setToken_type("bearer");

        when(restTemplate.postForEntity(anyString(), any(), eq(OAuthResponse.class))).thenReturn(ResponseEntity.ok(oAuthResponse));
    }

    @Test
    public void testGetAccessToken() throws Exception {
        AccessToken accessToken = authentication.getAccessToken();
        Assert.assertEquals(new AccessToken(oAuthResponse), accessToken);
    }
}
