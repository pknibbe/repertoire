package butlers;

import engines.RoleAndUserManager;
import org.apache.log4j.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    private final Logger logger = Logger.getLogger(this.getClass());
    private final RoleAndUserManager roleAndUserManager = new RoleAndUserManager();
    /**
     *  Handles HTTP POST requests.
     *
     *@param  request                   the HttpServletRequest object
     *@param  response                   the HttpServletResponse object
     *@exception ServletException  if there is a Servlet failure
     *@exception IOException       if there is an IO failure
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        String role = "administrator";
        if (role.equalsIgnoreCase((String) servletContext.getAttribute("user_role"))) {
            String userID = request.getParameter("userID");
            int identifier;
            if (userID != null) { // protect against empty input
                identifier = Integer.valueOf(userID);
            } else {
                identifier = 0;
            }

            Enumeration<String> parameterNames = request.getParameterNames();

            while (parameterNames.hasMoreElements()) {
                String parameterName = parameterNames.nextElement();
                logger.info("Parameter " + parameterName + " is " + request.getParameter(parameterName));
                if (parameterName.equalsIgnoreCase("Delete")) {
                    roleAndUserManager.removeUserWithRole(identifier);
                    logger.info("removed user " + identifier);
                } else if (parameterName.equalsIgnoreCase("Update")) {
                        String name = request.getParameter("Name");
                        String password = request.getParameter("Password");
                        String userName = request.getParameter("Username");
                        role = request.getParameter("Role");
                        if (identifier > 0) {
                            logger.info("Updating existing user ID " + identifier);
                            HttpSession session = request.getSession();
                            session.setAttribute("UserInfo", roleAndUserManager.getUser(identifier));
                            String url = "updateUser.jsp";
                            response.sendRedirect(url);
                            return;
                        } else {
                            int added = roleAndUserManager.addUserWithRole(userName, name, password, role);
                            logger.info("Creating a new user returned " + added);
                        }
                    }
            }

        }
        String url = "ShowUsers";

        logger.info("sending redirect to " + url);
        response.sendRedirect(url);
    }
}