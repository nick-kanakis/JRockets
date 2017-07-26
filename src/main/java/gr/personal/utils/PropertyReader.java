package gr.personal.utils;

import java.io.*;

import java.util.Properties;

/**
 * Created by Nick Kanakis on 20/7/2017.
 */
public class PropertyReader {

    public static String fetchValue(String file, String key){
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(file);
            prop.load(input);
            return prop.getProperty(key);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static String fetchValue(String key) {
        return fetchValue("src/main/resources/application.properties", key);
    }
}
