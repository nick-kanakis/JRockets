package gr.personal.aggregator;

import gr.personal.helper.OuputValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Kanakis on 23/7/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostAggregatorTest {
    @Autowired
    private PostAggregator postAggregator;

    @Test
    public void testAggregate() throws Exception {
        for (int i = 0; i < 500; i++) {
            postAggregator.forwardAggregate("all");
        }
        List<String> fullnames = OuputValidator.checkIncrementalIds("comments.txt", false);
        Assert.assertTrue( OuputValidator.checkValideFullnames(fullnames)<=0);
    }
}
