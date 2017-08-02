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
//TODO: Replace statics with services.
public final class ModelsUtils {
    private static final Logger logger = LoggerFactory.getLogger(ModelsUtils.class);

    private ModelsUtils() {}

    public static String extractFullname(JSONArray children, int index){
        if(index<0)
            return "";
        JSONObject innerModel;

        if(children ==null || index <0){
            logger.warn("Method extractFullname incorrect arguments");
            return "";
        }

        innerModel = children.getJSONObject(index);

        if(!innerModel.has("data")){
            logger.warn("Model must contain 'data' key");
            return "";
        }
        JSONObject innerData = innerModel.getJSONObject("data");
        if(!innerData.has("name")){
            logger.warn("Model must contain 'name' key");
            return "";
        }
        return innerData.getString("name");
    }

    public static String extractInitialFullname(JSONArray children){
        return extractFullname(children, 0);
    }

    public static String extractLastFullname(JSONArray children){
        if(children ==null ){
            logger.warn("Method extractLastFullname incorrect argument");
            return "";
        }
        int lastIndex = children.length() -1;
        return extractFullname(children, lastIndex);
    }

    public static String extractFirstFullname(JSONArray children){
        int firstIndex = 0;
        return extractFullname(children, firstIndex);
    }

    public static String extractLastId(JSONArray children){
        if(children ==null ){
            logger.warn("Method extractLastId incorrect argument");
            return "";
        }
        int lastIndex = children.length() -1;
        String fullname = extractFullname(children, lastIndex);
        return new Thing(fullname).getId();
    }

    public static String extractFirstId(JSONArray children){
        String fullname = extractFullname(children, 0);
        return new Thing(fullname).getId();

    }

    public static List<String> extractFullnames(JSONArray children){
        if(children ==null ){
            logger.warn("Method extractFullnames incorrect argument");
            return new ArrayList<>();
        }

        List<String> result = new ArrayList<>();

        for (int i = 0; i <= children.length() -1; i++) {
            JSONObject innerModel = children.getJSONObject(i);
            result.add(innerModel.getJSONObject("data").getString("name"));
        }
        return result;
    }

    public static List<String> extractIds(JSONArray children){
        if(children ==null ){
            logger.warn("Method extractIds incorrect argument");
            return new ArrayList<>();
        }
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
