package butlers;

import engines.RoleAndUserManager;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.*;
import java.util.Enumeration;


/**
 * Update or add a user in the database table
 * Created by Peter Knibbe on 2/8/17.
 */

@WebServlet(
        name = "UpdateAccounts",
        urlPatterns = { "/UpdateAccounts" }
)
public class UpdateAccounts extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass());
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

        String userID = request.getParameter("userID");
        int identifier;
        if (userID != null) { // protect against empty input
            identifier = Integer.valueOf(userID);
        } else {
            identifier = 0;
        }

        Enumeration<String> parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            if (parameterName.equalsIgnoreCase("Delete")) {
                roleAndUserManager.removeUserWithRole(identifier);
            } else if (parameterName.equalsIgnoreCase("Update")) {
                    String name = request.getParameter("Name");
                    String password = request.getParameter("Password");
                    String userName = request.getParameter("Username");
                    String role = request.getParameter("Role");
                    if (identifier > 0) {
                        HttpSession session = request.getSession();
                        session.setAttribute("UserInfo", roleAndUserManager.getUser(identifier));
                        String url = "restricted/UpdateUser.jsp";
                        response.sendRedirect(url);
                        return;
                    } else {
                        roleAndUserManager.addUserWithRole(userName, name, password, role);
                    }
                }
        }


        String url = "ShowUsers";

        response.sendRedirect(url);
    }
}