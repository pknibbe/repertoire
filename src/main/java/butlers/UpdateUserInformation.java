package butlers;

//import org.apache.log4j.Logger;

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

    //private final Logger logger = Logger.getLogger(this.getClass());

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

            if (userDAO.authenticated((Integer) session.getAttribute("user_id"))) {

                    User user = userDAO.get(Integer.valueOf(request.getParameter("id")));
                    user.setRole_name(request.getParameter("Role"));
                    user.setUser_pass(request.getParameter("NewPassword"));
                    user.setName(request.getParameter("Name"));
                    user.setUser_name(request.getParameter("UserName"));
                    if (0 == userDAO.modify(user)) {
                        session.setAttribute("message", "user not updated due to system error");
                    }

                url = "ShowUsers";

            } else {
                session.setAttribute("message", "user not authenticated");
                url = "/index.jsp";
            }
            response.sendRedirect(url);
        }
    }
