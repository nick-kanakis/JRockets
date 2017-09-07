package gr.personal.configuration;

import gr.personal.aggregator.AggregatorRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

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

    /**
     * In order to avoid the boiler plate code of logger instantiation in each class we use the InjectionPoint
     * Functionality of stream.
     *
     * We use scope = prototype as a new Logger instance should be created for each Spring Bean
     *
     * @param injectionPoint
     * @return
     */
    @Bean
    @Scope(value = "prototype")
    public Logger logger(InjectionPoint injectionPoint){
        return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass());
    }
}
