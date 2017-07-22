package gr.personal.consumer;

import gr.personal.consumer.request.RedditRequest;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by nkanakis on 7/20/2017.
 */
@Service
public class RestClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);

    private RestTemplate restTemplate;
    private static double delayInMs = 0;
    private static long lastCall = 0;

    public RestClient() {
        restTemplate = new RestTemplate();
    }

    /**
     * Executes the http call to Reddit API. It enforces delay policy as described here:
     * https://github.com/reddit/reddit/wiki/API
     *
     * @param request: Reddit specific request
     * @return Data object
     */
    public JSONObject executeGetRequestWithDelayPolicy(RedditRequest request) {
        if (delayInMs > 0) {
            try {
                Thread.sleep((long) delayInMs);
            } catch (InterruptedException e) {
                LOGGER.warn("Interrupted Exception thrown while enforcing delay policy", e);
            }
        }

        ResponseEntity<String> response = restTemplate.exchange(request.generateURI(), HttpMethod.GET, request.generateHttpHeaders(), String.class);

        HttpHeaders headers = response.getHeaders();

        double requestsRemaining = Double.valueOf(headers.get("X-Ratelimit-Remaining").get(0));
        double timeRemaining = Double.valueOf(headers.get("X-Ratelimit-Reset").get(0));
        calculateDelay(requestsRemaining, timeRemaining);

        lastCall = System.currentTimeMillis();
        return ConsumerUtil.shortChildrenArray(new JSONObject(response.getBody()).getJSONObject("data"));
    }

    /**
     * Warning: overusing this method will result in violation or Reddit's API Rules
     * More here: https://github.com/reddit/reddit/wiki/API
     *
     * @param request: Reddit specific request
     * @return Data object
     */
    public JSONObject executeGetRequestWithoutDelayPolicy(RedditRequest request) {
        ResponseEntity<String> response = restTemplate.exchange(request.generateURI(), HttpMethod.GET, request.generateHttpHeaders(), String.class);
        return ConsumerUtil.shortChildrenArray(new JSONObject(response.getBody()).getJSONObject("data"));
    }

    private void calculateDelay(double requestsRemaining, double timeRemaining) {
        double rate = 1;

        if (timeRemaining > 0)
            rate = requestsRemaining / timeRemaining;

        if (rate > 1)
            delayInMs = Math.max(0, (1000-System.currentTimeMillis() - lastCall));
        else
            delayInMs = 1000;
    }
}
