package butlers;

import entity.Player;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Provides control over a music player thread
 * Created by peter on 3/24/2017.
 */
@WebServlet(
        name = "ControlPlayer",
        urlPatterns = { "/ControlPlayer" })
public class ControlPlayer extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass());
    private Player player;

    /**
     *  Handles HTTP Post requests.
     *
     *@param  request                   the HttpServletRequest object
     *@param  response                   the HttpServletResponse object
     *@exception ServletException  if there is a Servlet failure
     *@exception IOException       if there is an IO failure
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        logger.info("In doPost");

        Enumeration<String> parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            logger.info("Parameter " + parameterName + " is " + request.getParameter(parameterName));
            if (parameterName.equalsIgnoreCase("Play")) {
                int list_id = (Integer) session.getAttribute("listID");
                player = new Player(list_id);
                session.setAttribute("message", "Starting playback");
                player.start();
            }
            if (parameterName.equalsIgnoreCase("Stop")) {
                session.setAttribute("message", "Stopping playback");
                player.stop();
            }
            if (parameterName.equalsIgnoreCase("Skip")) {
                session.setAttribute("message", "Skipping playback");
                player.skip();
            }
            if (parameterName.equalsIgnoreCase("Previous")) {
                session.setAttribute("message", "Skipping playback back one track");
                player.previous();
            }
        }
        response.sendRedirect("/manageAPlaylist.jsp");
    }
}
