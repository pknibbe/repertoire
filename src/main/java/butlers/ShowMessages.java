package butlers;

import persistence.MessageDAO;
import entity.Message;
import entity.User;
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
        Message plainMessage;

        HttpSession session = request.getSession();
        ServletContext servletContext = getServletContext();
        String url;
        User user = (User) session.getAttribute("user");

            List<Message> messages = messageDAO.getAll(user.getId());

            session.setAttribute("messages", messages);
            session.setAttribute("names", userDAO.getOtherUserNames(user.getId()));
            url = "/messages.jsp";

        RequestDispatcher dispatcher = servletContext.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}
