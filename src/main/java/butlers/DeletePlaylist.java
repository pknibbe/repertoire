package butlers;

import entity.User;
import org.apache.log4j.Logger;
import entity.Playlist;
import persistence.PlaylistDAO;

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

        if (((User) session.getAttribute("user")).getId() < 1) { // Guest users may not create, delete, or edit playlists
            response.sendRedirect("LogOut");
        } else {
            Enumeration<String> parameterNames = request.getParameterNames();
            int playlist_id = (Integer) session.getAttribute("listID");
            try {
            while (parameterNames.hasMoreElements()) {
                String parameterName = parameterNames.nextElement();
                logger.debug("Parameter " + parameterName + " is " + request.getParameter(parameterName));
                if (parameterName.equalsIgnoreCase("Delete")) {
                    Playlist playlist = playlistDAO.read(playlist_id);
                    playlistDAO.delete(playlist);

                    if (playlistDAO.read(playlist_id) == null) {
                        session.setAttribute("message", "List Deleted");
                    } else {
                        session.setAttribute("message", "List Not Deleted - Is it shared with anyone?");
                    }
                    logger.debug("removed playlist " + playlist_id);
                } else if (parameterName.equalsIgnoreCase("Cancel")) {
                    logger.debug("Did not remove playlist " + playlist_id);
                    session.setAttribute("message", "List Not Deleted");
                }
            }
            url = "/ShowPlaylists";

            logger.debug("sending redirect to " + url);
            response.sendRedirect(url);
            } catch (Exception e) {
                logger.error("Serious error caught. Logging the user out.", e);
                session.setAttribute("message", "Repertoire has encountered a serious error. Please contact the administrator for assistance.");
                response.sendRedirect("/Logout");
            }
        }
    }
}