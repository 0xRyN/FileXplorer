package org.xplorer.util;

import java.io.File;

public class FileUtils {

    public static String getFileInfo(File file) {
        return String.format("Nom: %s\nTaille: %d octets\nDernière modification: %tc",
                file.getName(), file.length(), file.lastModified());
    }
}
