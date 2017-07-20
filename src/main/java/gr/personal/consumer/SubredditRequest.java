package gr.personal.consumer;

import gr.personal.oauth.Authentication;
import gr.personal.utils.PropertyReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nkanakis on 7/13/2017.
 */
public class SubredditRequest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubredditRequest.class);

    private static final String ENDPOINT_FORMAT = "https://oauth.reddit.com/r/%s/new.json?%s";
    private static final long MAX_MODELS_LIMIT = 100;

    private String userAgent;
    private Authentication authentication;
    private String subreddit;
    private Map<String, String> parameters;

    public SubredditRequest(String subreddit, Map<String, String> parameters, long limit, Authentication authentication) {
        this.subreddit = subreddit;
        this.parameters = parameters;
        this.authentication = authentication;
        this.userAgent = PropertyReader.fetchValue("app.useragent");

        if (parameters == null) {
            this.parameters = new HashMap<String, String>();
        }

        this.parameters.put("limit", String.valueOf(limit));
    }

    public SubredditRequest(String subreddit, Map<String, String> parameters, Authentication authentication) {
        this(subreddit, parameters, MAX_MODELS_LIMIT, authentication);
    }

    public SubredditRequest(String subreddit, long limit, Authentication authentication) {
        this(subreddit, new HashMap<String, String>(), limit, authentication);
    }

    public SubredditRequest(String subreddit, Authentication authentication) {
        this(subreddit, new HashMap<String, String>(), authentication);
    }

    /**
     * Constructs URI for HTTP request to Reddit API.
     *
     * @return URI endpoint for reddit HTTP request
     */
    public String generateURI() {
        return String.format(ENDPOINT_FORMAT, subreddit, ConsumerUtil.transformParameters(parameters));
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
}

