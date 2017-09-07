package gr.personal.configuration;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Nick Kanakis on 6/9/2017.
 */
@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.comment.queue}")
    private String commentQueueName;
    @Value("${rabbitmq.post.queue}")
    private String postQueueName;
    @Value("${rabbitmq.comment.routingKey}")
    private String commentRoutingKey;
    @Value("${rabbitmq.post.routingKey}")
    private String postRoutingKey;

    @Bean
    public DirectExchange direct(){
        return new DirectExchange("reddit.direct");
    }

    @Bean
    public Queue commentQueue(){
        return new Queue(commentQueueName);
    }

    @Bean
    public Queue postQueue(){
        return new Queue(postQueueName);
    }

    @Bean
    public Binding commentBinding() {
        return BindingBuilder.bind(commentQueue()).to(direct()).with(commentRoutingKey);
    }

    @Bean
    public Binding postBinding() {
        return BindingBuilder.bind(postQueue()).to(direct()).with(postRoutingKey);
    }
}
