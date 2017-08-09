package gr.personal.queue;

import org.json.JSONArray;

/**
 * Created by nkanakis on 8/9/2017.
 */
public interface QueueService {
    void enqueueComment(JSONArray result);

    void enqueuePost(JSONArray result);
}
