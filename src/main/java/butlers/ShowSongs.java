package butlers;

import engines.Authentication;
import persistence.SongDAO;
import org.apache.log4j.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Provides access to Music Play Lists
 * Created by peter on 3/4/2017.
 */
@WebServlet(
        name = "ShowSongs",
        urlPatterns = { "/ShowSongs" })
public class ShowSongs extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass());

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
        logger.info("In ShowSongs.doGet");
        SongDAO dao = new SongDAO();

        ServletContext servletContext = getServletContext();
        Authentication authentication = new Authentication();
        String url;

        if (authentication.authenticated(servletContext)) {
            logger.info("getting list of songs");
            request.setAttribute("songs", dao.getAll());
            servletContext.setAttribute("message", "");
            url = "/uploadSong.jsp";
        } else {
            servletContext.setAttribute("message", "user not authenticated");
            url = "/index.jsp";
        }
        logger.info("Dispatching request forward to " + url);
        RequestDispatcher dispatcher = servletContext.getRequestDispatcher(url);
        logger.info("Using dispatcher " + dispatcher.toString());
        dispatcher.forward(request, response);
    }
}
