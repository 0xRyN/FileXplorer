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

    public boolean renameFile(String path, String newPath) {
        System.out.println("Renaming file: " + path + " to " + newPath);
        File file = new File(path);
        File newFile = new File(newPath);
        boolean success = file.renameTo(newFile);
        if (!success) {
            System.out.println("Failed to rename file");
        }

        currentDirectory = newFile;

        System.out.println("New path: " + currentDirectory.getPath());

        return success;
    }

    public boolean deleteFile(String path) {
        System.out.println("Deleting file: " + path);
        // Deleting moves the file to HOME/.xplorer/trash
        File file = new File(path);
        File trash = new File(System.getProperty("user.home") + File.separator + ".xplorer" + File.separator + "trash");
        if (!trash.exists()) {
            trash.mkdirs();
        }
        File newFile = new File(trash.getPath() + File.separator + file.getName());
        return file.renameTo(newFile);
    }

    public boolean changePermissions(String path, String permissions) {
        System.out.println("Changing permissions for file: " + path + " to " + permissions);
        if (permissions.length() != 3) {
            System.out.println("Invalid permissions - Example : (rwx) or (rw-) or (r-x)");
            return false;
        }
        File file = new File(path);
        return file.setReadable(permissions.charAt(0) == 'r') && file.setWritable(permissions.charAt(1) == 'w') && file.setExecutable(permissions.charAt(2) == 'x');
    }
}
