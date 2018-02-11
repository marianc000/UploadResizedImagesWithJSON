/*
 */
package resize;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import static resize.SaveThumb.THUMB_ENDING;
import utils.FileUtils;

/**
 *
 * @author caikovsk
 */
public class SaveThumbTest {

    SaveThumb i = new SaveThumb();
    static String TEST_RESOURCES_FOLDER = "src\\test\\resources";

    Path TEST_RESOURCE_DIR = Paths.get("").toAbsolutePath().resolve(TEST_RESOURCES_FOLDER);

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    Set<Path> getThumbFiles() throws IOException {
        return getFiles("*" + THUMB_ENDING + ".*");
    }

    Set<Path> getFiles(String glob) throws IOException {
        Set<Path> set = new HashSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(TEST_RESOURCE_DIR, glob)) {
            for (Path file : stream) {
                set.add(file);
            }
        }
        return set;
    }

    Set<Path> getAllFiles() throws IOException {
        return getFiles("*");
    }

    void cleanThumbs() throws IOException {
        for (Path p : getThumbFiles()) {
            Files.delete(p);
        }
    }

    String getExtension(Path p) {
        String fileName = p.getFileName().toString();
        return fileName.substring(0, fileName.indexOf("."));
    }

    @Test
    public void testCreateThumbnailExtensionRemainsUnchanged() throws IOException {
        cleanThumbs();
        assertTrue(getThumbFiles().isEmpty());
        Set<Path> set = getAllFiles();
        assertFalse(set.isEmpty());
        for (Path p : set) {
            i.createThumbnail(p, SaveThumb.maxSize);
        }
        Set<Path> thumbs = getThumbFiles();
        assertEquals(thumbs.size(), set.size());
        for (Path p : set) {
            String extension = FileUtils.getExtension(p);
            Path expectedThumb = p.getParent().resolve(p.getFileName().toString().replace(extension, THUMB_ENDING + extension));
            assertTrue(thumbs.contains(expectedThumb));
        }
    }

    @Test
    public void testGetThumbPath() {
        String fileName = "C:\\Users\\caikovsk\\Pictures\\Gran Canaria\\DSC02898.JPG";
        String ext = ".JPG";
        Path thumbPath = i.getThumbPath(Paths.get(fileName), THUMB_ENDING);
        System.out.println(thumbPath);
        assertEquals(thumbPath, Paths.get(fileName.replace(ext, THUMB_ENDING + ext)));

        fileName = "C:\\Users\\caikovsk\\Pictures\\Gran Canaria\\DS.C02.898.JPG";
        thumbPath = i.getThumbPath(Paths.get(fileName), THUMB_ENDING);
        System.out.println(thumbPath);
        assertEquals(thumbPath, Paths.get(fileName.replace(ext, THUMB_ENDING + ext)));
        fileName = "C:\\Users\\caikovsk\\Pictures\\Capture.PNG";
        ext = ".PNG";
        thumbPath = i.getThumbPath(Paths.get(fileName), THUMB_ENDING);
        System.out.println(thumbPath);
        assertEquals(thumbPath, Paths.get(fileName.replace(ext, THUMB_ENDING + ext)));
    }

}
