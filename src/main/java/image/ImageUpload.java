/*
 */
package image;

import static image.ImageConstants.FILE_STORAGE_LOCATION;
import static image.ImageServlet.IMAGE_SERVLET_PATH;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
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
@WebServlet(name = "ImageUpload", urlPatterns = {"/upload"})
@MultipartConfig(location = FILE_STORAGE_LOCATION)
public class ImageUpload extends HttpServlet {

    public String FILE_PART_NAME = "photo";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //   response.setContentType("text/html;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_CREATED);
        //   response.setHeader("Location", IMAGE_SERVLET_PATH + "XXXXXXXXXXXXXXXXXX");
        //   response.setHeader("Location", url);
        // try (PrintWriter out = response.getWriter()) {
//            for (Part p : request.getParts()) {
//
//                /* out.write("Part: " + p.toString() + "<br/>\n"); */
//                out.write("Part name: " + p.getName() + "<br/>\n");
//                out.write("Size: " + p.getSize() + "<br/>\n");
//                out.write("Content Type: " + p.getContentType() + "<br/>\n");
//                out.write("File name: " + p.getSubmittedFileName() + "<br/>\n");
//                out.write("Header Names:");
//                for (String name : p.getHeaderNames()) {
//                    out.write(" " + name);
//                }
//                out.write("<br/><br/> \n");
//            }
//            out.write("xxxxx Parameters:<br/>\n");
//            Enumeration<String> pn = request.getParameterNames();
//            while (pn.hasMoreElements()) {
//                String p = pn.nextElement();
//                out.println(p + "=" + request.getParameter(p) + "<br/>");
//            }

//           out.println("<br/><br/>XXXXXX Saving files:<br/>");  
        List<String> urls = new LinkedList<>();
        for (Part p : request.getParts()) {

            if (p.getName().equals(FILE_PART_NAME)) {

                String fileName = p.getSubmittedFileName();
                System.out.println("XXX Saving " + fileName);
                urls.add(quote(getImageUrl(fileName)));
                //    response.setHeader("Location", IMAGE_SERVLET_PATH + fileName);
                //  out.println("Saving " + fileName + "<br/>");
                p.write(fileName);
            }
        }
        //TODO: return url in location header
        String responseStr = "{\"photos\":" + urls.toString() + "}";
        System.out.println("responseStr=" + responseStr);
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.write(responseStr);
        }
    }

    String quote(String str) {
        return '"' + str + '"';
    }

    String getImageUrl(String fileName) {
        return IMAGE_SERVLET_PATH + fileName;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
