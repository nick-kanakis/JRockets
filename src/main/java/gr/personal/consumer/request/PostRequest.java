package gr.personal.consumer.request;

import gr.personal.oauth.Authentication;
import gr.personal.utils.RedditAPIUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by nkanakis on 7/13/2017.
 */
public class PostRequest extends RedditRequest {
    private static final String ENDPOINT_FORMAT = "https://oauth.reddit.com/r/%s/new.json?%s";

    public PostRequest(String subreddit, Map<String, String> parameters, long limit, Authentication authentication) {
        super(subreddit, parameters, limit, authentication);
    }

    public PostRequest(String subreddit, Map<String, String> parameters, Authentication authentication) {
        super(subreddit, parameters, authentication);
    }

    public PostRequest(String subreddit, long limit, Authentication authentication) {
        super(subreddit, limit, authentication);
    }

    public PostRequest(String subreddit, Authentication authentication) {
        super(subreddit, authentication);
    }

    /**
     * Constructs URI for HTTP request to Reddit API.
     *
     * @return URI endpoint for reddit HTTP request
     */
    public String generateURI() {
        return String.format(ENDPOINT_FORMAT, subreddit, RedditAPIUtils.transformParameters(parameters));
    }


}

