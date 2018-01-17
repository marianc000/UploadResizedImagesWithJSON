/*
 */
package resize;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static resize.SaveThumb.THUMB_ENDING;

/**
 *
 * @author caikovsk
 */
public class SaveThumbTest {

    SaveThumb i = new SaveThumb();

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
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
    }

}
