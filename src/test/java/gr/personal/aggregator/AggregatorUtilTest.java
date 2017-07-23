package gr.personal.aggregator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Kanakis on 22/7/2017.
 */
@RunWith(SpringRunner.class)
public class AggregatorUtilTest {

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
    public void testExtractInitialFullename() throws Exception {
        String id = AggregatorUtil.extractInitialFilename(mockObject);
        Assert.assertEquals("1",id);
    }

    @Test
    public void testExtractLastFullname() throws Exception {
        String id = AggregatorUtil.extractLastFullname(mockObject);
        Assert.assertEquals("4",id);
    }

    @Test
    public void testExtractFullnames() throws Exception {
        List<String> results = AggregatorUtil.extractFullnames(mockObject);
        List<String> expectedResult = new ArrayList<>();

        for (int i = 0; i <=3 ; i++) {
            expectedResult.add(String.valueOf(i+1));
        }
        Assert.assertEquals(expectedResult,results);
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

        JSONArray[] slicedArray = AggregatorUtil.splitArray(mockObject, 3);

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
    public void testIncreaseByOne() throws Exception {
        String increasedByOne = AggregatorUtil.increaseByOne("t1_dkijto7");
        Assert.assertEquals("t1_dkijto8", increasedByOne);
    }

    @Test
    public void testDecreaseByOne() throws Exception {
        String decreasedByOne = AggregatorUtil.decreaseByOne("t1_dkijto7");
        Assert.assertEquals("t1_dkijto6", decreasedByOne);
    }
}
