package butlers;

import java.util.*;

import engines.SharedManager;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import engines.UserManager;
import persistence.SongDAO;
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

    private final SongDAO songDAO = new SongDAO();
    private final UserManager userManager = new UserManager();
    private final Logger logger = Logger.getLogger(this.getClass());
    private final SharedManager sharedManager = new SharedManager();


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
        int list_id = (Integer) session.getAttribute("listID");

        logger.info("This doPost is called");
        logger.info("The user ID is " + session.getAttribute("user_id"));

        Enumeration<String> parameterNames = request.getParameterNames();

        logger.info("Collected the parameter names");

        while (parameterNames.hasMoreElements()) {
            String pName = parameterNames.nextElement();
            logger.info("Parameter name is " + pName);
            logger.info("Parameter value is " + request.getParameter(pName));
        }

        if (userManager.authenticated((Integer) session.getAttribute("user_id"))) {
            int songID = Integer.valueOf(request.getParameter("songID"));

            logger.info("In delete section");
            session.setAttribute("songToDelete", songDAO.get(songID).getLocation());
            session.setAttribute("songID", songID);
            if (sharedManager.isShared(list_id)) {
                session.setAttribute("message", "Can't delete song from shared playlist");
                response.sendRedirect("/manageAPlaylist.jsp");
            } else response.sendRedirect("/deleteSongConfirmation.jsp");
        } else { // user not authenticated
            session.setAttribute("message", "user not authenticated");
            response.sendRedirect("/index.jsp");
        }
    }
}
