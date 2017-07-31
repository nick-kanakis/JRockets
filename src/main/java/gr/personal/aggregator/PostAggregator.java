package gr.personal.aggregator;

import gr.personal.consumer.RedditConsumer;
import gr.personal.utils.ModelsUtils;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * Created by Nick Kanakis on 22/7/2017.
 */
@Service
public class PostAggregator {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostAggregator.class);

    @Autowired
    private RedditConsumer redditConsumer;
    private static String lastFullname = null;

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
        enqueue(result);
    }

    //TODO: Actually enqueue the result
    private void enqueue(JSONArray result) {
        File log = new File("posts.txt");
        PrintWriter out = null;
        try {
            if (out == null)
                out = new PrintWriter(new FileWriter(log, true));
            for (String id : ModelsUtils.extractIds(result)) {
                out.println(id);
                LOGGER.info("POST: CurrentTread: " + Thread.currentThread().getName() + ", ID: " + id);
            }
        } catch (IOException e) {

        } finally {
            out.close();
        }
    }
}
