/*
 */
package service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static service.MultiPartResource.CONTENT_DISPOSITION_HEADER;

/**
 *
 * @author caikovsk
 */
public class MultiPartResourceTest {

    public MultiPartResourceTest() {
    }
    MultiPartResource i = new MultiPartResource();

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testgetFileName() {
        MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();
        List<String> l = new LinkedList<>();
        l.add("form-data; name=\"photo\"; filename=\"chAR.jpg\"");
        headers.put(CONTENT_DISPOSITION_HEADER, l);
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
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        assertEquals("chAR.jpg", i.getFileName(part));
        l.set(0, "form-data; name=\"photo\"; filename=\"GEN.jpg\"");
        assertEquals("GEN.jpg", i.getFileName(part));
        l.set(0, "form-data; name=\"photo\"; filename=\"rZ2O7Q19om.jpg\"");
        assertEquals("rZ2O7Q19om.jpg", i.getFileName(part));
    }

}
