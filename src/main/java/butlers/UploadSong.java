package butlers;

import java.nio.file.*;

import engines.Authentication;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import entity.Song;
import persistence.SongDAO;

/**
 * Handle File Upload Requests
 * Created by peter on 3/6/2017.
 */
@WebServlet(
        name = "UploadSong",
        urlPatterns = { "/UploadSong" }
)
@MultipartConfig
public class UploadSong extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass());

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ServletContext servletContext = getServletContext();
        Authentication authentication = new Authentication();
        String url;

        if (authentication.authenticated(servletContext)) {
            servletContext.setAttribute("message", "");

            // Create path components to save the file
            final String path = "../" + request.getParameter("destination");
            final Part filePart = request.getPart("file");
            final String fileName = getFileName(filePart);
            final PrintWriter writer = response.getWriter();
            boolean success = writeFile(path, filePart, fileName, writer);
            logger.info("writeFile returned " + success);
            if (success) {
                Song song = new Song(path, fileName, request.getParameter("performer"), request.getParameter("duration"));
                SongDAO songDAO = new SongDAO();
                songDAO.add(song);
            }
            logger.info("redirecting to internalHome.jsp");
            url = "/uploadSong.jsp";
            } else {
                servletContext.setAttribute("message", "user not authenticated");
                url = "index.jsp";
            }
            logger.info("sending redirect to " + url);
            response.sendRedirect(url);
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

    private boolean writeFile(String path, Part filePart, String fileName, PrintWriter writer) throws IOException {
        OutputStream out = null;
        InputStream filecontent = null;
        boolean success = java.lang.Boolean.TRUE;
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
            success = java.lang.Boolean.FALSE;
        } finally {
            if (out != null) {
                out.close();
            }
            logger.info("closed out");
            if (filecontent != null) {
                filecontent.close();
            }
            logger.info("closed filecontent");

            if (writer != null) {
                writer.close();
            }
            logger.info("closed writer");
        }
        return success;
    }
}
