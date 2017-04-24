package butlers;

import org.apache.log4j.Logger;
import persistence.UserDAO;
import persistence.SongDAO;
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
        name = "DeleteSong",
        urlPatterns = { "/DeleteSong" }
)
public class DeleteSong extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass());
    private final UserDAO userDAO = new UserDAO();
    private final SongDAO songDAO = new SongDAO();

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
                    songDAO.remove((Integer) session.getAttribute("songID"));
                    session.setAttribute("message", "Song Deleted");
                    logger.debug("removed song " + session.getAttribute("songID"));
                } else if (parameterName.equalsIgnoreCase("Cancel")) {
                    logger.debug("Did not remove song " + session.getAttribute("songID"));
                    session.setAttribute("message", "Song Not Deleted");
                }
            }
            url = "/ShowAPlaylist";

            logger.debug("sending redirect to " + url);
            response.sendRedirect(url);
        } else { // bounce
            session.setAttribute("message", "user not authenticated");
            url = "/index.jsp";
            response.sendRedirect(url);
        }
    }
}