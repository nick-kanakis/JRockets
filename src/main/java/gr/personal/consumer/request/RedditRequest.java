package gr.personal.consumer.request;

import gr.personal.oauth.Authentication;
import gr.personal.utils.PropertyReader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nkanakis on 7/21/2017.
 */
public abstract class RedditRequest {

    private static final int MAX_MODELS_LIMIT = 100;

    private String userAgent;
    private Authentication authentication;
    protected String subreddit;
    protected Map<String, String> parameters;

    public RedditRequest(String subreddit, Map<String, String> parameters, long limit, Authentication authentication) {
        this.subreddit = subreddit;
        this.parameters = parameters;
        this.authentication = authentication;
        this.userAgent = PropertyReader.fetchValue("app.useragent");

        if (parameters == null) {
            this.parameters = new HashMap<String, String>();
        }
        if(limit > MAX_MODELS_LIMIT)
            limit = MAX_MODELS_LIMIT;

        this.parameters.put("limit", String.valueOf(limit));
    }

    public RedditRequest(String subreddit, Map<String, String> parameters, Authentication authentication) {
        this(subreddit, parameters, MAX_MODELS_LIMIT, authentication);
    }

    public RedditRequest(String subreddit, long limit, Authentication authentication) {
        this(subreddit, new HashMap<String, String>(), limit, authentication);
    }

    public RedditRequest(String subreddit, Authentication authentication) {
        this(subreddit, new HashMap<String, String>(), authentication);
    }

    /**
     * Constructs headers necessary for HTTP request to Reddit API.
     *
     * @return Http Entity containing Headers for http request to reddit.
     */
    public HttpEntity generateHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authentication.getAccessToken().getToken());
        headers.set("User-agent", userAgent);

        return new HttpEntity(headers);
    }
    public abstract String generateURI();

}
