package gr.personal.consumer;

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

    //TODO: Actual check the responses
    @Autowired
    private RedditConsumer redditConsumer;

    @Test
    public void testFetchInitialPost() throws Exception {
        redditConsumer.fetchInitialPost("all");
    }

    @Test
    public void testFetchInitialComment() throws Exception {
        redditConsumer.fetchInitialComment("all");
    }

    @Test
    public void testFetchReversedPosts() throws Exception {
        redditConsumer.fetchReversedPosts("all");
    }

    @Test
    public void testTetchReversedComments() throws Exception {
        redditConsumer.fetchReversedComments("all");
    }

    @Test
    public void testFetchBacklog() throws Exception {
        redditConsumer.fetchBacklog("t1_dkijto7", 10);
    }
}
