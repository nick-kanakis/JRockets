package gr.personal.consumer;

import gr.personal.oauth.Authentication;
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
    Authentication authentication;

    /**
     *  Retrieve the first model for specified subreddit.
     *
     * @param subreddit: subreddit to retrieve latest model from.
     */
    public void getFirstModel(String subreddit){
        SubredditRequest request = new SubredditRequest(subreddit, 1, authentication);
        client.executeGetRequest(request);
    }

    /**
     * Retrieve MAX_MODELS_LIMIT(=100) models for the specified subreddit.
     *
     * @param subreddit subreddit to retrieve latest models from.
     */
    public void getListOfModels(String subreddit){
        SubredditRequest request = new SubredditRequest(subreddit, authentication);
        client.executeGetRequest(request);
    }

}
