package org.xplorer.model;

import org.xplorer.model.viewer.FileViewer;
import org.xplorer.model.viewer.ViewerType;
import org.xplorer.util.config.ConfigurationManager;

import java.io.File;
import java.util.Map;

public class ViewerModel {
    private FileViewer viewer;

    public void setViewer(FileViewer viewer) {
        this.viewer = viewer;
    }

    public void displayFile(File file) {
        if (viewer != null) {
            viewer.display(file);
        } else {

        }
    }

    public ViewerType getViewerType(String path) {
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("File : " + file.getAbsolutePath() + " does not exist");
            return ViewerType.DEFAULT;
        }
        if (!file.isFile()) {
            return ViewerType.DEFAULT;
        }

        String fileName = file.getName();
        String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
        System.out.println("Extension: " + extension);
        Map<String, ViewerType> mappings = ConfigurationManager.loadConfiguration().getViewerMappings();
        System.out.println("Mappings: " + mappings);

        return mappings.getOrDefault(extension, ViewerType.DEFAULT);
    }
}
