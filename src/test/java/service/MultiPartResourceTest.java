/*
 */
package service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author caikovsk
 */
public class MultiPartResourceTest {

    MultiPartResource i = new MultiPartResource();

    @Test
    public void testgetFileName() {
        MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();
        List<String> l = new LinkedList<>();
        List<MediaType> currentMimeType = new LinkedList<>();
        currentMimeType.add(new MediaType("image", "jpeg"));
        l.add("form-data; name=\"photo\"; filename=\"chAR\""); // javascript sends files without extension
        headers.put(HttpHeaders.CONTENT_DISPOSITION, l);
        InputPart part = new InputPart() {
            @Override
            public MultivaluedMap<String, String> getHeaders() {
                return headers;
            }

            @Override
            public String getBodyAsString() throws IOException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public <T> T getBody(Class<T> type, Type genericType) throws IOException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public <T> T getBody(GenericType<T> type) throws IOException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public MediaType getMediaType() {
                return currentMimeType.get(0);
            }

            @Override
            public boolean isContentTypeFromMessage() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void setMediaType(MediaType mediaType) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        assertEquals("chAR", i.getFileName(part));
        assertEquals("jpeg", i.getExtensionFromMime(part.getMediaType()));
        String newFileName = i.getUniqueFileNameWithInferedExtension(part);
        assertTrue(newFileName.matches("^chAR\\d+\\.jpeg$"));
        //
        l.set(0, "form-data; name=\"photo\"; filename=\"G.EN\"");
        assertEquals("G.EN", i.getFileName(part));
        assertEquals("jpeg", i.getExtensionFromMime(part.getMediaType()));
        newFileName = i.getUniqueFileNameWithInferedExtension(part);
        assertTrue(newFileName.matches("^G.EN\\d+\\.jpeg$"));
//
        currentMimeType.set(0, new MediaType("image", "png"));
        l.set(0, "form-data; name=\"photo\"; filename=\"rZ2O.7Q.19om\"");
        assertEquals("rZ2O.7Q.19om", i.getFileName(part));
        assertEquals("png", i.getExtensionFromMime(part.getMediaType()));
        newFileName = i.getUniqueFileNameWithInferedExtension(part);
        assertTrue(newFileName.matches("^rZ2O.7Q.19om\\d+\\.png$"));
    }

    @Test
    public void testGetExtensionFromMime() {
        assertEquals(i.getExtensionFromMime(new MediaType("image", "jpeg")), "jpeg");
        assertEquals(i.getExtensionFromMime(new MediaType("image", "png")), "png");
    }
}
