package butlers;

import persistence.SongDAO;
import entity.Player;
import entity.Playlist;
import entity.User;
import persistence.PlaylistDAO;
import persistence.UserDAO;
import persistence.SharedDAO;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Handle request from showPlaylists jsp
 * This can be a) request to create a new playlist
 *             b) request to play a playlist
 *             c) request to manage a playlist
 * Created by peter on 4/18/2017.
 */
@WebServlet(
        name = "PlaylistAction",
        urlPatterns = { "/PlaylistAction" }
)
public class PlaylistAction extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass());
    private final PlaylistDAO playlistDAO = new PlaylistDAO();
    private final UserDAO userDAO = new UserDAO();
    private final SongDAO songDAO = new SongDAO();
    private final SharedDAO sharedDAO = new SharedDAO();

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
        boolean redirected = false;

        int user_id = ((User) session.getAttribute("user")).getId();
        if (user_id < 1) {
            Navigator.redirect(response, bad());
            redirected = true;
        } else {
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String parameterName = parameterNames.nextElement();
                String parameterValue = request.getParameter(parameterName);
                logger.debug("Parameter " + parameterName + " found with value " + request.getParameter(parameterName));

                if (parameterName.equalsIgnoreCase("create")) {
                    Navigator.redirect(response, create(request, session, user_id));
                    redirected = true;
                } else if (parameterValue.equalsIgnoreCase("Play")) {
                    Navigator.redirect(response, play(Integer.valueOf(parameterName.substring(6)), session));
                    redirected = true;
                } else if (parameterValue.equalsIgnoreCase("Stop")) {
                    Navigator.redirect(response, stop(session));
                    redirected = true;
                } else if (parameterValue.equalsIgnoreCase("manage")) {
                    Navigator.redirect(response, manage(session, Integer.valueOf(parameterName.substring(6))));
                    redirected = true;
                } else if (parameterValue.equalsIgnoreCase("delete")) {
                    Navigator.redirect(response, delete(session, Integer.valueOf(parameterName.substring(6)), user_id));
                    redirected = true;
                }
            }
        }
        if (!redirected) {
            Navigator.redirect(response, "/showPlaylists");
        }
    }

    /**
     * Creates a new playlist
     * @param request The user request
     * @param session The user session
     * @param user_id The user ID
     * @return The next page to load
     */
    private String create(HttpServletRequest request, HttpSession session, int user_id) {
        int listID = playlistDAO.create( new Playlist(request.getParameter("listname"), userDAO.read(user_id)));
        session.setAttribute("listName", request.getParameter("listname"));
        session.setAttribute("listID", listID);
        session.setAttribute("message", "Playlist " + playlistDAO.read(listID).getName());
        session.setAttribute("songs", songDAO.getAllThese(listID));
        session.setAttribute("myPlaylists", playlistDAO.getAllMine(user_id));
        session.setAttribute("potentialSharees", sharedDAO.notSharing(listID, user_id));
        session.setAttribute("currentSharees", sharedDAO.sharing(listID));
        return "manageAPlaylist.jsp";
    }

    private String delete(HttpSession session, int listID, int user_id) {
        playlistDAO.remove(listID, user_id);
        session.setAttribute("myPlaylists", playlistDAO.getAllMine(user_id));
        return "showPlaylists.jsp";
    }

    /**
     * Starts play of a playlist
     * @param listID The playlist ID
     * @param session The user session
     * @return The next page to load
     */
    private String play(int listID, HttpSession session) {
        Player player = new Player(listID);
        session.setAttribute("message", "Starting playback");
        session.setAttribute("isPlaying", true);
        session.setAttribute("player", player);
        return "showPlaylists.jsp";
    }

    /**
     * Stops the play of the playlist
     * @return The next page to load
     */
    private String stop(HttpSession session) {
        session.setAttribute("isPlaying", false);
        session.setAttribute("message", "Stopping playback");
        Player player = (Player) session.getAttribute("player");
        player.stop();
        return "showPlaylists.jsp";
    }

    /**
     * Prepares for user management of a playlist
     * @return The next page to load
     */
    private String manage(HttpSession session, int listID) {
        Playlist playlist = playlistDAO.read(listID);
        session.setAttribute("listName", playlist.getName());
        session.setAttribute("listID", listID);
        session.setAttribute("message", "Playlist " + playlist.getName());
        session.setAttribute("songs", songDAO.getAllThese(listID));
        return "manageAPlaylist.jsp";
    }


    private String bad() {
        return "index.jsp";
    }
}
