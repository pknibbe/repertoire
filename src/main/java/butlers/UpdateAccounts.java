package butlers;

import entity.User;
import entity.Group;
import org.hibernate.HibernateException;
import persistence.UserDAO;
import persistence.GroupDAO;
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
    private final GroupDAO groupDAO = new GroupDAO();

    /**
     *  Handles HTTP POST requests.
     *
     *@param  request                   the HttpServletRequest object
     *@param  response                   the HttpServletResponse object
     *@exception ServletException  if there is a Servlet failure
     *@exception IOException       if there is an IO failure
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, HibernateException
    {
        HttpSession session = request.getSession();
        String role = "administrator";
        String url = null;

        if (role.equalsIgnoreCase(((User) session.getAttribute("user")).getRole_name()))
        {
            int identifier = getIdentifier(request.getParameter("userID"));

            Enumeration<String> parameterNames = request.getParameterNames();

            try
            {
                while (parameterNames.hasMoreElements())
                {
                    String parameterName = parameterNames.nextElement();
                    logger.debug("Parameter " + parameterName + " is " + request.getParameter(parameterName));

                    if (parameterName.equalsIgnoreCase("Delete"))
                    {
                        if (userDAO.remove(identifier))
                        {
                            session.setAttribute("message", "removed user");
                            logger.debug("removed user " + identifier);
                        }
                        url = "ShowUsers";
                    }
                    else if (parameterName.equalsIgnoreCase("Update"))
                    {
                        url = updateUser(request, identifier, session);
                    }
                }
            }catch (Exception e) {
                    logger.error("Serious error caught. Logging the user out.", e);
                    session.setAttribute("message", "Repertoire has encountered a serious error. Please contact the administrator for assistance.");
                    response.sendRedirect("error.jsp");
                }
            }
            else {
                session.setAttribute("message", "Not authorized to manage user accounts");
                url = "ShowPlaylists";
            }

        logger.debug("sending redirect to " + url);
        response.sendRedirect(url);
    }

    /**
     * Retrieves the userID integer value from the request parameter
     * @param userID The value of the request parameter
     * @return the userID as an integer
     */
    private int getIdentifier(String userID)
    {
        if (userID != null) { // protect against empty input
            return Integer.valueOf(userID);
        } else {
            return 0;
        }
    }

    /**
     * Updates the user table as specified in the request
     * @param request the request from the accounts jsp
     * @param identifier the ID already retrieved from the request
     * @param session the HTTP session in which this request was sent
     * @return the url to which the session should go next
     */
    private String updateUser(HttpServletRequest request, int identifier, HttpSession session) throws HibernateException
    {
        String name = request.getParameter("Name");
        String password = request.getParameter("Password");
        String userName = request.getParameter("Username");
        Group group = groupDAO.getGroupByName(request.getParameter("Group"));

        if (identifier > 0) {
            logger.debug("Updating existing user ID " + identifier);
            User user = userDAO.read(identifier);
            if (user == null) {
                session.setAttribute("message", "Unable to update user due to system error");
                return "/index.jsp";
            } else {
                user = new User(userName, name, password, "registered-user", group);
                user.setId(identifier);
                userDAO.update(user);
                session.setAttribute("Users", userDAO.getAll());
                return "ShowUsers";
            }
        } else {
            int added = userDAO.create(new User(userName, name, password, "registered-user", group));
            logger.debug("Creating a new user returned " + added);
            return "ShowUsers";
        }
    }
}