package gr.personal.configuration;

import gr.personal.aggregator.PostAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Created by Nick Kanakis on 25/7/2017.
 */
@Configuration
public class BeanConfig {

    @Bean
    @Scope(value = "prototype")
    public PostAggregator postAggregator(){
        return new PostAggregator();
    }
}
