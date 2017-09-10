package gr.personal.queue.consumer;

import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by nkanakis on 9/8/2017.
 */
@Service
public class StreamConsumer {
    @Autowired
    private Logger logger;
    @Autowired
    private SimpMessagingTemplate webSocket;

    @RabbitListener(queues = "${rabbitmq.comment.queue}" )
    public void commentReceiver(String output){
        logger.debug(output);
        webSocket.convertAndSend("/topic/comments",output);
    }

    @RabbitListener(queues = "${rabbitmq.post.queue}" )
    public void postReceiver(String output){
        logger.debug(output);
        webSocket.convertAndSend("/topic/posts",output);
    }
}
