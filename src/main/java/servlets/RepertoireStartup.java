package servlets;

import java.util.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import util.Database;
import util.MyProperties;
import org.apache.log4j.*;


/**
 * Start a Web Application
 *
 * @author Peter Knibbe
 * @since 12/1/16.
 */
@WebServlet(
        name = "servlets.RepertoireStartup",
        urlPatterns = { "/JavaSrc/ApplicationStartup" },
        loadOnStartup = 1
)


public class RepertoireStartup extends HttpServlet{
    private final Logger logger = Logger.getLogger(this.getClass());
    private Properties properties = null;

    /**
     *  Initialize Web Application with objects and properties files
     */
    public void init() {
        String propertiesPath = "/repertoire.properties";
        MyProperties myProperties = new MyProperties(propertiesPath);
        if (myProperties != null) properties = myProperties.getProperties();
        if (properties == null) {
            logger.error("servlets.RepertoireStartup.LoadProperties has failed. Exiting.");
            return;
        }
        logger.info("properties obtained in startup");
        ServletContext servletContext = getServletContext();
        servletContext.setAttribute("Properties", properties);
        servletContext.setAttribute("sessionMessage", "");
        logger.info("servlet attributes set in startup");
        Database database = new Database(properties);
        servletContext.setAttribute("database", database);
        logger.info("database attribute set in startup");
    }

}