package butlers;

import engines.*;
import entity.Song;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
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
    private final SongManager songManager = new SongManager();
    private PlayerManager playerManager;

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
        ArrayList<Song> songs = (ArrayList<Song>) session.getAttribute("songs");

        Enumeration<String> parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            logger.info("Parameter " + parameterName + " is " + request.getParameter(parameterName));
            if (parameterName.equalsIgnoreCase("Play")) {
                startPlayer(songs);
            }
            if (parameterName.equalsIgnoreCase("Stop")) {
                stopPlayer();
            }
            if (parameterName.equalsIgnoreCase("Skip")) {
                stopPlayer();
                int currentSong = songManager.getCurrentSongIndex() + 1;
                if (currentSong < songs.size()) songManager.setCurrentSongIndex(currentSong);
                startPlayer(songs);
            }
            if (parameterName.equalsIgnoreCase("Previous")) {
                stopPlayer();
                int currentSong = songManager.getCurrentSongIndex() - 1;
                if (currentSong > -1) songManager.setCurrentSongIndex(currentSong);
                startPlayer(songs);
            }
        }
    }

    private void startPlayer(ArrayList<Song> songs) {
        playerManager = new PlayerManager(); // Make sure this is a new playerManager so the thread pool is available
        playerManager.start(songs); // create a thread and a loop that calls the player
    }

    private void stopPlayer() {
        playerManager.stop(); // kill the thread
    }
}
