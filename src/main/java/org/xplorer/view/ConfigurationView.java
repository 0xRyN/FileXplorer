package org.xplorer.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationView extends JPanel {
    private JButton addButton;
    private JButton saveButton;
    private JTextField newFavoriteField;
    private JComboBox<String> viewerTypeComboBox;
    private JTextField fileExtensionField;
    private DefaultListModel<String> favoritesListModel;


    private Map<String, ActionListener> actionListeners;

    public ConfigurationView() {
        this.actionListeners = new HashMap<>();
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());


        favoritesListModel = new DefaultListModel<>();
        JList<String> favoritesList = new JList<>(favoritesListModel);
        JScrollPane scrollPane = new JScrollPane(favoritesList);
        add(scrollPane, BorderLayout.CENTER);


        JPanel controlPanel = new JPanel(new GridLayout(3, 2));

        newFavoriteField = new JTextField();
        addButton = new JButton("Add Favorite");
        fileExtensionField = new JTextField();
        viewerTypeComboBox = new JComboBox<>(new String[]{"TEXT_VIEWER", "IMAGE_VIEWER"});
        saveButton = new JButton("Save Association");

        controlPanel.add(new JLabel("New Favorite:"));
        controlPanel.add(newFavoriteField);
        controlPanel.add(addButton);
        controlPanel.add(new JLabel("File Extension:"));
        controlPanel.add(fileExtensionField);
        controlPanel.add(viewerTypeComboBox);
        controlPanel.add(saveButton);

        add(controlPanel, BorderLayout.SOUTH);
    }

    public void addFavoriteActionListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void saveAssociationActionListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }

    public String getNewFavorite() {
        return newFavoriteField.getText();
    }

    public String getFileExtension() {
        return fileExtensionField.getText();
    }

    public String getSelectedViewerType() {
        return (String) viewerTypeComboBox.getSelectedItem();
    }

    public void addFavoriteToList(String favorite) {
        favoritesListModel.addElement(favorite);
    }


    public void clearInputFields() {
        newFavoriteField.setText("");
        fileExtensionField.setText("");
        viewerTypeComboBox.setSelectedIndex(0);
    }
}
