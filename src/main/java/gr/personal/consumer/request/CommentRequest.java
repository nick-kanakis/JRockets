package gr.personal.consumer.request;

import gr.personal.utils.RedditAPIUtils;
import gr.personal.oauth.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by nkanakis on 7/21/2017.
 */
public class CommentRequest extends RedditRequest {

    @Autowired
    private Logger logger;
    private static final String ENDPOINT_FORMAT = "https://oauth.reddit.com/r/%s/comments.json?%s";

    public CommentRequest(String subreddit, Map<String, String> parameters, long limit, Authentication authentication) {
        super(subreddit, parameters, limit, authentication);
        parameters.put("sort","new");
    }

    public CommentRequest(String subreddit, Map<String, String> parameters, Authentication authentication) {
        super(subreddit, parameters, authentication);
        parameters.put("sort","new");
    }

    public CommentRequest(String subreddit, long limit, Authentication authentication) {
        super(subreddit, limit, authentication);
        parameters.put("sort","new");
    }

    public CommentRequest(String subreddit, Authentication authentication) {
        super(subreddit, authentication);
        parameters.put("sort","new");
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
