package engines;
import java.io.*;
import java.util.*;
/**
 * Manages configuration information via properties
 * Created by peter on 3/27/2017.
 */
 class PropertyManager extends Properties {

     PropertyManager() {
        try {
            InputStream propertiesStream =this.getClass().getResourceAsStream("/repertoire.properties");
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

        StringBuilder contents = new StringBuilder("Properties File Contents:" + System.lineSeparator());
        contents.append("   Key            Value").append(System.lineSeparator());
        contents.append("-------------------------------------").append(System.lineSeparator());

        for (String key : keys) {
            String value = this.getProperty(key);
            contents.append(key).append("   ").append(value).append(System.lineSeparator());
        }
        return contents.toString();
    }
}
