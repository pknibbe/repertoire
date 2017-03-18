package butlers;

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
        name = "LogOut",
        urlPatterns = { "/LogOut" }
)public class LogOut extends HttpServlet {

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
        session.setAttribute("user_id", "");
        session.setAttribute("user_role", "");
        session.setAttribute("message", "Logged Out");
        session.setAttribute("name", "");
        RequestDispatcher dispatcher;

        dispatcher = servletContext.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }
}