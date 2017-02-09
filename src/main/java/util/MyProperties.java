package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.*;

/**
 * Created by peter on 1/31/2017.
 */
public class MyProperties {
    private Properties properties;
    private final Logger logger = Logger.getLogger(this.getClass());

    /**
     * default constructor
     */
    public MyProperties() {
    }

    /**
     * constructor
     * @param propertiesFilePath path to properties file
     */
    public MyProperties(String propertiesFilePath) {
        properties = new Properties();
        //logger.info("Calling MyProperties with path " + propertiesFilePath);

        try {
            InputStream propertiesStream =this.getClass().getResourceAsStream(
                    propertiesFilePath);

            properties.load (this.getClass().getResourceAsStream("/database.properties"));
            if (propertiesStream == null) {
                logger.error("PWK:InputStream from " + propertiesFilePath + " is null. Exiting.");
                properties = null;
                return;
            }
            properties.load(propertiesStream);
        }
        catch(IOException ioe) {
            logger.error("PWK:Unable to load the properties file at "  + propertiesFilePath, ioe);
        }
        catch(Exception e) {
            logger.error("PWK:Exception: ", e);
            logger.info("PWK:Properties file path is " + propertiesFilePath);
        }

    }

    public Properties getProperties() {
        return properties;
    }

}
