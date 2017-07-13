package gr.personal.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

/**
 * Created by nkanakis on 7/13/2017.
 */
public class ConsumerUtil {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerUtil.class);

    /**
     * Transform mapping to URL key-value parameters (eg: a=1&b=2&c=3).
     *
     * @param keyValueParameters
     * @return Key-Valye pairs formatted for url parameters.
     */
    public static String transformParameters(Map<String, String> keyValueParameters) {

        Set<String> keys = keyValueParameters.keySet();
        String parameters="";

        for (String key : keys) {
            String value = keyValueParameters.get(key);
                try {
                    value = URLEncoder.encode(value, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    logger.warn("Value cannot be encoded to URL parameter", e);
                }
            // Add key-value pair
            parameters = parameters.concat(key + "=" + value);
        }

        return parameters;
    }
}
