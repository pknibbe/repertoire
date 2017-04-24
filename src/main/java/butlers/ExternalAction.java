package butlers;

import org.apache.log4j.Logger;
import persistence.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Get the internal home page
 * Created by peter on 2/8/2017.
 */
@WebServlet(
        name = "ExternalAction",
        urlPatterns = { "/ExternalAction" }
) public class ExternalAction extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass());
    private final UserDAO userDAO = new UserDAO();

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

        logger.debug("user_name is " + request.getParameter("userName"));
        logger.debug("user_pass is " + request.getParameter("password"));
        int user_id = userDAO.verifyCredentials(request.getParameter("userName"), request.getParameter("password"));
        if (user_id == 0) {
            session.setAttribute("message", "User Credentials not verified");

            dispatcher = servletContext.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        }

        String role = userDAO.determineRole(user_id);
        String name = userDAO.getName(user_id);

        session.setAttribute("user_id", user_id);
        session.setAttribute("user_role", role);
        session.setAttribute("message", "Welcome, " + name);
        session.setAttribute("name", name);
        session.setAttribute("listID", 0);
        session.setAttribute("isPlaying", false);

        String url = "/ShowPlaylists";

        dispatcher = servletContext.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}