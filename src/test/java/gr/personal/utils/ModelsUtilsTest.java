package gr.personal.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;

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
            data.put("id",i+1);

            model.put("data", data);
            childrenArray.put(i,model);
        }
        mockObject =  childrenArray;
    }

    @Test
    public void testExtractFullname() throws Exception {
        Assert.assertEquals("3", ModelsUtils.extractFullname(mockObject, 2));
    }
    @Test
    public void testExtractFullnameWrongIndex() throws Exception {
        Assert.assertEquals("", ModelsUtils.extractFullname(mockObject, -1));
    }
    @Test
    public void testExtractFullnameNullChildren() throws Exception {
        Assert.assertEquals("", ModelsUtils.extractFullname(null, 2));
    }


    @Test
    public void testExtractInitialFullname() throws Exception {
        String id = ModelsUtils.extractInitialFullname(mockObject);
        Assert.assertEquals("1",id);
    }
    @Test
    public void testExtractInitialFullnameNullInput() throws Exception {
        String id = ModelsUtils.extractInitialFullname(null);
        Assert.assertEquals("",id);
    }

    @Test
    public void testExtractLastFullname() throws Exception {
        String id = ModelsUtils.extractLastFullname(mockObject);
        Assert.assertEquals("4",id);
    }

    @Test
    public void testExtractLastFullnameNullInput() throws Exception {
        String id = ModelsUtils.extractLastFullname(null);
        Assert.assertEquals("",id);
    }

    @Test
    public void testExtractLastId() throws Exception {
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
        Assert.assertEquals("4", ModelsUtils.extractLastId(childrenArray));
    }

    @Test
    public void testExtractLastIdNullInput() throws Exception {
        Assert.assertEquals("", ModelsUtils.extractLastId(null));
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
    public void testExtractIds() throws Exception {
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
        List<String> ids = new ArrayList<>();
        ids.add("1");
        ids.add("2");
        ids.add("3");
        ids.add("4");

        Assert.assertThat(ids, is( ModelsUtils.extractIds(childrenArray)));
    }

    @Test
    public void testExtractIdsNullInput() throws Exception {

        Assert.assertNotNull(ModelsUtils.extractIds(null));
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
