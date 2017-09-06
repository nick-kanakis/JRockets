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
    @Bean
    public DirectExchange direct(){
        return new DirectExchange("reddit.direct");
    }
}
