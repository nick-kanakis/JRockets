package gr.personal.queue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Nick Kanakis on 10/9/2017.
 */
@RunWith(SpringRunner.class)
public class RabbitMQServiceTest {

    @Mock
    private RabbitTemplate template;
    @Mock
    private DirectExchange direct;
    @InjectMocks
    private RabbitMQService rabbitMQService;

    @Test
    public void testNullEnqueueComment() throws Exception {
        rabbitMQService.enqueueComment(null);
        verify(template, times(0)).convertAndSend(anyString(), anyString(), anyString());
    }
    @Test
    public void testNullEnqueuePost() throws Exception {
        rabbitMQService.enqueuePost(null);
        verify(template, times(0)).convertAndSend(anyString(), anyString(), anyString());
    }
}
