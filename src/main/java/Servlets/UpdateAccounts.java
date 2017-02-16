package Servlets;

import org.apache.log4j.Logger;
import persistence.UserDAO;
import entity.User;
import util.Database;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.*;
import java.util.Enumeration;


/**
 * Update or add a user in the database table
 * Created by Peter Knibbe on 2/8/17.
 */

@WebServlet(
        name = "UpdateAccounts",
        urlPatterns = { "/UpdateAccounts" }
)
public class UpdateAccounts extends HttpServlet {
    final Logger logger = Logger.getLogger(this.getClass());

    /**
     *  Handles HTTP GET requests.
     *
     *@param  request                   the HttpServletRequest object
     *@param  response                   the HttpServletResponse object
     *@exception ServletException  if there is a Servlet failure
     *@exception IOException       if there is an IO failure
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String message;
        UserDAO userDAO = new UserDAO();

        ServletContext servletContext = getServletContext();
        String userID = request.getParameter("userID");
        int identifier = Integer.valueOf(userID);
        Enumeration<String> parameterNames = request.getParameterNames();
        User user;

        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            if (parameterName.equalsIgnoreCase("Delete")) {
                userDAO.remove(identifier);
                logger.info("removed user " + identifier);
            } else if (parameterName.equalsIgnoreCase("Update")) {
                String name = request.getParameter("Name");
                String password = request.getParameter("Password");
                if (identifier > 0) {
                    logger.info("Updating existing user ID " + identifier);
                    user = userDAO.get(identifier);
                    if (name.length() > 0) {
                        user.setUsername(name);
                        user.setName(name);
                        logger.info(user.toString());
                        logger.info("Updated user ID = " + userDAO.modify(user));
                    }
                } else {
                    logger.info("Creating a new user");
                    user = new User(name, name, password);
                    logger.info(user.toString());
                    logger.info("Added user ID = " + userDAO.add(user));
                }
            }
        }

        String url = "/ShowUsers";

        response.sendRedirect(url);
    }
}