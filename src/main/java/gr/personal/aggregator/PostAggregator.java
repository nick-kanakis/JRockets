package gr.personal.aggregator;

import gr.personal.consumer.ConsumerUtil;
import gr.personal.consumer.RedditConsumer;
import gr.personal.consumer.model.Thing;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by Nick Kanakis on 22/7/2017.
 */
@Service
public class PostAggregator {

    @Autowired
    private RedditConsumer redditConsumer;
    private static String lastFullname = null;

    public void forwardAggregate(String subreddit) {
        JSONArray result;
        if (lastFullname == null)
            result = redditConsumer.fetchInitialPost(subreddit);
        else
            result = redditConsumer.fetchForward(lastFullname);

        String tmpLastFullname = AggregatorUtil.extractLastFullname(result);

        if (tmpLastFullname == null || tmpLastFullname == "")
            return;

        lastFullname = tmpLastFullname;
        enqueue(result);

    }

    //TODO: Actually enqueue the result
    private void enqueue(JSONArray result) {
        PrintWriter writer =null;
        try {
            writer = new PrintWriter("posts.txt", "UTF-8");
            for (String tmp : AggregatorUtil.extractFullnames(result)) {
                writer.println("POST: CurrentTread: " + Thread.currentThread().getName() + ", ID: " + tmp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        finally {
            writer.close();
        }
    }
    }
