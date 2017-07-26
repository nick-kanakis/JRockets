package gr.personal.configuration;

import gr.personal.aggregator.AggregatorRunnable;
import gr.personal.aggregator.CommentAggregator;
import gr.personal.aggregator.PostAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.w3c.dom.Comment;

import java.util.function.Supplier;

/**
 * Created by Nick Kanakis on 25/7/2017.
 */
@Configuration
public class BeanConfig {

    /*
    * AggregatorRunnable implements Runnable and is not instantiated by Spring at startup.
    * This means that @Autowired is not working, to work around this problem we use beans with Scope "prototype"
    * (creates a new bean instance of the object every time a request for that specific bean is made).
    *
    * */
    @Bean
    @Scope(value = "prototype")
    public AggregatorRunnable aggregatorRunnable(){
        return new AggregatorRunnable();
    }


}