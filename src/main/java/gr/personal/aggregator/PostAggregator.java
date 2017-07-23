package gr.personal.aggregator;

import gr.personal.consumer.RedditConsumer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Nick Kanakis on 22/7/2017.
 */
@Service
public class PostAggregator {

    @Autowired
    private RedditConsumer redditConsumer;

    public void forwardAggregate(String subreddit) {
        String lastFullname = null;

        //TODO: replace loop with a Executor.
        for (int i = 0; i < 100; i++) {
            JSONArray result;
            if(lastFullname == null)
                result = redditConsumer.fetchInitialPost(subreddit);
            else
                result = redditConsumer.fetchForward(lastFullname);

            String tmpLastFullname = AggregatorUtil.extractLastFullname(result);

            if(tmpLastFullname == null || tmpLastFullname == "" )
                continue;

            lastFullname = tmpLastFullname;
            enqueue(result);
        }

    }

    //TODO: Actually enqueue the result
    private void enqueue(JSONArray result) {
        for (String tmp :AggregatorUtil.extractFullnames(result) ) {
            System.out.println(tmp);
        }
    }
}
