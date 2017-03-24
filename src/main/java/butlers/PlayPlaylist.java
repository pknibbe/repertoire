package butlers;

import entity.MP3Player;
import engines.UserManager;
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

/**
 * Plays all of the songs in a playlist
 * Created by peter on 3/21/2017.
 */
@WebServlet(
        name = "ShowAPlaylist",
        urlPatterns = { "/ShowAPlaylist" })
public class PlayPlaylist extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass());
    private final UserManager userManager = new UserManager();
    private final MP3Player mp3Player = new MP3Player();


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

        logger.info("In doGet");
        if (userManager.authenticated((Integer) session.getAttribute("user_id"))) {
            ArrayList<Song> songs = (ArrayList<Song>) session.getAttribute("songs");

            for (Song song : songs) {
                mp3Player.play(song.getLocation());
            }

        } else { // bounce
                    session.setAttribute("message", "user not authenticated");
        }

    }

}
