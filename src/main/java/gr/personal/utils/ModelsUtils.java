package gr.personal.utils;

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
//TODO: Decrease the number of *Utils classes
public class ModelsUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelsUtils.class);

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

    public static String extractFirstFullname(JSONArray children){
        int firstIndex = 0;
        return extractFullname(children, firstIndex);
    }

    public static String extractLastId(JSONArray children){
        int lastIndex = children.length() -1;
        String fullname = extractFullname(children, lastIndex);
        return new Thing(fullname).getId();
    }

    public static String extractFirstId(JSONArray children){
        String fullname = extractFullname(children, 0);
        return new Thing(fullname).getId();

    }

    public static List<String> extractFullnames(JSONArray children){
        List<String> result = new ArrayList<>();

        for (int i = 0; i <= children.length() -1; i++) {
            JSONObject innerModel = children.getJSONObject(i);
            result.add(innerModel.getJSONObject("data").getString("name"));
        }
        return result;
    }

    public static List<String> extractIds(JSONArray children){
        List<String> result = new ArrayList<>();

        for (int i = 0; i <= children.length() -1; i++) {
            JSONObject innerModel = children.getJSONObject(i);
            result.add(innerModel.getJSONObject("data").getString("id"));
        }
        return result;
    }

    public static String increaseFullnameByOne(String firstFullnameOfNewModels) {
        String[] split = firstFullnameOfNewModels.split("_");
        long increasedId2Decimal = Long.parseUnsignedLong(split[1], 36) +1;
        return split[0]+"_"+ Long.toString(increasedId2Decimal, 36);
    }

    public static String decreaseFullnameByOne(String lastEnqueuedFullname) {
        String[] split = lastEnqueuedFullname.split("_");
        long decreasedId2Decimal = Long.parseUnsignedLong(split[1], 36) -1;
        return split[0]+"_"+ Long.toString(decreasedId2Decimal, 36);
    }
}
