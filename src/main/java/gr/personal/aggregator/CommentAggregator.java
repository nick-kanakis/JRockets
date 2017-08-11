package gr.personal.aggregator;

import gr.personal.consumer.RedditConsumer;
import gr.personal.queue.QueueService;
import gr.personal.utils.ModelsUtils;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Nick Kanakis on 22/7/2017.
 */
@Component("CommentAggregator")
public class CommentAggregator implements Aggregator {

    @Autowired
    private Logger logger;

    @Autowired
    private RedditConsumer redditConsumer;
    @Autowired
    private QueueService queueService;
    private static String lastFullname = null;

    public void forwardAggregate(String subreddit) {
        JSONArray result;
        if (lastFullname == null)
            result = redditConsumer.fetchInitialComment(subreddit);
        else
            result = redditConsumer.fetchForward(lastFullname);

        String tmpLastFullname = ModelsUtils.extractLastFullname(result);

        if (tmpLastFullname == null || tmpLastFullname == "")
            return;

        lastFullname = tmpLastFullname;
        for (int i = 0; i<= result.length() - 1 ; i++) {
            queueService.enqueueComment(result.getJSONObject(i));
        }
    }

    public void reversedAggregate(String subreddit) {
        throw new RuntimeException("ReversedAggregate in not yet supported");
    }

//TODO: fix reversed Aggregator if possible
//    public void reversedAggregate(String subreddit) {
//
//        JSONArray newModels;
//
//        if (lastEnqueuedId == "") {
//            newModels = redditConsumer.fetchInitialComment(subreddit);
//            lastEnqueuedId = ModelsUtils.extractFirstId(newModels);
//            lastEnqueuedFullname = ModelsUtils.extractFirstFullname(newModels);
//            enqueue(newModels);
//            return;
//        }
//
//        newModels = redditConsumer.fetchReversedComments(subreddit);
//        String newModelsLatestId = ModelsUtils.extractLastId(newModels);
//
//        /**
//         * If there are no new comments the last processed id will be the same as the currently received last id.
//         * In that case we do not want to enqueue again the models.
//         */
//        long currentLatestIdDec = Long.parseLong(newModelsLatestId, 36);
//        long lastEnquedIdDec = Long.parseLong(lastEnqueuedId, 36);
//        if ( currentLatestIdDec <= lastEnquedIdDec) {
//            logger.debug("No new models, currentLatestId:{}, lastEnqueueId", currentLatestIdDec, lastEnquedIdDec);
//            return;
//        }
//
//        /**
//         * Find the most recently processed model in the list of models, then slice the list
//         * [index+1, models.lenght] and enqueue the result. This is done to avoid already processed
//         * models being processed again.
//         */
//        for (int index = 0; index <= newModels.length() - 1; index++) {
//            JSONObject innerModel = newModels.getJSONObject(index);
//            String currentId = innerModel.getJSONObject("data").getString("id");
//
//            if (currentId == lastEnqueuedId) {
//                JSONArray unProcessedModels = ModelsUtils.splitArray(newModels, index)[1];
//                lastEnqueuedId = ModelsUtils.extractLastId(unProcessedModels);
//                lastEnqueuedFullname = ModelsUtils.extractLastFullname(unProcessedModels);
//                enqueue(unProcessedModels);
//                break;
//            }
//        }
//
//        /**
//         * There is a possibility that the last processed Id is not in the list of models that was received.
//         * In that case there is a gap and we need to patch it with a backlog request.
//         */
//        String firstFullnameOfNewModels = ModelsUtils.extractFirstFullname(newModels);
//        Thing start = new Thing(ModelsUtils.increaseFullnameByOne(lastEnqueuedFullname));
//        Thing end = new Thing(ModelsUtils.decreaseFullnameByOne(firstFullnameOfNewModels));
//        JSONArray backlogModels = redditConsumer.fetchByRange(start, end);
//        enqueue(RedditAPIUtils.concatArray(backlogModels, newModels));
//    }
}
