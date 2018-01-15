/*
 */
package image;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.IlllustratedData;
import static image.ImageConstants.FILE_STORAGE_LOCATION;
import static image.ImageConstants.FORM_DATA_PART_NAME;
import static image.ImageServlet.IMAGE_SERVLET_PATH;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import service.MyObjectMapperFactory;

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
        for (Part p : request.getParts()) {

            System.out.println(">Part: " + p.toString());
            System.out.println("Part name: " + p.getName());
            System.out.println("Size: " + p.getSize());
            System.out.println("Content Type: " + p.getContentType());
            System.out.println("File name: " + p.getSubmittedFileName());
            System.out.println("Header Names:");
            for (String name : p.getHeaderNames()) {
                System.out.println(name + " " + p.getHeader(name));
            }
        }
        System.out.println("xxxxx Parameters:<br/>\n");
        Enumeration<String> pn = request.getParameterNames();
        while (pn.hasMoreElements()) {
            String p = pn.nextElement();
            System.out.println(p + "=" + request.getParameter(p) + "<br/>");
        }

//           out.println("<br/><br/>XXXXXX Saving files:<br/>");  
        List<String> urls = new LinkedList<>();
        for (Part p : request.getParts()) {

            if (p.getName().equals(FILE_PART_NAME)) {

                String fileName = p.getSubmittedFileName();
                System.out.println("XXX Saving " + fileName);
                urls.add(getImageUrl(fileName));
                //    response.setHeader("Location", IMAGE_SERVLET_PATH + fileName);
                //  out.println("Saving " + fileName + "<br/>");
                p.write(fileName);
            }
        }
        String dataPart = request.getParameter(FORM_DATA_PART_NAME);
        System.out.println("dataPart=" + dataPart);
        ObjectMapper mapper = MyObjectMapperFactory.getObjectMapper(); // create once, reuse
        IlllustratedData data = mapper.readValue(dataPart, IlllustratedData.class);
        data.setPhotos(urls);
        System.out.println("dataPart=" + data);
        //TODO: return url in location header
        //    String responseStr = "{\"photos\":" + urls.toString() + "}";
        String responseStr = mapper.writeValueAsString(data);
        System.out.println("responseStr=" + responseStr);
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.write(responseStr);
        }
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
