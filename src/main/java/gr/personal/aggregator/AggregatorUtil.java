package gr.personal.aggregator;

import gr.personal.consumer.model.Thing;
import org.json.JSONArray;
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

    public static String extractFullname(JSONArray children, int index){
        if(index<0)
            return "";
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

    public static String extractInitialFilename(JSONArray children){
        return extractFullname(children, 0);
    }

    public static String extractLastFullname(JSONArray children){

        int lastIndex = children.length() -1;
        return extractFullname(children, lastIndex);
    }

    public static String extractLastId(JSONArray children){

        int lastIndex = children.length() -1;
        String fullname = extractFullname(children, lastIndex);
        return new Thing(fullname).getId();

    }

    //TODO: do i need it?
    public static List<String> extractFullnames(JSONArray children){
        List<String> result = new ArrayList<>();

        for (int i = 0; i <= children.length() -1; i++) {
            JSONObject innerModel = children.getJSONObject(i);
            result.add(innerModel.getJSONObject("data").getString("name"));
        }
        return result;
    }

    public static JSONArray sliceModel(JSONArray children, int index){
        for (int i = 0; i < index; i++) {
            children.remove(i);
        }
        return children;
    }
}
