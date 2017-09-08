package gr.personal.queue.consumers;

import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by nkanakis on 9/8/2017.
 */

@Component
public class StreamConsumer {
    @Autowired
    private Logger logger;

    //todo read from queue
    @RabbitListener(queues = "COMMENT_QUEUE" )
    public void commentReceiver(String input){
        logger.info(input);
        //todo sent message to websocket
    }

    //todo read from queue
    @RabbitListener(queues = "POST_QUEUE" )
    public void postReceiver(String input){
        logger.info(input);
        //todo sent message to websocket
    }
}
