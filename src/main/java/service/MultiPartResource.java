package service;

import entity.IlllustratedData;
import static image.ImageConstants.FILE_STORAGE_LOCATION;
import static image.ImageConstants.FORM_DATA_PART_NAME;
import static image.ImageUpload.FILE_PART_NAME;
import static image.ImageUpload.getImageUrl;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
 
@Path("/upload")
public class MultiPartResource {

    java.nio.file.Path FILES_STORAGE_PATH = Paths.get(FILE_STORAGE_LOCATION);

    @GET
    @Produces("text/plain")
    public String getHello() {
        return "Hello World Form!";
    }

    @POST
    //  @Path("part")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public IlllustratedData post(MultipartFormDataInput input) throws IOException {
        Map<String, List<InputPart>> map = input.getFormDataMap();
        InputPart dataPartPart = map.get(FORM_DATA_PART_NAME).get(0);
        dataPartPart.setMediaType(MediaType.APPLICATION_JSON_TYPE);
        System.out.println(dataPartPart.getBodyAsString() + "; " + dataPartPart.getMediaType());
        IlllustratedData dataPart = dataPartPart.getBody(new GenericType<IlllustratedData>() {
        });
        System.out.println(dataPart);
        List<String> urls = new LinkedList<>();
        List<InputPart> filesParts = map.get(FILE_PART_NAME);
        for (InputPart part : filesParts) {

            InputStream is = part.getBody(InputStream.class, null);
            System.out.println(is);
            String fileName = getFileName(part);
            urls.add(getImageUrl(fileName));
            java.nio.file.Path filePath = FILES_STORAGE_PATH.resolve(fileName);
            System.out.println(filePath);
            Files.copy(is, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        input.close();

        dataPart.setPhotos(urls);
        return dataPart;
    }
    static String CONTENT_DISPOSITION_HEADER = "Content-Disposition";

    String getFileName(InputPart part) {
        MultivaluedMap<String, String> headers = part.getHeaders();
        for (String key : headers.keySet()) {
            System.out.println(key + " = " + headers.get(key));

        }
        String contentHeader = headers.get(CONTENT_DISPOSITION_HEADER).get(0);
        for (String content : contentHeader.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
