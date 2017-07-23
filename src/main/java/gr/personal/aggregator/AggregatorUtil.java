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
//TODO: refactor of utils methods
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

    /**
     * Spits a JSONArray into 2 pieces.
     *
     * @param children: List of models to be slitted
     * @param index: Children list will be splitted at index position
     *             eg: children{1,2,3,4,5} & indes = 3 Result: result[0]={1,2,3}, result[1]={4,5}
     * @return
     */
    public static JSONArray[] splitArray(JSONArray children, int index){
        JSONArray part1 = new JSONArray();
        JSONArray part2 = new JSONArray();

        for (int i = 0; i <= children.length() -1 ; i++) {
            if (i<index)
                part1.put(children.get(i));
            else
                part2.put(children.get(i));
        }
        JSONArray[] result = {part1, part2};
        return result;
    }

    public static String increaseByOne(String firstFullnameOfNewModels) {
        String[] split = firstFullnameOfNewModels.split("_");
        long increasedId2Decimal = Long.parseUnsignedLong(split[1], 36) +1;
        return split[0]+"_"+ Long.toString(increasedId2Decimal, 36);
    }

    public static String decreaseByOne(String lastEnqueuedFullname) {
        String[] split = lastEnqueuedFullname.split("_");
        long increasedId2Decimal = Long.parseUnsignedLong(split[1], 36) -1;
        return split[0]+"_"+ Long.toString(increasedId2Decimal, 36);
    }
}
