package gr.personal.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Nick Kanakis on 20/7/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PropertyReaderTest {

    @Test
    public void testFetchValue() throws Exception {
        String value = PropertyReader.fetchValue("app.useragent");
        Assert.assertNotNull("property should not be null", value);
    }
}
