package gr.personal.aggregator;

import gr.personal.helper.OuputValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by Nick Kanakis on 24/7/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentAggregatorTest {
    @Autowired
    private CommentAggregator commentAggregator;

    @Test
    public void testAggregate() throws Exception {
        for (int i = 0; i < 100; i++) {
            commentAggregator.forwardAggregate("all");
        }
        List<String> fullnames = OuputValidator.checkIncrementalIds("comments.txt", true);
        Assert.assertEquals(0, OuputValidator.checkValideFullnames(fullnames));
    }
}
