package butlers;

import java.util.*;

import entity.User;
import persistence.UserDAO;
import persistence.SongDAO;

import persistence.SharedDAO;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Handle request from manageAPlaylist jsp to delete a song from a playlist
 * Created by peter on 3/17/2017.
 */
@WebServlet(
        name = "DeleteOneSongFromAPlaylist",
        urlPatterns = { "/DeleteOneSongFromAPlaylist" }
)
public class DeleteOneSongFromAPlaylist extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass());
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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        int user_id = ((User) session.getAttribute("user")).getId();
        if (user_id < 1) { // Guest users may not delete songs
            Navigator.redirect(response, "LogOut");
        } else {
            int list_id = (Integer) session.getAttribute("listID");
            int songID = Integer.valueOf(request.getParameter("songID"));

            try {
            session.setAttribute("songToDelete", songDAO.getLocation(songID));
            session.setAttribute("songID", songID);
            if (sharedDAO.isShared(list_id)) {
                session.setAttribute("message", "Can't delete song from shared playlist");
                response.sendRedirect("/manageAPlaylist.jsp");
            } else response.sendRedirect("/deleteSongConfirmation.jsp");
            } catch (Exception e) {
                logger.error("Serious error caught. Logging the user out.", e);
                session.setAttribute("message", "Repertoire has encountered a serious error. Please contact the administrator for assistance.");
                response.sendRedirect("/Logout");
            }
        }
    }
}
