package gr.personal.oauth;

import gr.personal.oauth.model.AccessToken;
import gr.personal.oauth.model.OAuthResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Created by nkanakis on 7/12/2017.
 */
@Service
public class Authentication {

    private AccessToken token;
    private RestTemplate restTemplate;

    @Value("${env.clientid}")
    private String CLIENT_ID;
    @Value("${env.clientsecret}")
    private String CLIENT_SECRET;
    @Value("${env.username}")
    private String USERNAME;
    @Value("${env.password}")
    private String PASSWORD;
    @Value("${reddit.accestoken.url}")
    private String tokenUrl;
    @Value("{app.useragent}")
    private String userAgent;

    public Authentication() {
        restTemplate = new RestTemplate();
        this.token = null;
    }

    @Retryable(maxAttempts = 10, backoff = @Backoff(delay = 2000))
    public AccessToken getAccessToken() {
        /**
         *  If token is not null and has not expired do not request a new one.
         */
        if (token != null && token.hasExpired())
            return token;
        ResponseEntity<OAuthResponse> oAuthResponseResponseEntity = restTemplate.postForEntity(tokenUrl, constructRequest(), OAuthResponse.class);
        token = new AccessToken(oAuthResponseResponseEntity.getBody());
        return token;
    }

    private HttpEntity<MultiValueMap<String, String>> constructRequest(){
        String authorizationValue = Base64Utils.encodeToString((CLIENT_ID+":"+CLIENT_SECRET).getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic "+authorizationValue);
        headers.add("User-agent", userAgent);

        MultiValueMap<String, String> mvm = new LinkedMultiValueMap<String, String>();
        mvm.add("username",USERNAME);
        mvm.add("password",PASSWORD);
        mvm.add("grant_type", "password");

        return new HttpEntity<MultiValueMap<String, String>>(mvm, headers);
    }
}
