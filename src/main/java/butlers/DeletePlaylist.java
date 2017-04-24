package butlers;

import org.apache.log4j.Logger;
import persistence.PlaylistDAO;
import persistence.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Remove a playlist from the database
 * Created by Peter Knibbe on 2/8/17.
 */

@WebServlet(
        name = "DeletePlaylist",
        urlPatterns = { "/DeletePlaylist" }
)
public class DeletePlaylist extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass());
    private final PlaylistDAO playlistDAO = new PlaylistDAO();
    private final UserDAO userDAO = new UserDAO();

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
        String url;

        if (userDAO.authenticated((Integer) session.getAttribute("user_id"))) {
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String parameterName = parameterNames.nextElement();
                logger.debug("Parameter " + parameterName + " is " + request.getParameter(parameterName));
                if (parameterName.equalsIgnoreCase("Delete")) {
                    int result = playlistDAO.remove((Integer) session.getAttribute("listID"));
                    if (result == (Integer) session.getAttribute("listID")) {
                        session.setAttribute("message", "List Deleted");
                    } else {
                        session.setAttribute("message", "List Not Deleted - Is it shared with anyone?");
                    }
                    logger.debug("removed playlist " + session.getAttribute("listID"));
                } else if (parameterName.equalsIgnoreCase("Cancel")) {
                    logger.debug("Did not remove playlist " + session.getAttribute("listID"));
                    session.setAttribute("message", "List Not Deleted");
                }
            }
            url = "/ShowPlaylists";

            logger.debug("sending redirect to " + url);
            response.sendRedirect(url);
        } else { // bounce
            session.setAttribute("message", "user not authenticated");
            url = "/index.jsp";
            response.sendRedirect(url);
        }
    }
}