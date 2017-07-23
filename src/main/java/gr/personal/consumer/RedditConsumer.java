package gr.personal.consumer;

import gr.personal.consumer.request.FullnamesRequest;
import gr.personal.consumer.request.CommentRequest;
import gr.personal.consumer.request.PostRequest;
import gr.personal.consumer.request.RedditRequest;
import gr.personal.oauth.Authentication;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by nkanakis on 7/13/2017.
 */
@Service
public class RedditConsumer {
    @Autowired
    private RestClient client;
    @Autowired
    private Authentication authentication;

    /**
     * Retrieve the first post for specified subreddit.
     *
     * @param subreddit : subreddit to retrieve latest model from.
     * @return (Reddit models) single child object
     */
    public JSONArray fetchInitialPost(String subreddit) {
        RedditRequest request = new PostRequest(subreddit, 1, authentication);
        return client.executeGetRequestWithDelayPolicy(request);
    }

    /**
     * Retrieve the first comment for specified subreddit.
     *
     * @param subreddit : subreddit to retrieve latest model from.
     * @return (Reddit models) single child object
     */
    public JSONArray fetchInitialComment(String subreddit) {
        RedditRequest request = new CommentRequest(subreddit, 1, authentication);
        return client.executeGetRequestWithDelayPolicy(request);
    }

    /**
     * Retrieve last MAX_MODELS_LIMIT(=100) posts
     *
     * @param subreddit : subreddit to retrieve latest models from
     * @return (Reddit models) children
     */
    public JSONArray fetchReversedPosts(String subreddit) {
        RedditRequest request = new PostRequest(subreddit, authentication);
        return client.executeGetRequestWithDelayPolicy(request);
    }

    /**
     * Retrieve last MAX_MODELS_LIMIT(=100) comments
     *
     * @param subreddit : subreddit to retrieve latest models from.
     * @return (Reddit models) children
     */
    public JSONArray fetchReversedComments(String subreddit) {
        RedditRequest request = new CommentRequest(subreddit, authentication);
        return client.executeGetRequestWithDelayPolicy(request);
    }

    /**
     * The method is used to retrieve specific range of fullnames. Starting from (but not included initialFullname)
     * and for the next "length" IDs. eg: if initialFullname = t1_dkio3q5 and length = 3 the method will return:
     * > t1_dkio3q6
     * > t1_dkio3q7
     * > t1_dkio3q8
     * <p>
     * In case the length is > 100 we break it in chunks of 100 fullnames per request and join them again.
     *
     * @param initialFullname : starting point
     * @param length :
     * @return (Reddit models) children
     */
    public JSONArray fetchFullnames(String initialFullname, int length) {

        String commaSeparatedFullnames = ConsumerUtil.transformCommaSeparatedFullnames(initialFullname, length);
        RedditRequest request = new FullnamesRequest(authentication, commaSeparatedFullnames);
        return client.executeGetRequestWithDelayPolicy(request);
    }


    public JSONArray fetchForward(String initialFullname) {
        return fetchFullnames(initialFullname, 100);
    }
}
