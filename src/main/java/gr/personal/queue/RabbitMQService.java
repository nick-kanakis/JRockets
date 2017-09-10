package gr.personal.queue;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by nkanakis on 8/9/2017.
 */
@Service
public class RabbitMQService implements QueueService {

    @Autowired
    private Logger logger;
    @Value("${rabbitmq.comment.routingKey}")
    private String COMMENT_KEY;
    @Value("${rabbitmq.post.routingKey}")
    private String POST_KEY;

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange direct;

    @Override
    public void enqueueComment(JSONObject model2Enqueue) {
        logger.debug("Comment send to queue: "+ model2Enqueue.toString());
        template.convertAndSend(direct.getName(), COMMENT_KEY, model2Enqueue.toString());
    }

    @Override
    public void enqueuePost(JSONObject model2Enqueue) {
        logger.debug("Post send to queue: "+ model2Enqueue.toString());
        template.convertAndSend(direct.getName(), POST_KEY, model2Enqueue.toString());
    }
}
