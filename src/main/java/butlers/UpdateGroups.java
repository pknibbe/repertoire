package butlers;

import entity.Group;
import entity.User;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import persistence.UserDAO;
import persistence.GroupDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;


/**
 * Update or add a user in the database table
 * Created by Peter Knibbe on 2/8/17.
 */

@WebServlet(
        name = "UpdateGroups",
        urlPatterns = { "/UpdateGroups" }
)
public class UpdateGroups extends HttpServlet {
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
        String url = "LogOut";
        User user = (User) session.getAttribute("user");
        if (user == null) response.sendRedirect(url);

        if (role.equalsIgnoreCase(user.getRole_name()))
        {
            int identifier = user.getId();

            Enumeration<String> parameterNames = request.getParameterNames();

            try
            {
                while (parameterNames.hasMoreElements())
                {
                    String parameterName = parameterNames.nextElement();
                    logger.debug("Parameter " + parameterName + " is " + request.getParameter(parameterName));

                    if (parameterName.equalsIgnoreCase("Delete"))
                    {
                        if (groupDAO.remove(Integer.valueOf(request.getParameter("groupID")), ((User) session.getAttribute("user")).getId()))
                        {
                            session.setAttribute("message", "removed user");
                            logger.debug("removed user " + identifier);
                        }
                        url = "ShowUsers";
                    }
                    else if (parameterName.equalsIgnoreCase("Update"))
                    {
                        url = updateGroup(request, identifier, session);
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
                url = "showPlaylists.jsp";
            }

        logger.debug("sending redirect to " + url);
        response.sendRedirect(url);
    }

    /**
     * Updates the group table as specified in the request
     * @param request the request from the accounts jsp
     * @param identifier the user ID already retrieved from the request
     * @param session the HTTP session in which this request was sent
     * @return the url to which the session should go next
     */
    private String updateGroup(HttpServletRequest request, int identifier, HttpSession session) throws HibernateException
    {
        if (identifier != userDAO.getAdminId()) return "showPlaylists.jsp";

        int group_id = Integer.valueOf(request.getParameter("groupID"));
        if (group_id > 0) {
            logger.debug("Updating existing group with ID " + group_id);
            Group group = groupDAO.read(group_id);
            if (group == null) {
                session.setAttribute("message", "Unable to update group due to system error");
                return "/index.jsp";
            } else {
                group.setName(request.getParameter("Name"));
                group.setId(group_id);
                groupDAO.update(group);
                return "ShowUsers";
            }
        } else {
            logger.debug("Creating a new group returned " + groupDAO.create(new Group(request.getParameter("Name"))));
            session.setAttribute("Groups", groupDAO.getAll());
            return "ShowUsers";
        }
    }
}