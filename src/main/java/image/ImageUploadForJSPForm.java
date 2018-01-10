/*
 */
package image;

import static image.ImageServlet.IMAGE_SERVLET_PATH;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author caikovsk
 */
@WebServlet(name = "ImageUpload", urlPatterns = {"/uploadJSP"})
@MultipartConfig(location = "C:\\Users\\caikovsk\\Documents\\NetBeans\\postServlet\\src\\main\\webapp\\images")
public class ImageUploadForJSPForm extends HttpServlet {

    public static String FILE_FOLDER_PATH = "C:\\Users\\caikovsk\\Documents\\NetBeans\\postServlet\\src\\main\\webapp\\images\\"; // note,ends in path separator
    public String FILE_PART_NAME = "myFile";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //   response.setContentType("text/html;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_CREATED);
 
         try (PrintWriter out = response.getWriter()) {
            for (Part p : request.getParts()) {

                /* out.write("Part: " + p.toString() + "<br/>\n"); */
                out.write("Part name: " + p.getName() + "<br/>\n");
                out.write("Size: " + p.getSize() + "<br/>\n");
                out.write("Content Type: " + p.getContentType() + "<br/>\n");
                out.write("File name: " + p.getSubmittedFileName() + "<br/>\n");
                out.write("Header Names:");
                for (String name : p.getHeaderNames()) {
                    out.write(" " + name);
                }
                out.write("<br/><br/> \n");
            }
            out.write("xxxxx Parameters:<br/>\n");
            Enumeration<String> pn = request.getParameterNames();
            while (pn.hasMoreElements()) {
                String p = pn.nextElement();
                out.println(p + "=" + request.getParameter(p) + "<br/>");
            }

          out.println("<br/><br/>XXXXXX Saving files:<br/>");  
        for (Part p : request.getParts()) {

            if (p.getName().equals(FILE_PART_NAME)) {
                System.out.println("XXX Setting header");
              
                String fileName = p.getSubmittedFileName();

                  out.println("Saving " + fileName + "<br/>");
                p.write(fileName);
            }
        }
        //TODO: return url in location header
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
