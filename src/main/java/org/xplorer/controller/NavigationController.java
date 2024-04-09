package org.xplorer.controller;

import org.xplorer.interfaces.NavigationSelectionListener;
import org.xplorer.model.NavigationModel;
import org.xplorer.view.NavigationView;

import javax.swing.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class NavigationController {
    private NavigationModel model;
    private NavigationView view;

    private final NavigationSelectionListener navigationSelectionListener;

    public NavigationController(NavigationModel model, NavigationView view, NavigationSelectionListener navigationSelectionListener) {
        this.model = model;
        this.view = view;
        this.navigationSelectionListener = navigationSelectionListener;
        initController();
        initializeView();
    }

    private void fireSelectionEventIfFileSelected(String path) {
        navigationSelectionListener.onFileSelected(path);
    }

    private void addListEventListener(JList<String> list, int index) {
        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && !list.isSelectionEmpty()) {
                String selectedValue = list.getSelectedValue();
                String path = model.getCurrentPath();
                int currentDepth = view.getCurrentDepth();

                if (currentDepth == index) {
                    path = path + File.separator + selectedValue;
                } else if (currentDepth > index) {
                    Path p = Paths.get(path);
                    for (int j = index; j < currentDepth; j++) {
                        p = p.getParent();
                    }
                    path = Paths.get(p.toString(), selectedValue).toString();
                }

                File file = new File(path);

                // File is not a directory, don't open it and fire selection event
                if (!file.isDirectory()) {
                    fireSelectionEventIfFileSelected(path);
                    return;
                }


                setCurrentPath(index, path);
            }
        });
    }

    private void initController() {
        for (int i = 0; i < view.fileLists.size(); i++) {
            final int index = i;
            JList<String> list = view.getListAtIndex(i);
            addListEventListener(list, index);
        }
    }

    public void setCurrentPath(int listIndex, String path) {
        view.setCurrentDepth(listIndex + 1);
        model.setCurrentPath(path);
        List<String> contents = model.listDirectoryContents(path);
        if (listIndex + 1 >= view.fileLists.size()) {
            JList<String> list = view.addNewList();
            addListEventListener(list, listIndex + 1);
        }

        view.setDirectoryContents(listIndex + 1, contents);
        view.setCurrentPath(model.getCurrentPath());

    }

    public void initializeView() {
        String rootPath = model.getCurrentPath();
        List<String> rootContents = model.listDirectoryContents(rootPath);
        if (!rootContents.isEmpty()) {
            view.setDirectoryContents(0, rootContents);
        }
    }
}