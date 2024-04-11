package org.xplorer.model.viewer;

import org.xplorer.util.FileUtils;
import org.xplorer.view.ViewerView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TextViewer implements FileViewer {
    private final ViewerView view;

    public TextViewer(ViewerView view) {
        this.view = view;
    }

    @Override
    public void display(File file) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            view.displayText(content.toString(), FileUtils.getFileInfo(file));
        } catch (IOException e) {
            e.printStackTrace();
            view.displayText("Error while reading file.", FileUtils.getFileInfo(file));
        }
    }
}