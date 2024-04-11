package org.xplorer.view;

import org.xplorer.model.viewer.ViewerType;
import org.xplorer.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ConfigurationView extends JPanel {
    private JButton addButton, removeFavoriteButton, saveButton, removeAssociationButton;
    private JTextField newFavoriteField, fileExtensionField;
    private JComboBox<ViewerType> viewerTypeComboBox;
    private DefaultListModel<String> favoritesListModel, associationsListModel;
    private JList<String> favoritesList, associationsList;

    public ConfigurationView() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());


        favoritesListModel = new DefaultListModel<>();
        favoritesList = new JList<>(favoritesListModel);
        JScrollPane favoritesScrollPane = new JScrollPane(favoritesList);


        associationsListModel = new DefaultListModel<>();
        associationsList = new JList<>(associationsListModel);
        JScrollPane associationsScrollPane = new JScrollPane(associationsList);


        JSplitPane listsSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                favoritesScrollPane, associationsScrollPane);
        add(listsSplitPane, BorderLayout.CENTER);


        JPanel controlPanel = new JPanel(new GridLayout(4, 2));
        newFavoriteField = new JTextField();
        addButton = new JButton("Add Favorite");
        removeFavoriteButton = new JButton("Remove Favorite");
        fileExtensionField = new JTextField();
        viewerTypeComboBox = new JComboBox<>(ViewerType.values());
        saveButton = new JButton("Save Association");
        removeAssociationButton = new JButton("Remove Association");

        controlPanel.add(new JLabel("New Favorite:"));
        controlPanel.add(newFavoriteField);
        controlPanel.add(addButton);
        controlPanel.add(removeFavoriteButton);
        controlPanel.add(new JLabel("File Extension:"));
        controlPanel.add(fileExtensionField);
        controlPanel.add(viewerTypeComboBox);
        controlPanel.add(saveButton);
        controlPanel.add(removeAssociationButton);

        add(controlPanel, BorderLayout.SOUTH);
    }

    public void addFavoriteActionListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void removeFavoriteActionListener(ActionListener listener) {
        removeFavoriteButton.addActionListener(listener);
    }

    public void saveAssociationActionListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }

    public void removeAssociationActionListener(ActionListener listener) {
        removeAssociationButton.addActionListener(listener);
    }

    public String getNewFavorite() {
        return newFavoriteField.getText();
    }

    public String getFileExtension() {
        return fileExtensionField.getText();
    }

    public ViewerType getSelectedViewerType() {
        return (ViewerType) viewerTypeComboBox.getSelectedItem();
    }

    public void addFavoriteToList(String favorite) {
        favoritesListModel.addElement(favorite);
    }

    public void removeFavoriteFromList(String favorite) {
        favoritesListModel.removeElement(favorite);
    }

    public void addAssociationToList(String extension, ViewerType viewerType) {
        associationsListModel.addElement(extension + " - " + viewerType.name());
    }

    public void removeAssociationFromList(String extension, ViewerType association) {
        associationsListModel.removeElement(extension + " - " + association.name());
    }

    public String getSelectedFavorite() {
        return favoritesList.getSelectedValue();
    }

    public Pair<String, ViewerType> getSelectedAssociation() {
        String selectedAssociation = associationsList.getSelectedValue();
        if (selectedAssociation != null) {
            String[] parts = selectedAssociation.split(" - ");
            return new Pair<>(parts[0], ViewerType.valueOf(parts[1]));
        }
        return null;
    }

    public void clearInputFields() {
        newFavoriteField.setText("");
        fileExtensionField.setText("");
        viewerTypeComboBox.setSelectedIndex(0);
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}