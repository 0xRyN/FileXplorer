package org.xplorer.model.viewer;

import org.xplorer.util.FileUtils;
import org.xplorer.view.ViewerView;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ImageViewer implements FileViewer {
    private ViewerView view;

    public ImageViewer(ViewerView view) {
        this.view = view;
    }

    @Override
    public void display(File file) {
        ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());
        Image image = imageIcon.getImage().getScaledInstance(view.getWidth(), view.getHeight(), Image.SCALE_SMOOTH);
        view.displayImage(new ImageIcon(image), FileUtils.getFileInfo(file));
    }
}