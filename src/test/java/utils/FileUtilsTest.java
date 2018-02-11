/*
 */
package utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author caikovsk
 */
public class FileUtilsTest {

    public FileUtilsTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetExtension() {
        Path p = Paths.get("C:\\Users\\caikovsk\\Documents\\NetBeans\\ImageResizeAndUploadWildfly\\src\\test\\resources\\Ca.ptu.re3.PNG");
        assertEquals(FileUtils.getExtension(p), ".PNG");
        assertEquals(FileUtils.getFileNameWithoutExtension(p), "Ca.ptu.re3");
        p = Paths.get("C:\\Users\\caikovsk\\Documents\\NetBeans\\ImageResizeAndUploadWildfly\\src\\test\\resources\\2017012.1_123130.jpg");
        assertEquals(FileUtils.getExtension(p), ".jpg");
        assertEquals(FileUtils.getFileNameWithoutExtension(p), "2017012.1_123130");
    }
}
