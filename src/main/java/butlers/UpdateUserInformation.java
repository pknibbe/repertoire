package butlers;

import engines.RoleAndUserManager;
import engines.Authentication;
//import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        private final RoleAndUserManager roleAndUserManager = new RoleAndUserManager();
        /**
         *  Handles HTTP GET requests.
         *
         *@param  request                   the HttpServletRequest object
         *@param  response                   the HttpServletResponse object
         *@exception ServletException  if there is a Servlet failure
         *@exception IOException       if there is an IO failure
         */
        public void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            ServletContext servletContext = getServletContext();
            Authentication authentication = new Authentication();
            String url;

            if (authentication.authenticated(servletContext)) {
                roleAndUserManager.updateUserWithRole(
                        Integer.valueOf(request.getParameter("id")),
                        request.getParameter("UserName"),
                        request.getParameter("Name"),
                        request.getParameter("NewPassword"),
                        request.getParameter("Role"));

                url = "ShowUsers";

            } else {
                servletContext.setAttribute("message", "user not authenticated");
                url = "index.jsp";
            }
            response.sendRedirect(url);
        }
    }
