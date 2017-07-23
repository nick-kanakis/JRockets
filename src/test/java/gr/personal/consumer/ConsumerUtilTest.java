package gr.personal.consumer;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by nkanakis on 7/21/2017.
 */
@RunWith(SpringRunner.class)
public class ConsumerUtilTest {

    @Test
    public void testTransformParameters() throws Exception {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("a","1");
        parameters.put("b","2");

        String urlEndocedParameters = ConsumerUtil.transformParameters(parameters);
        Assert.assertEquals("a=1&b=2", urlEndocedParameters);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToTransformFullnames() throws Exception {
        ConsumerUtil.transformCommaSeparatedFullnames("asdasd", 3);

    }

    @Test
    public void testTransformFullnames() throws Exception {
        String commaSeparatedFullnames = ConsumerUtil.transformCommaSeparatedFullnames("t1_dkio3q5", 3);
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

        JSONArray children = ConsumerUtil.shortChildrenArray(mockObject);

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
}
