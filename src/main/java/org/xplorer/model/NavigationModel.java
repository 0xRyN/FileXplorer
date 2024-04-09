package org.xplorer.model;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NavigationModel {
    private File currentDirectory;

    public NavigationModel() {
        this.currentDirectory = new File(System.getProperty("user.home"));
    }

    public List<String> listDirectoryContents(String path) {
        currentDirectory = new File(path);
        File[] files = currentDirectory.listFiles();
        if (files != null) {
            return Arrays.stream(files)
                    .map(File::getName)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    public String getCurrentPath() {
        return currentDirectory.getPath();
    }

    public void setCurrentPath(String path) {
        this.currentDirectory = new File(path);
    }
}
