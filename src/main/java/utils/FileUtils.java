package utils;

import java.nio.file.Path;

public class FileUtils {

    public static String getFileNameWithoutExtension(Path p) {
        String fileName = p.getFileName().toString();
        return fileName.substring(0, fileName.lastIndexOf(".")); 
    }

    public static String getExtension(Path p) {
        String fileName = p.getFileName().toString();
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
