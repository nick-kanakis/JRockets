package gr.personal.utils;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Nick Kanakis on 31/7/2017.
 */
public class JSONArrayUtils {

    public static JSONArray concatArray(JSONArray... arrs)
            throws JSONException {
        JSONArray result = new JSONArray();
        for (JSONArray arr : arrs) {
            for (int i = 0; i < arr.length(); i++) {
                result.put(arr.get(i));
            }
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
}
