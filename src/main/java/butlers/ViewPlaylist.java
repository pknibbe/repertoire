package butlers;

import engines.Authentication;
import org.apache.log4j.Logger;
import engines.PlaylistAndSongManager;
import entity.*;
import persistence.*;
import javax.servlet.RequestDispatcher;
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
 * Created by peter on 3/6/2017.
 */
@WebServlet(
        name = "ViewPlaylist",
        urlPatterns = { "/ViewPlaylist" }
)
public class ViewPlaylist extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass());
    private final PlaylistAndSongManager manager = new PlaylistAndSongManager();

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
        Authentication authentication = new Authentication();
        String url;
        SongDAO songDAO = new SongDAO();

        if (authentication.authenticated(servletContext)) {

            logger.info("In ViewPlaylist.doPost");
            int listID = Integer.valueOf(request.getParameter("listID"));
            if (listID > 0) {
                FullPlayList fullPlayList = manager.compilePlayList(listID);

                servletContext.setAttribute("playlist", fullPlayList.getPlaylist());
                servletContext.setAttribute("listSongs", fullPlayList.getSongs());
                servletContext.setAttribute("allSongs", songDAO.getAll());
            } else { // need to create a new playlist and associate it with the session user
                Playlist playlist = new Playlist(request.getParameter("name"));
                PlaylistDAO playlistDAO = new PlaylistDAO();
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

            }
            url = "playList.jsp";
        } else {
            servletContext.setAttribute("message", "user not authenticated");
            url = "index.jsp";
        }
        response.sendRedirect(url);
    }
}
