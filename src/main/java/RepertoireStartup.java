import java.io.*;
import java.util.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import org.apache.log4j.*;


/**
 * Start a Web Application
 *
 * @author Peter Knibbe
 * @since 12/1/16.
 */
@WebServlet(
        name = "RepertoireStartup",
        urlPatterns = { "/JavaSrc/ApplicationStartup" },
        loadOnStartup = 1
)


public class RepertoireStartup extends HttpServlet{
    private final Logger logger = Logger.getLogger(this.getClass());
    private Properties properties = null;

    /**
     *  Initialize Employee Web Application with objects and properties files
     */
    public void init() {
        String propertiesPath = "resources/repertoire.properties";
        MyProperties myProperties = new MyProperties(propertiesPath);
        if (myProperties != null) properties = myProperties.getProperties();
        if (properties == null) {
            logger.error("RepertoireStartup.LoadProperties has failed. Exiting.");
            return;
        }
        ServletContext servletContext = getServletContext();
        servletContext.setAttribute("Properties", properties);
        servletContext.setAttribute("sessionMessage", "");


        Database database = new Database(properties);
        servletContext.setAttribute("database", database);
     }


}