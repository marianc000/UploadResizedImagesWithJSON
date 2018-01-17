package resize;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import net.coobird.thumbnailator.Thumbnails;

public class SaveThumb {

    public static String THUMB_ENDING = "_thumb";
    static int maxSize = 150;

    public static void main(String[] args) throws IOException {

        String filePath = "C:\\Users\\caikovsk\\Pictures\\GEN.JPG";

        long s = System.currentTimeMillis();
        new SaveThumb().createThumbnail(Paths.get(filePath), maxSize);
        System.out.println(System.currentTimeMillis() - s);
    }

    public String createThumbnail(Path p, int maxSize) throws IOException {
        Path outputPath = getThumbPath(p, THUMB_ENDING);
        Thumbnails.of(p.toString())
                .size(maxSize, maxSize)
                .outputFormat("jpg")
                .toFile(outputPath.toString());
        return outputPath.getFileName().toString();
    }

    Path getThumbPath(Path p, String suffix) {
        String fileName = p.getFileName().toString();
        int lastDot = fileName.lastIndexOf(".");
        String ext = fileName.substring(lastDot);
        String newFileName = fileName.substring(0, lastDot) + suffix + ext;
        return p.getParent().resolve(newFileName);
    }

}
