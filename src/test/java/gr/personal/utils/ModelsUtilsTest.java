package gr.personal.utils;

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
public class ModelsUtilsTest {

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
        String id = ModelsUtils.extractInitialFilename(mockObject);
        Assert.assertEquals("1",id);
    }

    @Test
    public void testExtractLastFullname() throws Exception {
        String id = ModelsUtils.extractLastFullname(mockObject);
        Assert.assertEquals("4",id);
    }

    @Test
    public void testExtractFullnames() throws Exception {
        List<String> results = ModelsUtils.extractFullnames(mockObject);
        List<String> expectedResult = new ArrayList<>();

        for (int i = 0; i <=3 ; i++) {
            expectedResult.add(String.valueOf(i+1));
        }
        Assert.assertEquals(expectedResult,results);
    }

    @Test
    public void testIncreaseByOne() throws Exception {
        String increasedByOne = ModelsUtils.increaseFullnameByOne("t1_dkijto7");
        Assert.assertEquals("t1_dkijto8", increasedByOne);
    }

    @Test
    public void testExtractFirstFullname() throws Exception {

        JSONArray childrenArray = new JSONArray();
        for(int i =0; i<=3; i++){
            JSONObject model = new JSONObject();
            JSONObject data = new JSONObject();

            model.put("kind","t1");
            data.put("name","t1_"+ (i+1));
            data.put("id",i+1);

            model.put("data", data);
            childrenArray.put(i,model);
        }

        String firstFullname = ModelsUtils.extractFirstFullname(childrenArray);
        Assert.assertEquals("t1_1", firstFullname);
    }

    @Test
    public void testExtractFirstId() throws Exception {
        JSONArray childrenArray = new JSONArray();
        for(int i =0; i<=3; i++){
            JSONObject model = new JSONObject();
            JSONObject data = new JSONObject();

            model.put("kind","t1");
            data.put("name","t1_"+ (i+1));
            data.put("id",i+1);

            model.put("data", data);
            childrenArray.put(i,model);
        }
        String firstId = ModelsUtils.extractFirstId(childrenArray);
        Assert.assertEquals("1", firstId);
    }

    @Test
    public void testDecreaseByOne() throws Exception {
        String decreasedByOne = ModelsUtils.decreaseFullnameByOne("t1_dkijto7");
        Assert.assertEquals("t1_dkijto6", decreasedByOne);
    }

}
