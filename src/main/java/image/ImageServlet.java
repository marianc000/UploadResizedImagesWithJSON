/*
 */
package image;

import static image.ImageConstants.FILE_STORAGE_LOCATION;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author caikovsk
 */
@WebServlet(urlPatterns = {"/image/*"})
public class ImageServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
        String fileName = request.getPathInfo();
        if (fileName != null) {
            fileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8.name());
            Path filePath = Paths.get(FILE_STORAGE_LOCATION, fileName.substring(1));
            if (Files.exists(filePath)) {
                response.setContentType(getServletContext().getMimeType(fileName)); // todo: it will be always the same
                response.setContentLengthLong(Files.size(filePath));
                Files.copy(filePath, response.getOutputStream());
            } else {
                sendNotFound(response, "file " + filePath + " does not exist");
            }
        } else {
            sendNotFound(response, "file name not specified");
        }
    }

    void sendNotFound(HttpServletResponse response, String msg) throws IOException {
        System.out.println(msg);
        response.sendError(HttpServletResponse.SC_NOT_FOUND, msg);
    }

}
