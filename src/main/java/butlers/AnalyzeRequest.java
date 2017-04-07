package butlers;

import engines.UserManager;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Collection;

/**
 * Examine a request
 * Created by peter on 2/8/2017.
 */
@WebServlet(
        name = "AnalyzeRequest",
        urlPatterns = { "/AnalyzeRequest" }
) public class AnalyzeRequest extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass());

    /**
     *  Handles HTTP GET requests.
     *
     *@param  request                   the HttpServletRequest object
     *@param  response                   the HttpServletResponse object
     *@exception ServletException  if there is a Servlet failure
     *@exception IOException       if there is an IO failure
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ServletContext servletContext = getServletContext();
        RequestDispatcher dispatcher;
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            logger.info("Parameter " + parameterName + " found with value " + request.getParameter(parameterName));
        }
        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            logger.info("Attribute " + attributeName + " found");
        }
        String contextPath = request.getContextPath();
        Cookie[] cookies = request.getCookies();
        int numberOfCookies = cookies.length;
        logger.info("Found " + numberOfCookies + " cookies.");

        String query = request.getQueryString();
        String uri = request.getRequestURI();




        dispatcher = servletContext.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }
}