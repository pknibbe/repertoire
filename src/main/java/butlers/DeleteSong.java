package butlers;

import entity.User;
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

        if (((User) session.getAttribute("user")).getId() < 1) { // Guest users may not create, delete, or manage playlists
            Navigator.redirect(response, "LogOut");
        } else {

            Enumeration<String> parameterNames = request.getParameterNames();
            try {
            while (parameterNames.hasMoreElements()) {
                String parameterName = parameterNames.nextElement();
                logger.debug("Parameter " + parameterName + " is " + request.getParameter(parameterName));
                if (parameterName.equalsIgnoreCase("Delete")) {
                    songDAO.delete(songDAO.read((Integer) session.getAttribute("songID")));
                    session.setAttribute("message", "Song Deleted");
                    logger.debug("removed song " + session.getAttribute("songID"));
                } else if (parameterName.equalsIgnoreCase("Cancel")) {
                    logger.debug("Did not remove song " + session.getAttribute("songID"));
                    session.setAttribute("message", "Song Not Deleted");
                }
            }
            session.setAttribute("songs", songDAO.getAllThese((Integer) session.getAttribute("listID")));
            response.sendRedirect("/manageAPlaylist.jsp");
            } catch (Exception e) {
                logger.error("Serious error caught. Logging the user out.", e);
                session.setAttribute("message", "Repertoire has encountered a serious error. Please contact the administrator for assistance.");
                response.sendRedirect("/Logout");
            }
        }
    }
}