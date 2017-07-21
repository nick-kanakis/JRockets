package gr.personal.consumer;

import gr.personal.consumer.request.Request;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by nkanakis on 7/20/2017.
 */
@Service
public class HttpClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClient.class);

    private RestTemplate restTemplate;

    public HttpClient() {
        restTemplate = new RestTemplate();
    }

    public JSONObject executeGetRequest(Request request){
        ResponseEntity<String> response = restTemplate.exchange(request.generateURI(), HttpMethod.GET , request.generateHttpHeaders(), String.class );
        return ConsumerUtil.shortChildrenArray(new JSONObject(response.getBody()).getJSONObject("data"));
    }
}
