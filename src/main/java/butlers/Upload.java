package butlers;

import engines.UserManager;
import org.apache.log4j.Logger;
import persistence.SongDAO;
import entity.Song;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;

/**
 * Handle request from manageAPlaylist jsp
 * This can be a) request to upload a new song
 *             b) request to delete a song
 * Created by peter on 3/17/2017.
 */
@WebServlet(
        name = "Upload",
        urlPatterns = { "/Upload" }
)
@MultipartConfig
public class Upload extends HttpServlet {

    private final UserManager userManager = new UserManager();
    HttpSession session;
    private final Logger logger = Logger.getLogger(this.getClass());
    private final SongDAO songDAO = new SongDAO();


    /**
     * Handles HTTP POST requests.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if there is a Servlet failure
     * @throws IOException      if there is an IO failure
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        session = request.getSession();

        logger.info("This doPost is called");
        logger.info("The user ID is " + session.getAttribute("user_id"));

        Enumeration<String> parameterNames = request.getParameterNames();

        logger.info("Collected the parameter names");

        while (parameterNames.hasMoreElements()) {
            String pName = parameterNames.nextElement();
            logger.info("Parameter name is " + pName);
            logger.info("Parameter value is " + request.getParameter(pName));
        }

        if (userManager.authenticated((Integer) session.getAttribute("user_id"))) {
            Part filePart = request.getPart("file");

            // Create path components to save the file
            String path = "../Data/";
            final String fileName = getFileName(filePart);
            logger.info("Parameter fileName value is " + fileName);
            path += fileName;
            boolean success = writeFile(path, filePart, fileName);
            if (success) { // Add new song to database
                int listID = (Integer) (session.getAttribute("listID"));
                Song song = new Song(path, listID );
                songDAO.add(song);
                logger.info("Added song record to the database");
                session.setAttribute("message", "Song Added");
                logger.info("Set message attribute");
            } else {
                session.setAttribute("message", "Unable to add song");
                logger.info("Song addition failed");
            }
            logger.info("About to forward to ShowAPlaylist");
            response.sendRedirect("ShowAPlaylist");

        } else { // user not authenticated
            session.setAttribute("message", "user not authenticated");
            response.sendRedirect("/index.jsp");
        }

    }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        logger.info( "Part Header = " + partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    private boolean writeFile(String path, Part filePart, String fileName) throws IOException {
        OutputStream out = null;
        InputStream filecontent = null;
        boolean success = true;
        logger.info("in writeFile");

        try {
            logger.info("in try block. about to open " + path);
            Path relPath = Paths.get(path);
            logger.info("File " + fileName + " being uploaded to " + relPath.toAbsolutePath());
            out = new FileOutputStream(new File(path));
            logger.info("FileOutputStream successfully opened");
            filecontent = filePart.getInputStream();
            logger.info("InputStream successfully opened");

            int read;
            final byte[] bytes = new byte[1024];


            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            logger.info("File content copied");
        } catch (FileNotFoundException fne) {
            logger.error( "Problems during file upload. Error: " + fne.getMessage());
            success = false;
        } finally {
            if (out != null) {
                out.close();
            }
            logger.info("closed out");
            if (filecontent != null) {
                filecontent.close();
            }
            logger.info("closed filecontent");

        }
        return success;
    }

}
