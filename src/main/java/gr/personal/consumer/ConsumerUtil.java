package gr.personal.consumer;

import gr.personal.consumer.model.Kind;
import gr.personal.consumer.model.Thing;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by nkanakis on 7/13/2017.
 */
public class ConsumerUtil {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerUtil.class);
    private static final long MAX_MODELS_LIMIT = 100;
    /**
     * Transform mapping to URL key-value parameters (eg: a=1&b=2&c=3).
     *
     * @param keyValueParameters
     * @return Key-Valye pairs formatted for url parameters.
     */
    public static String transformParameters(Map<String, String> keyValueParameters) {

        Set<String> keys = keyValueParameters.keySet();
        String parameters="";
        boolean start = true;
        for (String key : keys) {

            if (!start) {
                parameters = parameters.concat("&");
            } else {
                start = false;
            }

            String value = keyValueParameters.get(key);
                try {
                    value = URLEncoder.encode(value, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    logger.warn("Value cannot be encoded to URL parameter", e);
                }
            // Add key-value pair
            parameters = parameters.concat(key + "=" + value);
        }

        return parameters;
    }

    public static List<Thing> transformFullnames(String initialFullname, long length){
        if(length>MAX_MODELS_LIMIT) {
            length = MAX_MODELS_LIMIT;
        }
        Thing initialThing = new Thing(initialFullname);
        List<Thing> things = new ArrayList<Thing>();

        long initialIdToDecimal = Long.valueOf(initialThing.getId(), 36);

        for(long currentId =initialIdToDecimal +1; currentId <initialIdToDecimal+length+ 1; currentId++){
            things.add(new Thing(initialThing.getKind(), currentId));
        }
        return things;
    }

    public static String transformCommaSeparatedFullnames(String initialFullname, long length){
        List<Thing> things = transformFullnames(initialFullname, length);

        String commaSeparattedFullnames = things.stream().map(t -> t.getFullName()).collect(Collectors.joining(","));
        return commaSeparattedFullnames;
    }

    /**
     * Sometimes Reddit models are not in the right order. Sort them by ascending ID (oldest to newest)
     * @param data
     * @return
     */
    public static JSONObject shortChildrenArray(JSONObject data){

        JSONArray unsortedChildren = data.getJSONArray("children");
        JSONArray sortedChildren = new JSONArray();
        List<JSONObject> unsortedChildrenList = new ArrayList<>();


        for (int i = 0; i < unsortedChildren.length(); i++) {
            unsortedChildrenList.add(unsortedChildren.getJSONObject(i));
        }

        unsortedChildrenList.sort((o1, o2) -> {
            String valA = new String();
            String valB = new String();

            try {
                JSONObject data1 = (JSONObject) o1.get("data");
                valA = data1.getString("id");
                JSONObject data2 = (JSONObject) o2.get("data");
                valB = data2.getString("id");
            }
            catch (JSONException e) {
                logger.warn("Error during shorting of children", e);
            }
            return valA.compareTo(valB);
        });

        for (int i = 0; i < unsortedChildren.length(); i++) {
            sortedChildren.put(unsortedChildren.get(i));
        }
        data.put("children", sortedChildren);
        return data;
    }

}
