package org.xplorer.controller;

import org.xplorer.interfaces.NavigationFileSelectionListener;
import org.xplorer.interfaces.NavigationFolderSelectionListener;
import org.xplorer.model.NavigationModel;
import org.xplorer.view.NavigationView;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class NavigationController {
    private final NavigationModel model;
    private final NavigationView view;

    private final NavigationFileSelectionListener navigationFileSelectionListener;
    private final NavigationFolderSelectionListener navigationFolderSelectionListener;

    public NavigationController(NavigationModel model, NavigationView view, NavigationFileSelectionListener navigationFileSelectionListener, NavigationFolderSelectionListener navigationFolderSelectionListener) {
        this.model = model;
        this.view = view;
        this.navigationFileSelectionListener = navigationFileSelectionListener;
        this.navigationFolderSelectionListener = navigationFolderSelectionListener;
        initController();
        initializeView();
        initializeContextMenuListeners();
    }

    private void fireSelectionEventIfFileSelected(String path) {
        navigationFileSelectionListener.onNavigationFileSelected(path);
    }

    private void fireSelectionEventIfFolderSelected(String path) {
        navigationFolderSelectionListener.onFolderSelected(path);
    }

    private String getCurrentlySelectedPath(JList<String> list, int index) {
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

        return path;
    }

    private void addListEventListener(JList<String> list, int index) {
        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && !list.isSelectionEmpty()) {
                String path = getCurrentlySelectedPath(list, index);
                File file = new File(path);
                // File is not a directory, don't open it and fire selection event
                if (!file.isDirectory()) {
                    fireSelectionEventIfFileSelected(path);
                    return;
                }

                // File is a directory, open it and fire selection event
                fireSelectionEventIfFolderSelected(path);
                setCurrentPath(index, path);
            }
        });
    }

    private void addContextMenuListener(JList<String> list, int index) {
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showContextMenu(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showContextMenu(e);
                }
            }

            private void showContextMenu(MouseEvent e) {
                view.setCurrentContextDepth(index);
                int index = list.locationToIndex(e.getPoint());
                list.setSelectedIndex(index);
                view.contextMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });

    }

    private void initializeContextMenuListeners() {
        view.setRenameActionListener(e -> handleRenameAction());
        view.setChangePermissionsActionListener(e -> handleChangePermissionsAction());
        view.setDeleteActionListener(e -> handleDeleteAction());
    }

    private void handleRenameAction() {
        JList<String> list = getSelectedList();

        if (list == null || list.isSelectionEmpty()) return;

        String oldPath = getCurrentlySelectedPath(list, view.getCurrentContextDepth());
        String newPath;
        String input;
        File file = new File(oldPath);

        if (file.isDirectory()) {
            input = view.showInputDialog("Entrez le nouveau nom du dossier:", file.getName());
        } else {
            input = view.showInputDialog("Entrez le nouveau nom du fichier:", file.getName());
        }

        newPath = Paths.get(file.getParent(), input).toString();

        model.renameFile(oldPath, newPath);
        refreshView();

    }

    private void handleChangePermissionsAction() {
        JList<String> list = getSelectedList();
        if (list == null || list.isSelectionEmpty()) return;

        String fileName = list.getSelectedValue();
        String path = getCurrentlySelectedPath(list, view.getCurrentContextDepth());
        File file = new File(path);
        if (file.isDirectory()) {
            System.out.println("Cannot change permissions of a directory");
            return;
        }

        String newPermissions = view.showInputDialog("Nouvelles permissions exemples: (r-x), (r--), (--x)" + fileName, "rwx");
        model.changePermissions(path, newPermissions);
    }

    private void handleDeleteAction() {
        JList<String> list = getSelectedList();
        if (list == null || list.isSelectionEmpty()) return;

        boolean confirm = view.showConfirmDialog("Voulez-vous vraiment supprimer ce fichier/dossier?") == JOptionPane.YES_OPTION;
        if (!confirm) return;

        String path = getCurrentlySelectedPath(list, view.getCurrentContextDepth());
        model.deleteFile(path);
        refreshView();
    }

    private JList<String> getSelectedList() {
        int depth = view.getCurrentContextDepth();
        if (depth < 0) return null;
        return view.getListAtIndex(depth);
    }

    private void refreshView() {
        String currentPath = model.getCurrentPath();
        int depth = view.getCurrentDepth();
//        List<String> contents = model.listDirectoryContents(currentPath);
//        view.setDirectoryContents(depth, contents);
//        view.setCurrentPath(currentPath);

        String subPath = currentPath;

        for (int i = depth; i >= 0; i--) {
            List<String> contents = model.listDirectoryContents(subPath);
            view.setDirectoryContents(i, contents);
            subPath = Paths.get(subPath).getParent().toString();
        }
        setCurrentPath(depth - 1, currentPath);

    }

    private void initController() {
        for (int i = 0; i < view.fileLists.size(); i++) {
            JList<String> list = view.getListAtIndex(i);
            addListEventListener(list, i);
            addContextMenuListener(list, i);
        }
    }

    public void setCurrentPath(int listIndex, String path) {
        view.setCurrentDepth(listIndex + 1);
        model.setCurrentPath(path);
        List<String> contents = model.listDirectoryContents(path);
        if (listIndex + 1 >= view.fileLists.size()) {
            JList<String> list = view.addNewList();
            addListEventListener(list, listIndex + 1);
            addContextMenuListener(list, listIndex + 1);
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

    public void addSearchButtonListener(ActionListener listener) {
        view.addShowSearchListener(listener);
    }
}