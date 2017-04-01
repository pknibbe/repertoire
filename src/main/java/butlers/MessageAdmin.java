package butlers;

import engines.UserManager;
import entity.Message;
import persistence.MessageDAO;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * Update a user in the database table
 * Created by peter on 2/27/2017.
 */
@WebServlet(
        name = "MessageAdmin",
        urlPatterns = { "/MessageAdmin" }
) public class MessageAdmin extends HttpServlet {

    private final Logger logger = Logger.getLogger(this.getClass());
    private final MessageDAO messageDAO = new MessageDAO();
    private final UserManager userManager = new UserManager();
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

            Enumeration<String> parameterNames = request.getParameterNames();


            while (parameterNames.hasMoreElements()) {
                String pName = parameterNames.nextElement();
                logger.debug("Parameter name is " + pName);
                logger.debug("Parameter value is " + request.getParameter(pName));
            }

            String requestType = request.getParameter("options");
            String requester = request.getParameter("name");
            Message message = new Message("Request for login assistance",
                    0,
                    userManager.getAdminId(), 0,
                    requester + " needs help with: " + requestType);
            messageDAO.add(message);

            response.sendRedirect("/index.jsp");
        }
}
