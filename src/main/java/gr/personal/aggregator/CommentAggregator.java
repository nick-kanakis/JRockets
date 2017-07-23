package gr.personal.aggregator;

import gr.personal.consumer.ConsumerUtil;
import gr.personal.consumer.RedditConsumer;
import gr.personal.consumer.model.Thing;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Nick Kanakis on 22/7/2017.
 */
@Service
public class CommentAggregator {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentAggregator.class);

    @Autowired
    private RedditConsumer redditConsumer;

    public void reversedAggregate(String subreddit) {
        String lastEnqueuedId = "";
        String lastEnqueuedFullname = "";
        //TODO: replace loop with a Executor.
        for (int i = 0; i < 100; i++) {
            JSONArray newModels;
            if (lastEnqueuedId == "")
                newModels = redditConsumer.fetchInitialComment(subreddit);
            else
                newModels = redditConsumer.fetchReversedComments(subreddit);

            String newModelsLatestId = AggregatorUtil.extractLastId(newModels);

            /**
             * If there are no new comments the last processed id will be the same as the currently received last id.
             * In that case we do not want to enqueue again the models.
             */
            if(Long.parseLong(newModelsLatestId, 36)<= Long.parseLong(lastEnqueuedId, 36))
                continue;

            /**
             * Find the most recently processed model in the list of models, then slice the list
             * [index+1, models.lenght] and enqueue the result. This is done to avoid already processed
             * models being processed again.
             */
            for (int index = 0; index <= newModels.length() -1; index++) {
                JSONObject innerModel = newModels.getJSONObject(index);
                String currentId = innerModel.getJSONObject("data").getString("id");

                if(currentId == lastEnqueuedId){
                    lastEnqueuedId = currentId;
                    lastEnqueuedFullname = innerModel.getJSONObject("data").getString("name");
                    enqueue(AggregatorUtil.splitArray(newModels, index)[1]);
                    break;
                }
            }

            /**
             * There is a possibility that the last processed Id is not in the list of models that was received.
             * In that case there is a gap and we need to patch it with a backlog request.
             */
            String firstFullnameOfNewModels = AggregatorUtil.extractLastFullname(newModels);
            Thing firstThing = new Thing(AggregatorUtil.increaseByOne(firstFullnameOfNewModels));
            Thing lastThing = new Thing(AggregatorUtil.decreaseByOne(lastEnqueuedFullname));
            JSONArray backlogModels = redditConsumer.fetchByRange(firstThing, lastThing);
            enqueue(ConsumerUtil.concatArray(backlogModels,newModels));
        }

    }

    //TODO: Actually enqueue the result
    private void enqueue(JSONArray result) {
        for (String tmp : AggregatorUtil.extractFullnames(result)) {
            System.out.println(tmp);
        }
    }
}
