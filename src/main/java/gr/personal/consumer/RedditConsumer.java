package gr.personal.consumer;

import gr.personal.consumer.request.BacklogRequest;
import gr.personal.consumer.request.CommentRequest;
import gr.personal.consumer.request.PostRequest;
import gr.personal.consumer.request.Request;
import gr.personal.oauth.Authentication;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by nkanakis on 7/13/2017.
 */
@Service
public class RedditConsumer {
    @Autowired
    private HttpClient client;
    @Autowired
    private Authentication authentication;

    /**
     *  Retrieve the first post for specified subreddit.
     *
     * @param subreddit: subreddit to retrieve latest model from.
     * @return (Reddit models) single child object
     */
    public void fetchInitialPost(String subreddit){
        Request request = new PostRequest(subreddit, 1, authentication);
        JSONObject children = client.executeGetRequest(request);
    }

    /**
     *  Retrieve the first comment for specified subreddit.
     *
     * @param subreddit : subreddit to retrieve latest model from.
     * @return (Reddit models) single child object
     */
    public JSONObject fetchInitialComment(String subreddit){
        Request request = new CommentRequest(subreddit, 1, authentication);
        return client.executeGetRequest(request);
    }

    /**
     * Retrieve last MAX_MODELS_LIMIT(=100) posts
     *
     * @param subreddit : subreddit to retrieve latest models from
     * @return (Reddit models) children
     */
    public JSONObject fetchReversedPosts(String subreddit){
        Request request = new PostRequest(subreddit, authentication);
        return client.executeGetRequest(request);
    }

    /**
     * Retrieve last MAX_MODELS_LIMIT(=100) comments
     *
     * @param subreddit : subreddit to retrieve latest models from.
     * @return (Reddit models) children
     */
    public JSONObject fetchReversedComments(String subreddit){
        Request request = new CommentRequest(subreddit, authentication);
        return client.executeGetRequest(request);
    }

    /**
     * The method is used to retrieve specific range of fullnames. Starting from (but not included initialFullname)
     * and for the next "length" IDs. eg: if initialFullname = t1_dkio3q5 and length = 3 the method will return:
     *  > t1_dkio3q6
     *  > t1_dkio3q7
     *  > t1_dkio3q8
     *
     * @param initialFullname: starting point
     * @param length:
     * @return (Reddit models) children
     */

    public JSONObject fetchBacklog(String initialFullname, long length){
        String commaSeparatedFullnames = ConsumerUtil.transformCommaSeparatedFullnames(initialFullname, length);
        Request request = new BacklogRequest( authentication, commaSeparatedFullnames);
        return client.executeGetRequest(request);
    }
}
