package butlers;

import entity.Message;
import entity.User;
import persistence.MessageDAO;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import persistence.UserDAO;

import org.apache.log4j.Logger;

/**
 * Send a message to the administrator asking for login assistance
 * Created by peter on 2/27/2017.
 */
@WebServlet(
        name = "MessageAdmin",
        urlPatterns = { "/MessageAdmin" }
) public class MessageAdmin extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass());
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
            Enumeration<String> parameterNames = request.getParameterNames();


            while (parameterNames.hasMoreElements()) {
                String pName = parameterNames.nextElement();
                logger.debug("Parameter name is " + pName);
                logger.debug("Parameter value is " + request.getParameter(pName));
            }

            try {
                String requestType = request.getParameter("options");
                String requester = request.getParameter("name");
                Message message = new Message("Request for login assistance",
                        new User(requester),
                        userDAO.read(userDAO.getAdminId()), 0,
                        requester + " needs help with: " + requestType);
                messageDAO.create(message);

                response.sendRedirect("/index.jsp");
            } catch (Exception e) {
                logger.error("Serious error caught. Logging the user out.", e);
                session.setAttribute("message", "Repertoire has encountered a serious error. Please contact the administrator for assistance.");
                response.sendRedirect("error.jsp");
            }
        }
}
