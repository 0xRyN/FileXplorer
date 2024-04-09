package org.xplorer.controller;

import org.xplorer.model.ViewerModel;
import org.xplorer.model.viewer.DefaultViewer;
import org.xplorer.model.viewer.ImageViewer;
import org.xplorer.model.viewer.TextViewer;
import org.xplorer.model.viewer.ViewerType;
import org.xplorer.view.ViewerView;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ViewerController {
    private ViewerModel model;
    private ViewerView view;

    private File currentFile;

    public ViewerController(ViewerModel model, ViewerView view) {
        this.model = model;
        this.view = view;
        addOpenButtonListener();
    }

    private void addOpenButtonListener() {
        view.addOpenFileButtonListener(e -> {
            if (this.currentFile != null) {
                try {
                    Desktop.getDesktop().open(this.currentFile);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void updateViewer(String path) {
        File file = new File(path);
        this.currentFile = file;
        ViewerType viewerType = model.getViewerType(path);
        System.out.println(viewerType + " for file: " + path);
        switch (viewerType) {
            case IMAGE_VIEWER:
                model.setViewer(new ImageViewer(view));
                break;
            case TEXT_VIEWER:
                model.setViewer(new TextViewer(view));
                break;
            default:
                model.setViewer(new DefaultViewer(view));
                break;
        }
        model.displayFile(file);
    }
}

