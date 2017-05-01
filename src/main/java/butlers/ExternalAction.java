package butlers;

import org.apache.log4j.Logger;
import persistence.UserDAO;
import persistence.PlaylistDAO;
import persistence.SharedDAO;
import entity.User;
import entity.Playlist;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Set up session attributes and set the internal home page
 * Created by peter on 4/29/2017.
 */
@WebServlet(
        name = "ExternalAction",
        urlPatterns = { "/ExternalAction" }
) public class ExternalAction extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass());
    private final UserDAO userDAO = new UserDAO();
    private final PlaylistDAO playlistDAO = new PlaylistDAO();
    private final SharedDAO sharedDAO = new SharedDAO();

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

        logger.debug("user_name is " + request.getParameter("userName"));
        logger.debug("user_pass is " + request.getParameter("password"));
        String userName = request.getParameter("userName");
        int user_id = userDAO.getIdByUsername(userName);

        if (!userDAO.verifyCredentials(user_id, userName, request.getParameter("password"))) {
            session.setAttribute("user", UserDAO.getGuest());
            session.setAttribute("message", "User Credentials not verified. Logged in as Guest");
            session.setAttribute("securityMessage","To access controlled features" +
                                                          ", please log out and log back in as yourself." );
        } else {
            User user = userDAO.read(user_id);

            session.setAttribute("user", user);
            session.setAttribute("message", "Welcome, " + user.getName() + ".");
            session.setAttribute("securityMessage", " If you are not " + user.getName() +
                                                           ", please log out and log back in as yourself.");
        }
        session.setAttribute("listID", 0);
        session.setAttribute("isPlaying", false);
        session.setAttribute("myPlaylists", playlistDAO.getAllMine(user_id));
        session.setAttribute("receivedPlaylists", sharedDAO.getReceivedPlaylists(user_id));

        Navigator.forward(request, response, servletContext, "/showPlaylists.jsp");
    }
}