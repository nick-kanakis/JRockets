package gr.personal.queue;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by nkanakis on 8/9/2017.
 */
@Service
public class RabbitMQService implements QueueService {

    @Override
    public void enqueueComment(JSONObject model2Enqueue) {
        //todo implement enqueue
    }

    @Override
    public void enqueuePost(JSONObject model2Enqueue) {
      //todo implement enqueue
    }
}
