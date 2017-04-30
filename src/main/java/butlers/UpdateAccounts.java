package butlers;

import entity.User;
import persistence.UserDAO;
import org.apache.log4j.Logger;
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
    private final UserDAO userDAO = new UserDAO();
    private String url;

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
        HttpSession session = request.getSession();
        String role = "administrator";

            if (role.equalsIgnoreCase(((User) session.getAttribute("user")).getRole_name())) {
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
                    logger.debug("Parameter " + parameterName + " is " + request.getParameter(parameterName));
                    if (parameterName.equalsIgnoreCase("Delete")) {
                        userDAO.delete(userDAO.read(identifier));

                        session.setAttribute("message", "removed user");

                        logger.debug("removed user " + identifier);
                        url = "ShowUsers";
                    } else if (parameterName.equalsIgnoreCase("Update")) {
                        String name = request.getParameter("Name");
                        String password = request.getParameter("Password");
                        String userName = request.getParameter("Username");
                        role = request.getParameter("Role");
                        if (identifier > 0) {
                            logger.debug("Updating existing user ID " + identifier);
                            User user = userDAO.read(identifier);
                            if (user == null) {
                                session.setAttribute("message", "Unable to update user due to system error");
                                url = "/index.jsp";
                            } else {
                                session.setAttribute("UserInfo", userDAO.read(identifier));
                                url = "ShowUsers";
                            }
                        } else {
                            int added = userDAO.create(new User(userName, name, password, role));
                            logger.debug("Creating a new user returned " + added);
                            url = "ShowUsers";
                        }
                    }
                }
            }
            else {
                session.setAttribute("message", "Not authorized to manage user accounts");
                url = "ShowPlaylists";
            }

        logger.debug("sending redirect to " + url);
        response.sendRedirect(url);
    }
}