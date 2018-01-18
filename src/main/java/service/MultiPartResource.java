package service;

import entity.IlllustratedData;
import entity.Photo;
import static image.ImageConstants.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
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
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import resize.SaveThumb;

@Path("/upload")
public class MultiPartResource {

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
        dataPartPart.setMediaType(MediaType.APPLICATION_JSON_TYPE); // to parse to java
        IlllustratedData dataPart = dataPartPart.getBody(new GenericType<IlllustratedData>() {
        });
        List<Photo> urls = new LinkedList<>();
        List<InputPart> filesParts = map.get(FILE_PART_NAME);
        if (filesParts!=null) {
            for (InputPart part : filesParts) {
                InputStream is = part.getBody(InputStream.class, null);
                String fileName = getFileName(part);
                java.nio.file.Path filePath = FILES_STORAGE_PATH.resolve(fileName);
                Files.copy(is, filePath, StandardCopyOption.REPLACE_EXISTING);
                String thumbFileName = new SaveThumb().createThumbnail(filePath, MAX_THUMB_SIZE);
                urls.add(new Photo(fileName, thumbFileName));
            }
        }
        input.close();
        dataPart.setPhotos(urls);
        return dataPart;
    }

    String getFileName(InputPart part) {
        MultivaluedMap<String, String> headers = part.getHeaders();
        String contentHeader = headers.get(HttpHeaders.CONTENT_DISPOSITION).get(0);
        for (String content : contentHeader.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
