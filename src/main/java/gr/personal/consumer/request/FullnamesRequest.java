package gr.personal.consumer.request;

import gr.personal.oauth.Authentication;
import gr.personal.utils.RedditAPIUtils;

import java.util.Map;

/**
 * Created by nkanakis on 7/21/2017.
 */
public class FullnamesRequest extends RedditRequest {

    private static final String ENDPOINT_FORMAT = "https://oauth.reddit.com/api/info?%s";

    public FullnamesRequest(Map<String, String> parameters, long limit, Authentication authentication, String fullnames) {
        super(null, parameters, limit, authentication);
        parameters.put("id",fullnames);
    }

    public FullnamesRequest(Map<String, String> parameters, Authentication authentication, String fullnames) {
        super(null, parameters, authentication);
        parameters.put("id",fullnames);
    }

    public FullnamesRequest(long limit, Authentication authentication, String fullnames) {
        super(null, limit, authentication);
        parameters.put("id",fullnames);
    }

    public FullnamesRequest(Authentication authentication, String fullnames) {
        super(null, authentication);
        parameters.put("id",fullnames);
    }

    @Override
    public String generateURI() {
            return String.format(ENDPOINT_FORMAT, RedditAPIUtils.transformParameters(parameters));
    }
}
