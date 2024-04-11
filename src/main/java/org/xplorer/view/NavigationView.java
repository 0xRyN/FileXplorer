package org.xplorer.view;

import org.xplorer.util.Consts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class NavigationView extends JPanel {
    private static final int LIST_COUNT = 3;
    public List<JList<String>> fileLists;
    private JPanel listsPanel;
    private JTextField currentPathField;
    private JButton showSearch;
    private List<DefaultListModel<String>> listModels;
    private int currentDepth = 0;

    public NavigationView() {
        setLayout(new BorderLayout());
        setBackground(Color.RED);
        initializeTopPanel();
        initializeListsPanel();
        // addDebuggingButton();
    }

    private void initializeTopPanel() {

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        currentPathField = new JTextField();
        currentPathField.setEditable(false);
        topPanel.add(currentPathField);

        topPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        showSearch = new JButton("Search");
        topPanel.add(showSearch);

        this.add(topPanel, BorderLayout.NORTH);
    }


    private void initializeListsPanel() {
        fileLists = new ArrayList<>();
        listModels = new ArrayList<>();
        listsPanel = new JPanel();
        listsPanel.setLayout(new BoxLayout(listsPanel, BoxLayout.X_AXIS));
        add(listsPanel, BorderLayout.CENTER);
        initLists();
    }

    private void addDebuggingButton() {
        JButton button = new JButton("Debug");
        button.addActionListener(e -> clearListsFromIndex(0));
        add(button, BorderLayout.SOUTH);
    }

    public JList<String> addNewList() {
        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> list = new JList<>(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listModels.add(model);
        fileLists.add(list);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(Consts.SCREEN_WIDTH / 10, scrollPane.getHeight()));
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane);

        listsPanel.add(panel);

        // Refresh the view
        revalidate();
        repaint();
        return list;
    }

    public void removeLastList() {
        if (!fileLists.isEmpty()) {
            fileLists.remove(fileLists.size() - 1);
            listModels.remove(listModels.size() - 1);
            listsPanel.remove(listsPanel.getComponentCount() - 1);
            revalidate();
            repaint();
        }
    }

    private void initLists() {
        listsPanel.removeAll();
        fileLists.clear();
        listModels.clear();
        for (int i = 0; i < NavigationView.LIST_COUNT; i++) {
            addNewList();
        }
    }

    public void setDirectoryContents(int index, java.util.List<String> contents) {

        DefaultListModel<String> model = listModels.get(index);
        model.clear();
        for (String item : contents) {
            model.addElement(item);
        }
        clearListsFromIndex(index + 1);
    }

    private void clearListsFromIndex(int startIndex) {
        for (int i = startIndex; i < listModels.size(); i++) {
            listModels.get(i).clear();
        }

        // Remove all lists after the startIndex + LIST_COUNT
        while (listModels.size() > startIndex + LIST_COUNT - 1) {
            removeLastList();
        }
    }

    public JList<String> getListAtIndex(int index) {
        return index < fileLists.size() ? fileLists.get(index) : null;
    }

    public void setCurrentPath(String path) {
        currentPathField.setText(path);
    }

    public int getCurrentDepth() {
        return currentDepth;
    }

    public void setCurrentDepth(int depth) {
        currentDepth = depth;
    }

    public void addShowSearchListener(ActionListener actionListener) {
        showSearch.addActionListener(actionListener);
    }

}
