package butlers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import persistence.UserDAO;
import persistence.SharedDAO;

import org.apache.log4j.Logger;

/**
 * Update a user in the database table
 * Created by peter on 2/27/2017.
 */
@WebServlet(
        name = "SharePlaylist",
        urlPatterns = { "/SharePlaylist" }
)
public class SharePlaylist extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass());
    private final UserDAO userDAO = new UserDAO();
    private final SharedDAO sharedDAO = new SharedDAO();

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

            int user_id = (Integer) session.getAttribute("user_id");
            int playlist_id = (Integer) session.getAttribute("listID");
            String url;
            if (userDAO.authenticated(user_id)) {
                if (request.getParameter("Cancel") != null) {
                    url = "ShowAPlaylist";
                } else {
                    Enumeration<String> parameterNames = request.getParameterNames();

                    while (parameterNames.hasMoreElements()) {
                        String parameterName = parameterNames.nextElement();
                        logger.debug("Parameter " + parameterName + " is " + request.getParameter(parameterName));
                        if (request.getParameter("Share") != null) {
                            logger.debug("In share section");
                            if (request.getParameter(parameterName).equalsIgnoreCase("on")) { // this is id
                                Integer index = Integer.valueOf(parameterName);
                                sharedDAO.share(playlist_id, index);
                            }
                        } else if (request.getParameter("UnShare") != null) {
                            logger.debug("In un-share section");
                            if (request.getParameter(parameterName).equalsIgnoreCase("on")) { // this is id
                                Integer index = Integer.valueOf(parameterName);
                                sharedDAO.remove(index, playlist_id);
                            }
                        }
                    }
                    url = "/sharePlaylist.jsp";
                }
            } else {
                session.setAttribute("message", "user not authenticated");
                url = "/index.jsp";
            }
            response.sendRedirect(url);
        }
    }
