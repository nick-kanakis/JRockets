package gr.personal.helper;

import gr.personal.utils.ModelsUtils;
import gr.personal.utils.PropertyReader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Kanakis on 26/7/2017.
 */
public class OuputValidator {

    private static final Logger logger = LoggerFactory.getLogger(OuputValidator.class);

    /**
     * Given a List of models, the method checks for out of sequence ids and returns the inconsistencies fullnames.
     *
     * @param models
     * @return missing fullnames.
     */
    public static List<String> checkIncrementalIds(List<JSONObject> models) throws Exception {
        long previousId = -1;
        List<String> inconsistentFullnames = new ArrayList<>();

        for (JSONObject model : models) {

            long tmpId = Long.parseLong(model.getJSONObject("data").getString("id"), 36);

            if (previousId == -1) {
                previousId = tmpId;
                continue;
            }
            if (previousId > tmpId)
                throw new Exception("Unsorted list");
            if (previousId == tmpId)
                throw new Exception("Duplicate values");
            if (tmpId - previousId > 1) {
                inconsistentFullnames.add(ModelsUtils.decreaseFullnameByOne(model.getJSONObject("data").getString("name")));
            }
            previousId = tmpId;
        }
        return inconsistentFullnames;
    }

    /**
     * Checks how many of the given fullnames are actual valid fullnames
     *
     * @param fullnames List of ids to check
     * @return number of valid ids
     */
    public static int checkValidFullnames(List<String> fullnames) throws JSONException, InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        //default userAgent in case of IOException when reading properties file
        String userAgent = "web_backend:JRockets_bot:JRockets:v0.1";
        try {
            userAgent = PropertyReader.fetchValue("app.useragent");
        } catch (IOException e) {
            logger.warn("Unable to fetch properties", e);
        }
        headers.set("User-agent", userAgent);
        int inconsistenciesCounter = 0;
        for (String fullname : fullnames) {
            ResponseEntity<String> response = restTemplate.exchange("https://www.reddit.com/api/info.json?id=" + fullname, HttpMethod.GET, new HttpEntity(headers), String.class);
            JSONArray children = new JSONObject(response.getBody()).getJSONObject("data").getJSONArray("children");
            if (children.length() > 0)
                inconsistenciesCounter++;
            Thread.sleep(2000);
        }
        return inconsistenciesCounter;
    }
}
