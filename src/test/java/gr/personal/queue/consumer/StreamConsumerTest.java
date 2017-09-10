package gr.personal.queue.consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Nick Kanakis on 10/9/2017.
 */
@RunWith(SpringRunner.class)
public class StreamConsumerTest {
    @Mock
    private Logger logger;
    @Mock
    private SimpMessagingTemplate webSocket;
    @InjectMocks
    private StreamConsumer streamConsumer;

    @Test
    public void TestNullCommentReceiver() throws Exception{
        streamConsumer.commentReceiver(null);
        verify(webSocket, times(0)).convertAndSend(anyString(), anyString());
    }

    @Test
    public void TestEmptyCommentReceiver() throws Exception{
        streamConsumer.commentReceiver("");
        verify(webSocket, times(0)).convertAndSend(anyString(), anyString());
    }

    @Test
    public void TestNullPostReceiver() throws Exception{
        streamConsumer.postReceiver(null);
        verify(webSocket, times(0)).convertAndSend(anyString(), anyString());
    }

    @Test
    public void TestEmptyPostReceiver() throws Exception{
        streamConsumer.postReceiver("");
        verify(webSocket, times(0)).convertAndSend(anyString(), anyString());
    }
}
