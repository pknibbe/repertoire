import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import Utilities.Database;
import org.apache.log4j.*;

/**
 * Add a user to the database table
 * Created by Peter Knibbe on 12/12/16.
 */

@WebServlet(
        name = "UserAddAction",
        urlPatterns = { "/UserAddAction" }
)
public class UserAddActionServlet extends HttpServlet{
    final Logger logger = Logger.getLogger(this.getClass());

    /**
     *  Handles HTTP GET requests.
     *
     *@param  request                   the HttpServletRequest object
     *@param  response                   the HttpServletResponse object
     *@exception  ServletException  if there is a Servlet failure
     *@exception  IOException       if there is an IO failure
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //final Logger logger = Logger.getLogger(this.getClass());
        String message;

        ServletContext servletContext = getServletContext();
        Database database = (Database) servletContext.getAttribute("database");
        logger.info("database is " + database + ".");
        logger.info("request is " + request + ".");

        int rowsAffected = database.update("INSERT INTO users (username, privileges) VALUES (" +
                           request.getParameter("username") + ", " +
                           request.getParameter("privileges") + ");");

        servletContext.setAttribute("rowsAffected", rowsAffected);
        if (rowsAffected > 0) {
            message = "user successfully added";
        }
        else {
            message = "user not added";
        }
        servletContext.setAttribute("sessionMessage", message);

        String url = "/addEmployee.jsp";

        response.sendRedirect(url);
    }
}