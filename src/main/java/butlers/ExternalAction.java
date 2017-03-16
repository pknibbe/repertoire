package butlers;

import engines.UserManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Get the internal home page
 * Created by peter on 2/8/2017.
 */
@WebServlet(
        name = "ExternalAction",
        urlPatterns = { "/ExternalAction" }
)public class ExternalAction extends HttpServlet {

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
        ServletContext servletContext = getServletContext();
        RequestDispatcher dispatcher;
        int user = manager.VerifyCredentials(request.getParameter("user_name"), request.getParameter("user_pass"));
        if (user == 0) {
            servletContext.setAttribute("message", "User Credentials not verified");

            dispatcher = servletContext.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        }

        String role = manager.DetermineRole(user);
        String name = manager.getName(user);

        servletContext.setAttribute("user_id", user);
        servletContext.setAttribute("user_role", role);
        servletContext.setAttribute("message", " ");
        servletContext.setAttribute("name", name);

        String url = "/ShowPlayLists";

        dispatcher = servletContext.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}