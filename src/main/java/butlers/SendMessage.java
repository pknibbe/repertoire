package butlers;

import entity.Message;
import persistence.MessageDAO;
import persistence.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import entity.User;

//import org.apache.log4j.Logger;

/**
 * Update a user in the database table
 * Created by peter on 2/27/2017.
 */
@WebServlet(
        name = "SendMessage",
        urlPatterns = { "/SendMessage" }
) public class SendMessage extends HttpServlet {
    //private final Logger logger = Logger.getLogger(this.getClass());
    private final UserDAO userDAO = new UserDAO();
    private final MessageDAO messageDAO = new MessageDAO();

    /**
         *  Handles HTTP POST requests.
         *
         *@param  request                   the HttpServletRequest object
         *@param  response                   the HttpServletResponse object
         *@exception ServletException  if there is a Servlet failure
         *@exception IOException       if there is an IO failure
         */
        public void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            HttpSession session = request.getSession();

            int user_id = ((User) session.getAttribute("user")).getId();
            String url;

                int recipientId = userDAO.getIdByName(request.getParameter("to"));
                Message message = new Message(request.getParameter("subject"), userDAO.read(user_id), userDAO.read(recipientId), 0,
                        request.getParameter("content"));
                messageDAO.create(message);
                url = "ShowMessages";


            response.sendRedirect(url);
        }
    }
