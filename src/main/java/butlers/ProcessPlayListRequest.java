package butlers;

import engines.Authentication;
import org.apache.log4j.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import entity.Association;
import persistence.AssociationDAO;
import engines.AssociationManager;

/**
 * Handle requests from the playList page
 * Created by Peter Knibbe on 2/8/17.
 */

@WebServlet(
        name = "ProcessPlayListRequest",
        urlPatterns = { "/ProcessPlayListRequest" }
)
public class ProcessPlayListRequest extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass());
    /**
     *  Handles HTTP POST requests.
     *
     *@param  request                   the HttpServletRequest object
     *@param  response                   the HttpServletResponse object
     *@exception ServletException  if there is a Servlet failure
     *@exception IOException       if there is an IO failure
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        servletContext.setAttribute("message", "");
        Authentication authentication = new Authentication();
        String url;

        if (authentication.authenticated(servletContext)) {

            String command = request.getParameter("submit");
            int songId = Integer.valueOf(request.getParameter("songID"));
            Association association;
            AssociationDAO associationDAO = new AssociationDAO();
            AssociationManager associationManager = new AssociationManager();
            int playlistId = Integer.valueOf(request.getParameter("playlistID"));

            if (command.equalsIgnoreCase("Add")) {
                if (songId < 1) { // This is a request to make a new association
                    int newSong = Integer.valueOf(request.getParameter("song"));
                    association = new Association("playlists", playlistId, newSong, "songs");
                    associationDAO.add(association);
                }
            } else if (command.equalsIgnoreCase("Delete")) {
                association = associationManager.getAssociation("playlists", playlistId, songId, "songs");
                associationDAO.remove(association.getId());
            }

            url = "ViewPlaylist";
        } else {
        servletContext.setAttribute("message", "user not authenticated");
        url = "index.jsp";
    }

    response.sendRedirect(url);

    }
}