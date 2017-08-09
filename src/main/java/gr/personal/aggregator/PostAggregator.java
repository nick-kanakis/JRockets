package gr.personal.aggregator;

import gr.personal.consumer.RedditConsumer;
import gr.personal.queue.QueueService;
import gr.personal.utils.ModelsUtils;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * Created by Nick Kanakis on 22/7/2017.
 */
@Component("PostAggregator")
public class PostAggregator implements Aggregator {
    @Autowired
    private Logger logger;

    @Autowired
    private RedditConsumer redditConsumer;
    @Autowired
    private QueueService queueService;
    private static String lastFullname = null;

    @Override
    public void forwardAggregate(String subreddit) {
        JSONArray result;
        if (lastFullname == null)
            result = redditConsumer.fetchInitialPost(subreddit);
        else
            result = redditConsumer.fetchForward(lastFullname);

        String tmpLastFullname = ModelsUtils.extractLastFullname(result);

        if (tmpLastFullname == null || tmpLastFullname == "")
            return;

        lastFullname = tmpLastFullname;
        queueService.enqueuePost(result);
    }

    @Override
    public void reversedAggregate(String subreddit) {
        throw new RuntimeException("ReversedAggregate in not yet supported");
    }

}
