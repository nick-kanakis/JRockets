package gr.personal.oauth;

import gr.personal.helper.IntegrationTest;
import gr.personal.oauth.model.AccessToken;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Nick Kanakis on 12/7/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Category(IntegrationTest.class)
public class AuthenticationIntegrationTest {

    @Autowired
    private Authentication authentication;

    @Test
    public void integrationTest() throws Exception {
        AccessToken authenticate = authentication.getAccessToken();
        Assert.assertNotNull(authenticate);
    }
}
