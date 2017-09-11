package gr.personal.queue.listener;

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
public class StreamListenerTest {
    @Mock
    private Logger logger;
    @Mock
    private SimpMessagingTemplate webSocket;
    @InjectMocks
    private StreamListener streamListener;

    @Test
    public void TestNullCommentReceiver() throws Exception{
        streamListener.commentReceiver(null);
        verify(webSocket, times(0)).convertAndSend(anyString(), anyString());
    }

    @Test
    public void TestEmptyCommentReceiver() throws Exception{
        streamListener.commentReceiver("");
        verify(webSocket, times(0)).convertAndSend(anyString(), anyString());
    }

    @Test
    public void TestNullPostReceiver() throws Exception{
        streamListener.postReceiver(null);
        verify(webSocket, times(0)).convertAndSend(anyString(), anyString());
    }

    @Test
    public void TestEmptyPostReceiver() throws Exception{
        streamListener.postReceiver("");
        verify(webSocket, times(0)).convertAndSend(anyString(), anyString());
    }
}
