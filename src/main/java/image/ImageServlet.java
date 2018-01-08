/*
 */
package image;

import static image.ImageUpload.FILE_FOLDER_PATH;
import java.io.IOException;
import java.io.PrintWriter;
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
import javax.ws.rs.core.HttpHeaders;

/**
 *
 * @author caikovsk
 */
@WebServlet(urlPatterns = {"/image/*"})
public class ImageServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("XXXXXXXXXXXX " + getServletInfo());
        String fileName = request.getPathInfo();
        if (fileName != null) {
            fileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8.name());
            Path filePath = Paths.get(FILE_FOLDER_PATH, fileName.substring(1));
            if (Files.exists(filePath)) {
                response.setHeader(HttpHeaders.CONTENT_TYPE, getServletContext().getMimeType(fileName));
                response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(Files.size(filePath)));
                //content-disposition inline?
                Files.copy(filePath, response.getOutputStream());
            } else {
                sendNotFound(response, "file " + filePath + " does not exist");
            }
        } else {
            sendNotFound(response, "file name not specified");
        }
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            out.println(fileName);
        }
    }

    void sendNotFound(HttpServletResponse response, String msg) throws IOException {
        System.out.println(msg);
        response.sendError(HttpServletResponse.SC_NOT_FOUND, msg);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
