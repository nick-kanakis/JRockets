package gr.personal.helper;

import gr.personal.aggregator.AggregatorUtil;
import gr.personal.utils.PropertyReader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Kanakis on 26/7/2017.
 */
public class OuputValidator {

    /**
     * Given a file, the method checks for out of sequence ids and returns the inconsistencies fullnames.
     *
     * @param filepath
     * @return missing fullnames.
     */
    public static List<String> checkIncrementalIds(String filepath, boolean isComment) throws Exception {
        long previousId = -1;
        List<String> inconsistentFullnames = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                long tmpId = Long.parseLong(line, 36);

                if (previousId == -1) {
                    previousId = tmpId;
                    continue;
                }
                if (previousId > tmpId)
                    throw new Exception("Unsorted list");
                if (previousId == tmpId)
                    throw new Exception("Duplicate values");
                if (tmpId - previousId > 1) {
                    if (isComment)
                        inconsistentFullnames.add(AggregatorUtil.decreaseByOne("t1_"+line));
                    else
                        inconsistentFullnames.add(AggregatorUtil.decreaseByOne("t3_"+line));
                }
                previousId = tmpId;
            }
        }

        File file = new File(filepath);
        file.delete();
        return inconsistentFullnames;
    }

    /**
     * Checks how many of the given fullnames are actual valid fullnames
     *
     * @param fullnames List of ids to check
     * @return number of valid ids
     */
    public static int checkValideFullnames(List<String> fullnames) throws JSONException, InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-agent", PropertyReader.fetchValue("app.useragent"));
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
