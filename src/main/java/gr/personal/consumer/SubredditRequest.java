package gr.personal.consumer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nkanakis on 7/13/2017.
 */
public class SubredditRequest {
    private static final String ENDPOINT_FORMAT = "https://oauth.reddit.com/r/%s/new.json?%s";
    private static final long MAX_MODELS_LIMIT = 100;

    private String subreddit;
    private Map<String, String> parameters;

    public SubredditRequest(String subreddit, Map<String, String> parameters, long limit ){
        this.subreddit = subreddit;
        this.parameters = parameters;

        if(parameters == null){
            this.parameters = new HashMap<String, String>();
        }

        this.parameters.put("limit", String.valueOf(limit));
    }

    public SubredditRequest(String subreddit, Map<String, String> parameters ) {
        this(subreddit, parameters, MAX_MODELS_LIMIT);
    }

    public SubredditRequest(String subreddit, long limit){
        this(subreddit, new HashMap<String, String>(), limit);
    }

    public SubredditRequest(String subreddit){
        this(subreddit, new HashMap<String, String>());
    }

    public String generateURI(){
        return String.format(ENDPOINT_FORMAT, subreddit, ConsumerUtil.transformParameters(parameters));
    }
}
