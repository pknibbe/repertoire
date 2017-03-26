package butlers;

import engines.UserManager;
//import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Update a user in the database table
 * Created by peter on 2/27/2017.
 */
@WebServlet(
        name = "UpdateUserInformation",
        urlPatterns = { "/UpdateUserInformation" }
)
public class UpdateUserInformation extends HttpServlet {

    //private final Logger logger = Logger.getLogger(this.getClass());
    private final UserManager userManager = new UserManager();
    String url;

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

            if (userManager.authenticated((Integer) session.getAttribute("user_id"))) {
                if (0 ==
                userManager.updateUserWithRole(
                        Integer.valueOf(request.getParameter("id")),
                        request.getParameter("UserName"),
                        request.getParameter("Name"),
                        request.getParameter("NewPassword"),
                        request.getParameter("Role"))) {
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
