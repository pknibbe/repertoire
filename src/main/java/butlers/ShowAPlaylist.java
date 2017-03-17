package butlers;

import engines.UserManager;
import entity.Playlist;
import org.apache.log4j.Logger;
import persistence.PlaylistDAO;
import engines.SongManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private final SongManager songManager = new SongManager();
    private final UserManager userManager = new UserManager();
    private Playlist playlist;

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

        ServletContext servletContext = getServletContext();
        String url;

        logger.info("In doGet");
        if (userManager.authenticated(servletContext)) { // proceed
            playlist = playlistDAO.get((Integer) servletContext.getAttribute("listID"));
            servletContext.setAttribute("message", "Playlist " + servletContext.getAttribute("listName"));
            servletContext.setAttribute("songs", songManager.getSongs((Integer) servletContext.getAttribute("listID")));

            logger.info("Loaded songs");
            url = "/manageAPlaylist.jsp";

        } else { // bounce
            servletContext.setAttribute("message", "user not authenticated");
            url = "index.jsp";
        }
        RequestDispatcher dispatcher = servletContext.getRequestDispatcher(url);
        logger.info("Redirecting to " + url);
        dispatcher.forward(request, response);
    }
}
