package butlers;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.*;
import persistence.MessageDAO;
/**
 * Provides access to messages
 * Created by peter on 3/4/2017.
 */
@WebServlet(
        name = "ShowMessageLists",
        urlPatterns = { "/ShowMessageLists" })
public class ShowMessageLists extends HttpServlet {


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
        MessageDAO dao = new MessageDAO();

        ServletContext servletContext = getServletContext();

        request.setAttribute("users", dao.getAll());
        String currentMessage = (String) request.getAttribute("SessionMessage");
        if (currentMessage == null) {
            request.setAttribute("SessionMessage", "");
        } else
            request.setAttribute("SessionMessage", currentMessage);

        String url = "/accounts.jsp";

        RequestDispatcher dispatcher = servletContext.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

}
