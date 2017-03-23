package butlers;

import engines.MessageManager;
import engines.UserManager;
import entity.Message;
import entity.User;
import entity.PresentableMessage;
import persistence.MessageDAO;
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
import java.util.ArrayList;

/**
 * Provides access to Messages to the session user
 * Created by peter on 3/4/2017.
 */
@WebServlet(
        name = "ShowMessages",
        urlPatterns = { "/ShowMessages" })
public class ShowMessages extends HttpServlet {

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
        MessageDAO dao = new MessageDAO();
        UserDAO userDAO = new UserDAO();
        Message plainMessage;
        MessageManager MessageManager = new MessageManager();

        HttpSession session = request.getSession();
        ServletContext servletContext = getServletContext();
        UserManager userManager = new UserManager();
        String url;
        int user_id = (Integer) session.getAttribute("user_id");

        if (userManager.authenticated(user_id)) {
            ArrayList<Integer> listIDs = MessageManager.getIDs(user_id);
            ArrayList<PresentableMessage> messages = new ArrayList<>();
            for (int index : listIDs) {
               plainMessage = dao.get(index);
               int senderID = plainMessage.getSender();
               User user = userDAO.get(senderID);
               String senderName;
               if (user != null) {
                   senderName = user.getName();
               } else {
                   senderName = "Unknown";
               }
               PresentableMessage presentableMessage =
                        new PresentableMessage(plainMessage, senderName);
               messages.add(presentableMessage);
            }
            session.setAttribute("messages", messages);
            url = "/messages.jsp";

        } else {
            session.setAttribute("message", "user not authenticated");
            url = "/index.jsp";
        }
        RequestDispatcher dispatcher = servletContext.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}
