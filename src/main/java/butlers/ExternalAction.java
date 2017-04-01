package butlers;

import engines.UserManager;
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

/**
 * Get the internal home page
 * Created by peter on 2/8/2017.
 */
@WebServlet(
        name = "ExternalAction",
        urlPatterns = { "/ExternalAction" }
) public class ExternalAction extends HttpServlet {
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
        UserManager manager = new UserManager();
        HttpSession session = request.getSession();
        ServletContext servletContext = getServletContext();
        RequestDispatcher dispatcher;
        logger.info("user_name is " + request.getParameter("user_name"));
        logger.info("user_pass is " + request.getParameter("user_pass"));
        int user_id = manager.VerifyCredentials(request.getParameter("user_name"), request.getParameter("user_pass"));
        if (user_id == 0) {
            session.setAttribute("message", "User Credentials not verified");

            dispatcher = servletContext.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        }

        String role = manager.DetermineRole(user_id);
        String name = manager.getName(user_id);

        session.setAttribute("user_id", user_id);
        session.setAttribute("user_role", role);
        session.setAttribute("message", "Welcome, " + name);
        session.setAttribute("name", name);

        String url = "/ShowPlayLists";

        dispatcher = servletContext.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}