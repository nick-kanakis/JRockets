package gr.personal.oauth;

import gr.personal.oauth.model.AccessToken;
import gr.personal.oauth.model.OAuthResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

/**
 * Created by nkanakis on 7/12/2017.
 */
@Component
public class Authentication {

    private AccessToken token;

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

    RestTemplate restTemplate;

    public Authentication() {
        this.token = null;
    }

    public AccessToken authenticate() {
        /**
         *  If token is not null and has not expired do not request a new one.
         */
        if (token != null && token.hasExpired())
            return token;

        restTemplate = new RestTemplate();

        ResponseEntity<OAuthResponse> oAuthResponseResponseEntity = restTemplate.postForEntity(tokenUrl, constructRequest(), OAuthResponse.class);
        return new AccessToken(oAuthResponseResponseEntity.getBody());
    }

    private HttpEntity<MultiValueMap<String, String>> constructRequest(){
        String authorizationValue = Base64.getEncoder().encodeToString((CLIENT_ID+":"+CLIENT_SECRET).getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic "+authorizationValue);
        headers.add("User-agent", "JRockit 0.1");

        MultiValueMap<String, String> mvm = new LinkedMultiValueMap<String, String>();
        mvm.add("username",USERNAME);
        mvm.add("password",PASSWORD);
        mvm.add("grant_type", "password");

        return new HttpEntity<MultiValueMap<String, String>>(mvm, headers);
    }
}
