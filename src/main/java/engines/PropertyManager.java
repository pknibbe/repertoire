package engines;
import java.io.*;
import java.util.*;
/**
 * Manages configuration information via properties
 * Created by peter on 3/27/2017.
 */
public class PropertyManager extends Properties {
    /**
     * constructor with input parameter to identify configuration file
     *
     * @param filePath name of configuration file containing the keys and values
     */
    public PropertyManager(String filePath) {
        try {
            InputStream propertiesStream =this.getClass().getResourceAsStream(
                    filePath);
            this.load(propertiesStream);
        }
        catch(IOException ioe) {
            System.out.println("Unable to load the properties file");
            ioe.printStackTrace();
        }
        catch(Exception e) {
            System.out.println("Exception: " + e);
            e.printStackTrace();
        }
    }

    /**
     * prints AnalyzerProperties to standard out
     */
    public String toString() {
        Set<String> keys = this.stringPropertyNames();

        String contents = "Properties File Contents:" + System.lineSeparator();
        contents += "   Key            Value" + System.lineSeparator();
        contents += "-------------------------------------" + System.lineSeparator();

        for (String key : keys) {
            String value = this.getProperty(key);
            contents += key + "   " + value + System.lineSeparator();
        }
        return contents;
    }
}
