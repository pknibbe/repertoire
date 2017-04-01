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
import engines.UserManager;

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
    private final UserManager userManager = new UserManager();

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
        UserDAO userdao = new UserDAO();
        RequestDispatcher dispatcher;
        HttpSession session = request.getSession();
        ServletContext servletContext = getServletContext();
        String role = "administrator";
        if (userManager.authenticated((Integer) session.getAttribute("user_id"))) {
            if (role.equalsIgnoreCase((String) session.getAttribute("user_role"))) {

                request.setAttribute("users", userdao.getAll());

                dispatcher = servletContext.getRequestDispatcher("/accounts.jsp");
                logger.debug("Dispatching request forward to /accounts.jsp");

            } else {
                session.setAttribute("message", "Not authorized to view user information.");
                dispatcher = servletContext.getRequestDispatcher("ShowPlayLists");
                logger.debug("Dispatching request forward to ShowPlayLists");

            }

        } else {
            dispatcher = servletContext.getRequestDispatcher("/index.jsp");
            session.setAttribute("message", "Not authorized.");
            logger.debug("Dispatching request forward to index.jsp");
        }
        logger.debug("Using dispatcher " + dispatcher.toString());
        dispatcher.forward(request, response);    }
}


