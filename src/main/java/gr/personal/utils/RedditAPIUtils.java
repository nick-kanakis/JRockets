package gr.personal.utils;

import gr.personal.consumer.model.Thing;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by nkanakis on 7/13/2017.
 *
 * Utilities that transform data for easier interaction with Reddit's API.
 */
public final class RedditAPIUtils {
    private static final Logger logger = LoggerFactory.getLogger(RedditAPIUtils.class);
    public static final int MAX_MODELS_LIMIT = 100;

    private RedditAPIUtils() {}

    /**
     * Transform mapping to URL key-value parameters (eg: a=1&b=2&c=3).
     *
     * @param keyValueParameters
     * @return Key-Valye pairs formatted for url parameters.
     */
    public static String transformParameters(Map<String, String> keyValueParameters) {

        if(keyValueParameters == null){
            logger.warn("Method transformParameters incorrect argument");
            return "";
        }

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
            // Add key-value pair
            parameters = parameters.concat(key + "=" + value);
        }
        return parameters;
    }

    public static List<Thing> transformFullnamesToThings(String initialFullname, int length){

        if(initialFullname == null || length<0){
            logger.warn("Method transformFullnamesToThings incorrect arguments");
            return new ArrayList<>();
        }

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

    public static String transformFullnamesToCommaSeparated(String initialFullname, int length){
        List<Thing> things = transformFullnamesToThings(initialFullname, length);

        String commaSeparattedFullnames = things.stream().map(t -> t.getFullName()).collect(Collectors.joining(","));
        return commaSeparattedFullnames;
    }

    /**
     * Sometimes Reddit models are not in the right order. Sort them by ascending ID (oldest to newest)
     * @param data
     * @return
     */
    public static JSONArray shortChildrenArray(JSONObject data){
        if(data == null ){
            logger.warn("Method shortChildrenArray incorrect argument");
            return new JSONArray();
        }

        JSONArray unsortedChildren = data.getJSONArray("children");
        JSONArray sortedChildren = new JSONArray();
        List<JSONObject> unsortedChildrenList = new ArrayList<>();

        for (int i = 0; i < unsortedChildren.length(); i++) {
            unsortedChildrenList.add(unsortedChildren.getJSONObject(i));
        }

        List<JSONObject> sortedChildrenList = unsortedChildrenList.stream().sorted((o1, o2) -> {
            String valA = new String();
            String valB = new String();

            try {
                JSONObject data1 = (JSONObject) o1.get("data");
                valA = data1.getString("id");
                JSONObject data2 = (JSONObject) o2.get("data");
                valB = data2.getString("id");
            } catch (JSONException e) {
                logger.warn("Error during shorting of children", e);
            }
            return valA.compareTo(valB);
        }).collect(Collectors.toList());

        for (int i = 0; i < sortedChildrenList.size(); i++) {
            sortedChildren.put(sortedChildrenList.get(i));
        }
        return sortedChildren;
    }
}
