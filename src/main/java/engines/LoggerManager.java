package engines;
import org.apache.log4j.Logger;

/**
 * Provides access to a logger
 * Created by peter on 4/4/2017.
 */
public class LoggerManager {
    final private Logger logger = Logger.getLogger(this.getClass());

    public Logger get() { return logger;}
}
