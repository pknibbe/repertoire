package butlers;

import persistence.SongDAO;
import entity.Player;
import entity.Playlist;
import persistence.PlaylistDAO;
import persistence.UserDAO;
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
        Player player;
        String url = "ShowPlaylists";
        int listID;

        if (userDAO.authenticated((Integer) session.getAttribute("user_id"))) {
            int user_id = (Integer) session.getAttribute("user_id");
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String parameterName = parameterNames.nextElement();
                String parameterValue = request.getParameter(parameterName);
                logger.debug("Parameter " + parameterName + " found with value " + request.getParameter(parameterName));

                if (parameterName.equalsIgnoreCase("create")) {
                    listID = playlistDAO.add( new Playlist(request.getParameter("listname"), userDAO.read(user_id)));
                    session.setAttribute("listName", request.getParameter("listname"));
                    session.setAttribute("listID", listID);
                    session.setAttribute("message", "Playlist " + session.getAttribute("listName"));
                    session.setAttribute("songs", songDAO.getAll((Integer) session.getAttribute("listID")));
                    url = "manageAPlaylist.jsp";
                } else if (parameterValue.equalsIgnoreCase("Play")) {
                    listID = Integer.valueOf(parameterName.substring(6));
                    player = new Player(listID);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    session.setAttribute("message", "Starting playback");
                    session.setAttribute("isPlaying", true);
                    session.setAttribute("player", player);
                    player.start();
                    url = "showPlaylists.jsp";
                } else if (parameterValue.equalsIgnoreCase("Stop")) {
                    //PlaylistManager.stop();
                    session.setAttribute("isPlaying", false);
                    session.setAttribute("message", "Stopping playback");
                    player = (Player) session.getAttribute("player");
                    player.stop();
                    url = "showPlaylists.jsp";
                } else if (parameterValue.equalsIgnoreCase("manage")) {
                    listID = Integer.valueOf(parameterName.substring(6));
                    Playlist playlist = playlistDAO.get(listID);
                    session.setAttribute("listName", playlist.getName());
                    session.setAttribute("listID", listID);
                    session.setAttribute("message", "Playlist " + session.getAttribute("listName"));
                    session.setAttribute("songs", songDAO.getAll((Integer) session.getAttribute("listID")));
                    url = "ShowAPlaylist";
                }
            }
        } else { // user not authenticated
            session.setAttribute("message", "user not authenticated");
            url = "/index.jsp";
        }
        response.sendRedirect(url);
    }
}
