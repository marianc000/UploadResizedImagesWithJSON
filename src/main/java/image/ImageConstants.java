package image;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageConstants {

// note,ends in path separator
    // an absolute path where to save uploaded files must be specified somewhere explicitly, one cannot use relative paths when deployed into a server
    public static final String FILE_STORAGE_LOCATION = "C:\\Users\\caikovsk\\Documents\\NetBeans\\ImageResizeAndUploadWildflyBackbone\\src\\main\\webapp\\images",
            FORM_DATA_PART_NAME = "dataObject",
            FILE_PART_NAME = "photo",
            IMAGE_SERVLET_PATH = "image/";
    public static final Path FILES_STORAGE_PATH = Paths.get(FILE_STORAGE_LOCATION);
    public static int MAX_THUMB_SIZE = 150;
}
