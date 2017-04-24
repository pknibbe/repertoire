package butlers;

import entity.Playlist;
import org.apache.log4j.Logger;
import persistence.PlaylistDAO;
import persistence.UserDAO;
import persistence.SongDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Provides access to A Music Play List
 * Created by peter on 3/4/2017.
 */
@WebServlet(
        name = "ShowAPlaylist",
        urlPatterns = { "/ShowAPlaylist" })
public class ShowAPlaylist extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass());
    private final PlaylistDAO playlistDAO = new PlaylistDAO();
    private final UserDAO userDAO = new UserDAO();
    private final SongDAO songDAO = new SongDAO();

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
        Playlist playlist;

        logger.debug("In doGet");
        if (userDAO.authenticated((Integer) session.getAttribute("user_id"))) {
            playlist = playlistDAO.get((Integer) session.getAttribute("listID"));
            session.setAttribute("listName", playlist.getName());
            session.setAttribute("message", "Playlist " + session.getAttribute("listName"));
            session.setAttribute("songs", songDAO.getAll((Integer) session.getAttribute("listID")));

            logger.debug("Loaded songs");
            url = "/manageAPlaylist.jsp";

        } else { // bounce
            session.setAttribute("message", "user not authenticated");
            url = "/index.jsp";
        }
        RequestDispatcher dispatcher = servletContext.getRequestDispatcher(url);
        logger.debug("Redirecting to " + url);
        dispatcher.forward(request, response);
    }
}
