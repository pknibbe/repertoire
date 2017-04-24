package butlers;

import entity.*;
import persistence.PlaylistDAO;
import persistence.UserDAO;
import persistence.SongDAO;
import persistence.SharedDAO;
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
        String url = "";
        Player player;

        int listID;
        int user_id = (Integer) session.getAttribute("user_id");

        if (userDAO.authenticated(user_id)) {

            listID = Integer.valueOf(request.getParameter("listID"));
            if (listID == 0) { // request to create a new list
                String name = request.getParameter("newName");
                listID = playlistDAO.add( new Playlist(name, userDAO.get(user_id)));
                Playlist playlist = playlistDAO.get(listID);
                session.setAttribute("listName", playlist.getName());
                session.setAttribute("listID", listID);
                session.setAttribute("message", "Playlist " + session.getAttribute("listName"));
                session.setAttribute("songs", songDAO.getAllThese((Integer) session.getAttribute("listID")));
                url = "manageAPlaylist.jsp";
            } else {
                Enumeration<String> parameterNames = request.getParameterNames();

                while (parameterNames.hasMoreElements()) {
                    String parameterName = parameterNames.nextElement();
                    if (parameterName.equalsIgnoreCase("Delete")) {
                        if (sharedDAO.isShared(listID)) {
                            session.setAttribute("message", "Can't delete shared playlist.");
                            url = "/manageAPlaylist.jsp";
                        } else {
                            url = "/deletePlaylistConfirmation.jsp";
                        }
                    } else if (parameterName.equalsIgnoreCase("Share")) {
                        session.setAttribute("listName", playlistDAO.get(listID).getName());
                        session.setAttribute("otherUsers", sharedDAO.notSharing(listID));
                        session.setAttribute("sharingUsers", sharedDAO.sharing(listID));
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
        response.sendRedirect(url);
    }
}
