package butlers;

import engines.PlaylistAndSongManager;
import entity.FullPlayList;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
     *  Handles HTTP GET requests.
     *
     *@param  request                   the HttpServletRequest object
     *@param  response                   the HttpServletResponse object
     *@exception ServletException  if there is a Servlet failure
     *@exception IOException       if there is an IO failure
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int listID = Integer.valueOf(request.getParameter("listID"));
        manager.compilePlayList(listID);
    }
}
