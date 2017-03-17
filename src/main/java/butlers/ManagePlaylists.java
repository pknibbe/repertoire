package butlers;

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
    private final Logger logger = Logger.getLogger(this.getClass());
    private final PlaylistManager playListmanager = new PlaylistManager();

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
        ServletContext servletContext = getServletContext();
        UserManager userManager = new UserManager();
        String url = "";
        SongDAO songDAO = new SongDAO();
        UserDAO userDAO = new UserDAO();
        PlaylistDAO playlistDAO = new PlaylistDAO();
        int listID = 0;

        if (userManager.authenticated(servletContext)) {

            logger.info("In ManagePlaylists.doPost");
            listID = Integer.valueOf(request.getParameter("listID"));
            if (listID == 0) { // request to create a new list
                String name = request.getParameter("name");
                int associationID = playListmanager.add(name, (int) servletContext.getAttribute("user_id"));
                listID = playListmanager.getID(associationID);

                servletContext.setAttribute("playlist", playlistDAO.get(listID));
                servletContext.setAttribute("message", "New List Created");

                url = "manageAPlayList.jsp";
            } else { // must be delete, modify, or share request
                Enumeration<String> parameterNames = request.getParameterNames();

                while (parameterNames.hasMoreElements()) {
                    String parameterName = parameterNames.nextElement();
                    logger.info("Parameter " + parameterName + " is " + request.getParameter(parameterName));
                    if (parameterName.equalsIgnoreCase("Delete")) {
                        url = "deletePlaylistConfirmation.jsp";
                    } else if (parameterName.equalsIgnoreCase("Share")) {
                        ArrayList<Integer> otherUserIDs = userManager.getOtherUserIDs((Integer) servletContext.getAttribute("user_id"));
                        ArrayList<String> otherUserNames = new ArrayList<>();
                        for (Integer userID : otherUserIDs) {
                            otherUserNames.add(userDAO.get(userID).getName());
                        }
                        servletContext.setAttribute("otherUserNames", otherUserNames);
                        url = "/sharePlaylist.jsp";
                    } else if (parameterName.equalsIgnoreCase("Manage")) {
                        url = "ShowAPlaylist";
                    }
                }
            }
        } else { // user not authenticated
            servletContext.setAttribute("message", "user not authenticated");
            url = "index.jsp";
        }
        servletContext.setAttribute("listID", listID);
        servletContext.setAttribute("listName", playlistDAO.get(listID).getName());
        logger.info("Sending redirect to " + url);
        response.sendRedirect(url);
    }
}
