package gr.personal.aggregator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Kanakis on 22/7/2017.
 */
public class AggregatorUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(AggregatorUtil.class);

    public static String extractFullname(JSONObject model, int index){
        if(index<0)
            return "";

        if(!model.has("children")){
            LOGGER.warn("Model must contain 'children' key");
            return "";
        }
        JSONArray children = model.getJSONArray("children");
        JSONObject innerModel;

        innerModel = children.getJSONObject(index);


        if(!innerModel.has("data")){
            LOGGER.warn("Model must contain 'data' key");
            return "";
        }
        JSONObject innerData = innerModel.getJSONObject("data");
        if(!innerData.has("name")){
            LOGGER.warn("Model must contain 'name' key");
            return "";
        }
        return innerData.getString("name");
    }

    public static String extractInitialFilename(JSONObject model){
        return extractFullname(model, 0);
    }

    public static String extractLastFullname(JSONObject model){
        if(!model.has("children")){
            LOGGER.warn("Model must contain 'children' key");
            return "";
        }
        int lastIndex = model.getJSONArray("children").length() -1;
        return extractFullname(model, lastIndex);
    }

    //TODO: do i need it?
    public static List<String> extractFullnames(JSONObject model){
        List<String> result = new ArrayList<>();
        if(!model.has("children")){
            LOGGER.warn("Model must contain 'children' key");
            return result;
        }
        JSONArray children = model.getJSONArray("children");
        for (int i = 0; i <= children.length() -1; i++) {
            JSONObject innerModel = children.getJSONObject(i);
            result.add(innerModel.getJSONObject("data").getString("name"));
        }
        return result;
    }
}
