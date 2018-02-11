package resize;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import net.coobird.thumbnailator.Thumbnails;
import utils.FileUtils;

public class SaveThumb {

    public static String THUMB_ENDING = "_thumb";
    static int maxSize = 150;

    public static void main(String[] args) throws IOException {

        String filePath = "C:\\Users\\caikovsk\\Pictures\\GEN.JPG";
        long s = System.currentTimeMillis();
        new SaveThumb().createThumbnail(Paths.get(filePath), maxSize);
        System.out.println(System.currentTimeMillis() - s);
    }
    String[] thumbFormats = {"jpg", "bmp", "gif", "png"};

    public String createThumbnail(Path p, int maxSize) throws IOException {
        System.out.println("p: " + p);
        Path outputPath = getThumbPath(p, THUMB_ENDING);
        System.out.println("saving thumb to: " + outputPath);
        Thumbnails.of(p.toString())
                .size(maxSize, maxSize)
                //   .outputFormat("jpg") //JPG, jpg, bmp, BMP, gif, GIF, WBMP, png, PNG, wbmp, jpeg, JPEG
                .toFile(outputPath.toString());
        return outputPath.getFileName().toString();
    }

    Path getThumbPath(Path p, String suffix) {
        String newFileName = FileUtils.getFileNameWithoutExtension(p)+ suffix + FileUtils.getExtension(p);
        return p.getParent().resolve(newFileName);
    }

}
