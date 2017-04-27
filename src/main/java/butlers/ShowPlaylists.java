package butlers;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.*;
import org.hibernate.HibernateException;
import persistence.UserDAO;

//import org.apache.log4j.Logger;
import persistence.PlaylistDAO;
/**
 * Provides access to Music Play Lists
 * Created by peter on 3/4/2017.
 */
@WebServlet(
        name = "ShowPlaylists",
        urlPatterns = { "/ShowPlaylists" })
public class ShowPlaylists extends HttpServlet {
    //private final Logger logger = Logger.getLogger(this.getClass());
    private final PlaylistDAO playlistDAO = new PlaylistDAO();
    private final UserDAO userDAO = new UserDAO();

    /**
     *  Handles HTTP GET requests for the playlists.
     *
     *@param  request                   the HttpServletRequest object
     *@param  response                   the HttpServletResponse object
     *@exception ServletException  if there is a Servlet failure
     *@exception IOException       if there is an IO failure
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException,  HibernateException{

        HttpSession session = request.getSession();
        ServletContext servletContext = getServletContext();
        String url;
        int user_id = (Integer) session.getAttribute("user_id");

        if (userDAO.authenticated(user_id)) {
            session.setAttribute("playlists", playlistDAO.getAll(user_id));
            url = "/showPlaylists.jsp";

        } else {
            session.setAttribute("message", "user not authenticated");
            url = "/index.jsp";
        }
        RequestDispatcher dispatcher = servletContext.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}
