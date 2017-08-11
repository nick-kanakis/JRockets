package gr.personal.queue;

import org.json.JSONObject;

/**
 * Created by nkanakis on 8/9/2017.
 */
public interface QueueService {
    void enqueueComment(JSONObject model2Enqueue);

    void enqueuePost(JSONObject model2Enqueue);
}
