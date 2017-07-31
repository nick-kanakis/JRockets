package gr.personal.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Nick Kanakis on 31/7/2017.
 */
@RunWith(SpringRunner.class)
public class JSONArrayUtilsTest {

    JSONArray mockObject;

    @Before
    public void setUp() throws Exception {

        JSONArray childrenArray = new JSONArray();
        for(int i =0; i<=3; i++){
            JSONObject model = new JSONObject();
            JSONObject data = new JSONObject();

            model.put("kind","t1");
            data.put("name",i+1);

            model.put("data", data);
            childrenArray.put(i,model);
        }
        mockObject =  childrenArray;
    }

    @Test
    public void testSplitArray() throws Exception {
        JSONArray part1 = new JSONArray();
        JSONArray part2 = new JSONArray();

        for(int i =0; i<4; i++){
            JSONObject model = new JSONObject();
            JSONObject data = new JSONObject();

            model.put("kind","t1");
            data.put("name",i+1);

            model.put("data", data);
            if(i<3)
                part1.put(model);
            else
                part2.put(model);
        }
        JSONArray[] correctArray = {part1,part2};

        JSONArray[] slicedArray = JSONArrayUtils.splitArray(mockObject, 3);

        for (int i = 0; i < 2; i++) {

            JSONArray sliced = slicedArray[i];
            JSONArray correct = correctArray[i];

            for (int j = 0; j < correct.length() -1 ; j++) {
                String correctName = correct.getJSONObject(i).getJSONObject("data").getString("name");
                String slicedName = sliced.getJSONObject(i).getJSONObject("data").getString("name");
                Assert.assertEquals(correctName, slicedName);
            }
        }
    }

    @Test
    public void testConcatArray() throws Exception {
        JSONArray part1 = new JSONArray();
        JSONArray part2 = new JSONArray();

        for(int i =0; i<5; i++){
            JSONObject model = new JSONObject();

            model.put("id",i+1);
            if(i<3)
                part1.put(model);
            else
                part2.put(model);
        }

        JSONArray concatArray = JSONArrayUtils.concatArray(part1, part2);

        Assert.assertEquals(5, concatArray.getJSONObject(concatArray.length()-1).getInt("id"));
    }
}
