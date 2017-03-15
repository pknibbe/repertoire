package butlers;

import engines.AuthenticationManager;
import engines.UserManager;
import org.apache.log4j.Logger;
import engines.PlaylistManager;
import entity.*;
import persistence.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Handle request from playlists jsp
 * This can be a) request to create a new playlist
 *             b) request to delete a playlist
 *             c) request to share a playlist
 *             d) request to modify a playlist
 * Created by peter on 3/6/2017.
 */
@WebServlet(
        name = "ManagePlaylist",
        urlPatterns = { "/ManagePlaylist" }
)
public class ManagePlaylist extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass());
    private final PlaylistManager PlayListmanager = new PlaylistManager();

    /**
     * Handles HTTP GET requests.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if there is a Servlet failure
     * @throws IOException      if there is an IO failure
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        AuthenticationManager authenticationManager = new AuthenticationManager();
        UserManager userManager = new UserManager();
        String url;
        SongDAO songDAO = new SongDAO();
        UserDAO userDAO = new UserDAO();
        PlaylistDAO playlistDAO = new PlaylistDAO();

        if (authenticationManager.authenticated(servletContext)) {

            logger.info("In ManagePlaylist.doPost");
            int listID = Integer.valueOf(request.getParameter("listID"));
            if (listID > 0) { // must be delete, modify, or share request
                String action = request.getParameter("Action");
                servletContext.setAttribute("listID", listID);
                servletContext.setAttribute("listName", playlistDAO.get(listID).getName());

                if (action.equalsIgnoreCase("Delete")) { // remove the playlist via confirmation dialogue
                    url = "deletePlaylistConfirmation.jsp";
                } else if (action.equalsIgnoreCase("Share")) { // share the playlist with another user
                    ArrayList<Integer> otherUserIDs = userManager.getOtherUserIDs((Integer) servletContext.getAttribute("user_id"));
                    ArrayList<String> otherUserNames = new ArrayList<>();
                    for (Integer userID : otherUserIDs) {
                        otherUserNames.add(userDAO.get(userID).getName());
                    }
                    servletContext.setAttribute("otherUserNames", otherUserNames);
                    url = "sharePlaylist.jsp";
                } else { // make changes to the playlist via another web page

                    FullPlayList fullPlayList = PlayListmanager.compilePlayList(listID);

                    servletContext.setAttribute("playlist", fullPlayList.getPlaylist());
                    servletContext.setAttribute("listSongs", fullPlayList.getSongs());
                    servletContext.setAttribute("allSongs", songDAO.getAll());
                    url = "manageAPlayList.jsp";
                }
            } else { // need to create a new playlist and associate it with the session user
                Playlist playlist = new Playlist(request.getParameter("name"));
                listID = playlistDAO.add(playlist);
                Association association = new Association("users",
                        (Integer) servletContext.getAttribute("user_id"), listID, "playlists", "accessor");
                AssociationDAO associationDAO = new AssociationDAO();
                associationDAO.add(association);

                servletContext.setAttribute("playlist", playlistDAO.get(listID));
                ArrayList<Song> songs = new ArrayList<Song>();
                servletContext.setAttribute("listSongs", songs);
                servletContext.setAttribute("allSongs", songDAO.getAll());
                servletContext.setAttribute("message", "");

                url = "manageAPlayList.jsp";
            }
        } else {
            servletContext.setAttribute("message", "user not authenticated");
            url = "index.jsp";
        }
        response.sendRedirect(url);
    }
}
