package gr.personal.aggregator;

/**
 * Created by nkanakis on 8/8/2017.
 */
public interface Aggregator {
    void forwardAggregate(String subreddit);
    void reversedAggregate(String subreddit);
}
