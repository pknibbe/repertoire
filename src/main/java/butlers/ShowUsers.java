package butlers;

import org.apache.log4j.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.*;
import persistence.UserDAO;
import persistence.GroupDAO;
import entity.User;

/**
 * Get the full list of users to populate the user management page
 * Created by peter on 2/8/2017.
 */
@WebServlet(
        name = "ShowUsers",
        urlPatterns = { "/ShowUsers" }
)
public class ShowUsers extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass());
    private final UserDAO userDAO = new UserDAO();
    private final GroupDAO groupDAO = new GroupDAO();

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

        RequestDispatcher dispatcher;
        HttpSession session = request.getSession();
        ServletContext servletContext = getServletContext();
        String role = "administrator";

        try {
            if (role.equalsIgnoreCase(((User) session.getAttribute("user")).getRole_name())) {

                request.setAttribute("users", userDAO.getAll());
                request.setAttribute("groups", groupDAO.getAll());

                dispatcher = servletContext.getRequestDispatcher("/accounts.jsp");
                logger.debug("Dispatching request forward to /accounts.jsp");

            } else {
                session.setAttribute("message", "Not authorized to view user information.");
                dispatcher = servletContext.getRequestDispatcher("ShowPlaylists");
                logger.debug("Dispatching request forward to ShowPlaylists");
            }

            logger.debug("Using dispatcher " + dispatcher.toString());
            dispatcher.forward(request, response);
        } catch (Exception e) {
            logger.error("Serious error caught. Logging the user out.", e);
            session.setAttribute("message", "Repertoire has encountered a serious error. Please contact the administrator for assistance.");
            Navigator.forward(request, response, servletContext, "error.jsp");
        }
    }
}


