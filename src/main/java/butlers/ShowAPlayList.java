package butlers;

import engines.PlaylistManager;
import engines.UserManager;
import entity.Playlist;
import persistence.PlaylistDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Provides access to A Music Play List
 * Created by peter on 3/4/2017.
 */
@WebServlet(
        name = "ShowAPlayList",
        urlPatterns = { "/ShowAPlayList" })
public class ShowAPlayList extends HttpServlet {

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
        PlaylistDAO dao = new PlaylistDAO();
        PlaylistManager playlistManager = new PlaylistManager();

        ServletContext servletContext = getServletContext();
        UserManager userManager = new UserManager();
        String url;

        if (userManager.authenticated(servletContext)) {
            ArrayList<Integer> listIDs = playlistManager.getIDs((Integer) servletContext.getAttribute("user_id"));
            ArrayList<Playlist> playlists = new ArrayList<>();
            for (int index : listIDs) {
                playlists.add(dao.get(index));
            }
            servletContext.setAttribute("playlists", playlists);
            url = "/playlists.jsp";

        } else {
            servletContext.setAttribute("message", "user not authenticated");
            url = "index.jsp";
        }
        RequestDispatcher dispatcher = servletContext.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}
