package edu.wayne.cs.severe.redress2.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class IOUtils {

    public static void createEmptyFolder(String dirPath) throws IOException {
        File directory = new File(dirPath);
        FileUtils.deleteDirectory(directory);
        FileUtils.forceMkdir(directory);
    }

    public static void removeFile(String filePath) {
        File file = new File(filePath);
        file.delete();
    }

}
