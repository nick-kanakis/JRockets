package gr.personal.consumer;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by nkanakis on 7/20/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedditConsumerTest {

    @Autowired
    private RedditConsumer redditConsumer;

    @Test
    public void testFetchInitialPost() throws Exception {
        JSONObject data = redditConsumer.fetchInitialPost("all");
        Assert.assertTrue(data.has("children"));
        JSONArray children = data.getJSONArray("children");
        Assert.assertFalse(children.isNull(0));
        JSONObject innerData =  children.getJSONObject(0).getJSONObject("data");
        Assert.assertNotNull(innerData.get("id"));
    }

    @Test
    public void testFetchInitialComment() throws Exception {
        JSONObject data =  redditConsumer.fetchInitialComment("all");
        Assert.assertTrue(data.has("children"));
        JSONArray children = data.getJSONArray("children");
        Assert.assertFalse(children.isNull(0));
        JSONObject innerData =  children.getJSONObject(0).getJSONObject("data");
        Assert.assertNotNull(innerData.get("id"));

    }

    @Test
    public void testFetchReversedPosts() throws Exception {
        JSONObject data =  redditConsumer.fetchReversedPosts("all");
        Assert.assertTrue(data.has("children"));
        JSONArray children = data.getJSONArray("children");
        Assert.assertFalse(children.isNull(99));
        JSONObject innerData =  children.getJSONObject(99).getJSONObject("data");
        Assert.assertNotNull(innerData.get("id"));
    }

    @Test
    public void testTetchReversedComments() throws Exception {
        JSONObject data =  redditConsumer.fetchReversedComments("all");
        Assert.assertTrue(data.has("children"));
        JSONArray children = data.getJSONArray("children");
        Assert.assertFalse(children.isNull(99));
        JSONObject innerData =  children.getJSONObject(99).getJSONObject("data");
        Assert.assertNotNull(innerData.get("id"));
    }

    @Test
    public void testFetchFullnames() throws Exception {
        JSONObject data =  redditConsumer.fetchFullnames("t1_dkijto7", 2);
        Assert.assertTrue(data.has("children"));
        JSONArray children = data.getJSONArray("children");

        Assert.assertFalse(children.isNull(0));
        JSONObject innerData =  children.getJSONObject(0).getJSONObject("data");
        Assert.assertEquals("dkijto8", innerData.get("id"));

        Assert.assertFalse(children.isNull(1));
        JSONObject innerData2 =  children.getJSONObject(1).getJSONObject("data");
        Assert.assertEquals("dkijto9", innerData2.get("id"));

    }

    @Test
    public void testFetchForward() throws Exception {
        JSONObject data =  redditConsumer.fetchForward("t1_dkijto7");
        Assert.assertTrue(data.has("children"));
        JSONArray children = data.getJSONArray("children");
        Assert.assertFalse(children.isNull(99));

        Assert.assertFalse(children.isNull(0));
        JSONObject innerData =  children.getJSONObject(0).getJSONObject("data");
        Assert.assertEquals("dkijto8", innerData.get("id"));

        Assert.assertFalse(children.isNull(1));
        JSONObject innerData2 =  children.getJSONObject(1).getJSONObject("data");
        Assert.assertEquals("dkijto9", innerData2.get("id"));

    }
}
