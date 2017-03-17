package butlers;

import persistence.SongDAO;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;


/**
 * Remove a playlist from the database
 * Created by Peter Knibbe on 2/8/17.
 */

@WebServlet(
        name = "DeleteSong",
        urlPatterns = { "/DeleteSong" }
)
public class DeleteSong extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass());
    private final SongDAO songDAO = new SongDAO();

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

        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            logger.info("Parameter " + parameterName + " is " + request.getParameter(parameterName));
            if (parameterName.equalsIgnoreCase("Delete")) {
                songDAO.remove((Integer) servletContext.getAttribute("songID"));
                servletContext.setAttribute("message", "Song Deleted");
                logger.info("removed song " + servletContext.getAttribute("songID"));
            } else if (parameterName.equalsIgnoreCase("Cancel")) {
                logger.info("Did not remove song " + servletContext.getAttribute("songID"));
                servletContext.setAttribute("message", "Song Not Deleted");
            }
        }
        String url = "/ShowAPlaylist";

        logger.info("sending redirect to " + url);
        response.sendRedirect(url);
    }
}