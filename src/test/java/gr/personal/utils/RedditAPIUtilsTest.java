package gr.personal.utils;

import gr.personal.consumer.model.Thing;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.Matchers.is;

/**
 * Created by nkanakis on 7/21/2017.
 */
@RunWith(SpringRunner.class)
public class RedditAPIUtilsTest {

    @Test
    public void testTransformParameters() throws Exception {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("a","1");
        parameters.put("b","2");

        String urlEndocedParameters = RedditAPIUtils.transformParameters(parameters);
        Assert.assertEquals("a=1&b=2", urlEndocedParameters);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToTransformFullnames() throws Exception {
        RedditAPIUtils.transformFullnamesToCommaSeparated("asdasd", 3);

    }

    @Test
    public void testTransformFullnamesToCommaSeparated() throws Exception {
        String commaSeparatedFullnames = RedditAPIUtils.transformFullnamesToCommaSeparated("t1_dkio3q5", 3);
        Assert.assertEquals("t1_dkio3q6,t1_dkio3q7,t1_dkio3q8",commaSeparatedFullnames);
    }

    @Test
    public void testShortChildrenArray() throws Exception {

        JSONArray childrenArray = new JSONArray();
        for(int i =0; i<=3; i++){
            JSONObject model = new JSONObject();
            JSONObject data = new JSONObject();

            model.put("kind","t1");
            data.put("id", ThreadLocalRandom.current().nextInt(1, 100 + 1));

            model.put("data", data);
            childrenArray.put(i,model);
        }

        JSONObject mockObject = new JSONObject();
        mockObject.put("outerKey", "outerValue");
        mockObject.put("children", childrenArray);

        JSONArray children = RedditAPIUtils.shortChildrenArray(mockObject);

        List<JSONObject> sortedChildrenList = new ArrayList<>();

        for (int i = 0; i < children.length(); i++) {
            sortedChildrenList.add(children.getJSONObject(i));
        }

        int previousId = 200;
        for (JSONObject jsonObject :sortedChildrenList) {
            int currentId = jsonObject.getJSONObject("data").getInt("id");

            if(previousId == 200)
                previousId = currentId;
            else
                Assert.assertFalse(previousId >= currentId);
        }
    }

    @Test
    public void testShortChildrenArrayWithNullData() throws Exception {
        Assert.assertNotNull(RedditAPIUtils.shortChildrenArray(null));
    }

    @Test
    public void testTransformFullnamesToThingsNullInput() throws Exception{
        List<Thing> things = RedditAPIUtils.transformFullnamesToThings(null, 1);
        Assert.assertNotNull(things);
    }

    @Test
    public void testTransformFullnamesToThingsExceedLimit() throws Exception{
        List<Thing> things = RedditAPIUtils.transformFullnamesToThings("t1_1", 200);
        Assert.assertEquals(100, things.size());
    }

    @Test
    public void testTransformFullnamesToThings() throws Exception {
        JSONArray childrenArray = new JSONArray();

        for(int i =0; i<=3; i++){
            JSONObject model = new JSONObject();
            JSONObject data = new JSONObject();

            model.put("kind","t1");
            data.put("id", i+1);
            data.put("name","t1"+ i+1);

            model.put("data", data);
            childrenArray.put(i,model);
        }

        List<Thing> things = new ArrayList<>();
        things.add(new Thing("t1_2"));
        things.add(new Thing("t1_3"));
        things.add(new Thing("t1_4"));

        Assert.assertThat(things, is( RedditAPIUtils.transformFullnamesToThings("t1_1", 3)));
    }
}
