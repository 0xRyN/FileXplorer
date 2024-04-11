package org.xplorer.model.viewer;

import org.xplorer.view.ViewerView;

import java.io.File;

public class DefaultViewer implements FileViewer {
    private final ViewerView view;

    public DefaultViewer(ViewerView view) {
        this.view = view;
    }

    @Override
    public void display(File file) {
        String fileInfo = String.format("Nom: %s\nTaille: %d octets\nDernière modification: %tc",
                file.getName(), file.length(), file.lastModified());
        view.displayDefault(fileInfo);
    }
}