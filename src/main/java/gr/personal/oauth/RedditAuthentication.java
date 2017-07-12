package gr.personal.oauth;

import gr.personal.oauth.model.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

/**
 * Created by nkanakis on 7/12/2017.
 */
public class RedditAuthentication {

    private AccessToken token;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${env.useragent}")
    private String USER_AGENT;
    @Value("${env.clientid}")
    private String CLIENT_ID;
    @Value("${env.clientsecret}")
    private String CLIENT_SECRET;
    @Value("${env.username}")
    private String USERNAME;
    @Value("${env.password}")
    private String PASSWORD;

    public RedditAuthentication() {
        this.token = null;
    }

    public AccessToken authenticate() {
        /**
         *  If token is not null and has not expired do not request a new one.
         */
        if (token != null && token.hasExpired())
            return token;

        /* TODO use restTemplate to post @https://www.reddit.com/api/v1/access_token with
         *
         *
         *
         */

        return null;
    }
}
