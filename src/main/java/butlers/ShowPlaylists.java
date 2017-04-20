package butlers;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.*;
import java.util.ArrayList;

import engines.UserManager;
import engines.PlaylistManager;
import entity.*;
import org.apache.log4j.Logger;
import persistence.PlaylistDAO;
/**
 * Provides access to Music Play Lists
 * Created by peter on 3/4/2017.
 */
@WebServlet(
        name = "ShowPlaylists",
        urlPatterns = { "/ShowPlaylists" })
public class ShowPlaylists extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass());

    /**
     *  Handles HTTP GET requests.
     *
     *@param  request                   the HttpServletRequest object
     *@param  response                   the HttpServletResponse object
     *@exception ServletException  if there is a Servlet failure
     *@exception IOException       if there is an IO failure
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        ServletContext servletContext = getServletContext();
        String url;

        if (UserManager.authenticated((Integer) session.getAttribute("user_id"))) {
            int user_id = (Integer) session.getAttribute("user_id");
            ArrayList<PresentablePlaylist> presentables = new ArrayList<>();
            Playlist playlist;
            PlaylistDAO playlistDAO = new PlaylistDAO();

            int listOwnerID;
            String owner_name;

            ArrayList<Integer> listIDs = PlaylistManager.getIDs((Integer) session.getAttribute("user_id"));
            for (int index : listIDs) {
                playlist = playlistDAO.get(index);
                listOwnerID = playlist.getOwner_id();
                if (listOwnerID == user_id) {
                    owner_name = "Me";
                } else {
                    owner_name = UserManager.getName(listOwnerID);
                }
                PresentablePlaylist presentablePlaylist = new PresentablePlaylist(index,
                        playlist.getName(),
                        listOwnerID,
                        owner_name);
                logger.info("The playlist ID attribute is " + session.getAttribute("listID"));
                logger.info(presentablePlaylist.toString());
                if (index == (Integer) session.getAttribute("listID")) {
                    presentablePlaylist.setPlayerState((String) session.getAttribute("playerState"));
                }
                presentables.add(presentablePlaylist);
                logger.info(presentablePlaylist.toString());
            }
            session.setAttribute("playlists", presentables);
            url = "/showPlaylists.jsp";

        } else {
            session.setAttribute("message", "user not authenticated");
            url = "/index.jsp";
        }
        RequestDispatcher dispatcher = servletContext.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}
