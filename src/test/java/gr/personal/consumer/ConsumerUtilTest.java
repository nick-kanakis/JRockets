package gr.personal.consumer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

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
}
