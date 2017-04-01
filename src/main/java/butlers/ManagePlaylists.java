package butlers;

import engines.*;
import org.apache.log4j.Logger;
import entity.*;
import persistence.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Handle request from playlists jsp
 * This can be a) request to create a new playlist
 *             b) request to delete a playlist
 *             c) request to share a playlist
 *             d) request to modify a playlist
 * Created by peter on 3/6/2017.
 */
@WebServlet(
        name = "ManagePlaylists",
        urlPatterns = { "/ManagePlaylists" }
)
public class ManagePlaylists extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass());
    private final UserManager userManager = new UserManager();
    private final PlaylistDAO playlistDAO = new PlaylistDAO();
    private final PlaylistManager playlistManager = new PlaylistManager();
    private final SharedManager sharedManager = new SharedManager();
    private final SongManager songManager = new SongManager();

    /**
     * Handles HTTP POST requests.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if there is a Servlet failure
     * @throws IOException      if there is an IO failure
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String url = "";
        Player player;

        int listID;

        if (userManager.authenticated((Integer) session.getAttribute("user_id"))) {

            logger.debug("In ManagePlaylists.doPost");
            listID = Integer.valueOf(request.getParameter("listID"));
            if (listID == 0) { // request to create a new list
                String name = request.getParameter("newName");
                listID = playlistManager.add((Integer) session.getAttribute("user_id"), name);
                Playlist playlist = playlistDAO.get(listID);
                session.setAttribute("listName", playlist.getName());
                session.setAttribute("listID", listID);
                session.setAttribute("message", "Playlist " + session.getAttribute("listName"));
                session.setAttribute("songs", songManager.getSongs((Integer) session.getAttribute("listID")));
                url = "manageAPlaylist.jsp";
            } else {
                Enumeration<String> parameterNames = request.getParameterNames();

                while (parameterNames.hasMoreElements()) {
                    String parameterName = parameterNames.nextElement();
                    logger.debug("Parameter " + parameterName + " is " + request.getParameter(parameterName));
                    if (parameterName.equalsIgnoreCase("Delete")) {
                        if (sharedManager.isShared(listID)) {
                            session.setAttribute("message", "Can't delete shared playlist.");
                            url = "/manageAPlaylist.jsp";
                        } else {
                            url = "/deletePlaylistConfirmation.jsp";
                        }
                    } else if (parameterName.equalsIgnoreCase("Share")) {
                        session.setAttribute("listName", playlistDAO.get(listID).getName());
                        session.setAttribute("otherUsers", sharedManager.notSharing(listID));
                        session.setAttribute("sharingUsers", sharedManager.sharing(listID));
                        url = "/sharePlaylist.jsp";
                    } else if (parameterName.equalsIgnoreCase("Manage")) {
                        url = "ShowAPlaylist";
                    } else if (parameterName.equalsIgnoreCase("Play")) {
                        player = new Player(listID);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        session.setAttribute("message", "Starting playback");
                        session.setAttribute("playerState", "playing");
                        session.setAttribute("player", player);
                        player.start();
                        url = "ShowAPlaylist";
                    } else if (parameterName.equalsIgnoreCase("Stop")) {
                        session.setAttribute("message", "Stopping playback");
                        session.setAttribute("playerState", "stopped");
                        player = (Player) session.getAttribute("player");
                        player.stop();
                        url = "ShowAPlaylist";
                    }
                }
                session.setAttribute("listID", listID);
                session.setAttribute("listName", playlistDAO.get(listID).getName());
            }
        } else { // user not authenticated
            session.setAttribute("message", "user not authenticated");
            url = "/index.jsp";
        }
        logger.debug("Sending redirect to " + url);
        response.sendRedirect(url);
    }
}
