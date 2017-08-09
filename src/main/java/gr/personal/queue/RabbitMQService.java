package gr.personal.queue;

import gr.personal.utils.ModelsUtils;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by nkanakis on 8/9/2017.
 */
@Service
public class RabbitMQService implements QueueService {
    @Autowired
    private Logger logger;

    @Override
    public void enqueueComment(JSONArray result) {
        File log = new File("comments.txt");
        PrintWriter out = null;
        try {
            if (out == null)
                out = new PrintWriter(new FileWriter(log, true));
            for (String id : ModelsUtils.extractIds(result)) {
                out.println(id);
                logger.debug("COMMENT: CurrentTread: {}, ID: {}", Thread.currentThread().getName(), id);
            }
        } catch (IOException e) {

        } finally {
            out.close();
        }
    }

    @Override
    public void enqueuePost(JSONArray result) {
        File log = new File("posts.txt");
        PrintWriter out = null;
        try {
            if (out == null)
                out = new PrintWriter(new FileWriter(log, true));
            for (String id : ModelsUtils.extractIds(result)) {
                out.println(id);
                logger.debug("POST: CurrentTread: {}, ID: {}", Thread.currentThread().getName(), id);
            }
        } catch (IOException e) {

        } finally {
            out.close();

        }
    }
}
