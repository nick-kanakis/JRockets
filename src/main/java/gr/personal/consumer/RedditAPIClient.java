package gr.personal.consumer;

import gr.personal.consumer.request.RedditRequest;
import gr.personal.utils.RedditAPIUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by nkanakis on 7/20/2017.
 */
@Component
/**
 *  Handles every interaction with Reddit's API.
 */
public class RedditAPIClient {
    @Autowired
    private Logger logger;

    private RestTemplate restTemplate;
    private static double delayInMs = 0;
    private static long lastCall = 0;

    public RedditAPIClient() {
        restTemplate = new RestTemplate();
    }

    /**
     * Enforces delay policy on HTTP calls as described here:
     * https://github.com/reddit/reddit/wiki/API
     *
     * @param request : Reddit specific request
     * @return Children JSONArray
     */
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public JSONArray executeGetRequestWithDelayPolicy(RedditRequest request) {
        if (delayInMs > 0) {
            try {
                Thread.sleep((long) delayInMs);
            } catch (InterruptedException e) {
                logger.warn("Interrupted Exception thrown while enforcing delay policy", e);
            }
        }

        ResponseEntity<String> response = restTemplate.exchange(request.generateURI(), HttpMethod.GET, request.generateHttpHeaders(), String.class);
        HttpHeaders headers = response.getHeaders();

        double requestsRemaining = Double.valueOf(headers.get("X-Ratelimit-Remaining").get(0));
        double timeRemaining = Double.valueOf(headers.get("X-Ratelimit-Reset").get(0));
        calculateDelay(requestsRemaining, timeRemaining);

        lastCall = System.currentTimeMillis();
        return RedditAPIUtils.shortChildrenArray(new JSONObject(response.getBody()).getJSONObject("data"));
    }

    /**
     * Warning: overusing this method will result in violation or Reddit's API Rules
     * More here: https://github.com/reddit/reddit/wiki/API
     *
     * @param request : Reddit specific request
     * @return Data object
     */
    public JSONArray executeGetRequestWithoutDelayPolicy(RedditRequest request) {
        ResponseEntity<String> response = restTemplate.exchange(request.generateURI(), HttpMethod.GET, request.generateHttpHeaders(), String.class);
        return RedditAPIUtils.shortChildrenArray(new JSONObject(response.getBody()).getJSONObject("data"));
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
