package gr.personal.queue.consumers;

import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by nkanakis on 9/8/2017.
 */

@Component
public class StreamConsumer {
    @Autowired
    private Logger logger;

    @Autowired
    private SimpMessagingTemplate webSocket;

    //todo read from properties queue name
    @RabbitListener(queues = "COMMENT_QUEUE" )
    public void commentReceiver(String output){
        logger.info(output);
        //todo sent message to websocket
        webSocket.convertAndSend("/topic/comments",output);
    }

    //todo read from properties queue name
    @RabbitListener(queues = "POST_QUEUE" )
    public void postReceiver(String output){
        logger.info(output);
        //todo sent message to websocket
        webSocket.convertAndSend("/topic/posts",output);
    }
}
