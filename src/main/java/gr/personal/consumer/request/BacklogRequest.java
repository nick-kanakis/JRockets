package gr.personal.consumer.request;

import gr.personal.consumer.ConsumerUtil;
import gr.personal.oauth.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by nkanakis on 7/21/2017.
 */
public class BacklogRequest extends Request {
    private static final Logger LOGGER = LoggerFactory.getLogger(BacklogRequest.class);
    private static final String ENDPOINT_FORMAT = "https://oauth.reddit.com/api/info?%s";

    public BacklogRequest( Map<String, String> parameters, long limit, Authentication authentication, String fullnames) {
        super(null, parameters, limit, authentication);
        parameters.put("id",fullnames);
    }

    public BacklogRequest( Map<String, String> parameters, Authentication authentication, String fullnames) {
        super(null, parameters, authentication);
        parameters.put("id",fullnames);
    }

    public BacklogRequest(long limit, Authentication authentication, String fullnames) {
        super(null, limit, authentication);
        parameters.put("id",fullnames);
    }

    public BacklogRequest(Authentication authentication, String fullnames) {
        super(null, authentication);
        parameters.put("id",fullnames);
    }

    @Override
    public String generateURI() {
            return String.format(ENDPOINT_FORMAT, ConsumerUtil.transformParameters(parameters));
    }
}
