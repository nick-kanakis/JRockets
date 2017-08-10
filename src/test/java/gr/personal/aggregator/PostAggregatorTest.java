package gr.personal.aggregator;

import gr.personal.consumer.RedditConsumer;
import gr.personal.queue.QueueService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Nick Kanakis on 2/8/2017.
 */
@RunWith(SpringRunner.class)
public class PostAggregatorTest {

    @InjectMocks
    private PostAggregator postAggregator;
    @Mock
    private RedditConsumer redditConsumer;
    @Mock
    private QueueService queueService;

    @Before
    public void setUp() throws Exception {
    }


    @Test
    public void testForwardAggregate() throws Exception {
        //todo add test
    }

    @Test(expected = RuntimeException.class)
    public void testReversedAggregate() throws Exception {
    }
}
