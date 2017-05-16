package butlers;

import org.apache.log4j.Logger;
import persistence.MessageDAO;
import entity.Message;
import entity.User;
import persistence.UserDAO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Provides access to Messages to the session user
 * Created by peter on 3/4/2017.
 */
@WebServlet(
        name = "ShowMessages",
        urlPatterns = { "/ShowMessages" })
public class ShowMessages extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();
    private final MessageDAO messageDAO = new MessageDAO();
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

        HttpSession session = request.getSession();
        ServletContext servletContext = getServletContext();

        User user = (User) session.getAttribute("user");
        try {
            List<Message> messages = messageDAO.getAllToMe(user.getId());

            session.setAttribute("messages", messages);
            session.setAttribute("names", userDAO.getOtherUserNames(user.getId()));
            Navigator.forward(request, response, servletContext, "/messages.jsp");

        } catch (Exception e) {
            logger.error("Serious error caught. Logging the user out.", e);
            session.setAttribute("message", "Repertoire has encountered a serious error. Please contact the administrator for assistance.");
            Navigator.forward(request, response, servletContext, "error.jsp");
        }
    }
}
