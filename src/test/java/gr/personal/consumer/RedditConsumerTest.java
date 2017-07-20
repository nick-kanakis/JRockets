package gr.personal.consumer;

import gr.personal.oauth.model.AccessToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by nkanakis on 7/20/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedditConsumerTest {

    @Autowired
    private RedditConsumer redditConsumer;

    @Test
    public void integrationTest() throws Exception {
        redditConsumer.getFirstModel("all");
    }
}
