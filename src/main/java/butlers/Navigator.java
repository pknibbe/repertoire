package butlers;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Provide utility methods for directing browsers to another location
 * Created by peter on 4/29/2017.
 */
public class Navigator {

    public static void forward(HttpServletRequest request,
                               HttpServletResponse response,
                               ServletContext servletContext,
                               String destination) throws ServletException, IOException {
        servletContext.getRequestDispatcher(destination).forward(request, response);
    }

    public static void redirect(HttpServletResponse response, String destination)  throws ServletException, IOException {
        response.sendRedirect(destination);
    }
}
