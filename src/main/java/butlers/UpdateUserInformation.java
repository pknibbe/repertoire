package butlers;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import persistence.UserDAO;
import entity.User;

/**
 * Update a user in the database table
 * Created by peter on 2/27/2017.
 */
@WebServlet(
        name = "UpdateUserInformation",
        urlPatterns = { "/UpdateUserInformation" }
)
class UpdateUserInformation extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    private final Logger logger = Logger.getLogger(this.getClass());

    /**
         *  Handles HTTP POST requests.
         *
         *@param  request                   the HttpServletRequest object
         *@param  response                   the HttpServletResponse object
         *@exception ServletException  if there is a Servlet failure
         *@exception IOException       if there is an IO failure
         */
        public void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException, NullPointerException{
            HttpSession session = request.getSession();

            String url;

            try {
                User user = userDAO.read(Integer.valueOf(request.getParameter("id")));
                user.setRole_name(request.getParameter("Role"));
                user.setUser_pass(request.getParameter("NewPassword"));
                user.setName(request.getParameter("Name"));
                user.setUser_name(request.getParameter("UserName"));
                userDAO.update(user);

                url = "ShowUsers";

                response.sendRedirect(url);
            }catch (Exception e) {
                logger.error("Serious error caught. Logging the user out.", e);
                session.setAttribute("message", "Repertoire has encountered a serious error. Please contact the administrator for assistance.");
                response.sendRedirect("error.jsp");
            }
        }
    }
