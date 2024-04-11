package org.xplorer.controller;

import org.xplorer.model.ConfigurationModel;
import org.xplorer.model.viewer.ViewerType;
import org.xplorer.util.Pair;
import org.xplorer.view.ConfigurationView;

import java.util.List;
import java.util.Map;

public class ConfigurationController {
    private final ConfigurationModel model;
    private final ConfigurationView view;

    public ConfigurationController(ConfigurationModel model, ConfigurationView view) {
        this.model = model;
        this.view = view;

        initViewListeners();
        initializeViewFromModel();
    }

    private void initViewListeners() {
        view.addFavoriteActionListener(e -> addFavorite());
        view.removeFavoriteActionListener(e -> removeFavorite());

        view.saveAssociationActionListener(e -> saveAssociation());
        view.removeAssociationActionListener(e -> removeAssociation());
    }

    private void initializeViewFromModel() {
        List<String> favorites = model.getFavorites();
        for (String favorite : favorites) {
            view.addFavoriteToList(favorite);
        }

        Map<String, ViewerType> associations = model.getViewerMappings();
        associations.forEach((extension, viewerType) -> view.addAssociationToList(extension, viewerType));
    }

    private void addFavorite() {
        String newFavorite = view.getNewFavorite();
        if (newFavorite != null && !newFavorite.trim().isEmpty()) {
            List<String> favorites = model.getFavorites();
            if (!favorites.contains(newFavorite)) {
                favorites.add(newFavorite);
                model.setFavorites(favorites);
                view.addFavoriteToList(newFavorite);
                view.clearInputFields();
            }
        }
    }

    private void removeFavorite() {
        String favoriteToRemove = view.getSelectedFavorite();
        if (favoriteToRemove != null && !favoriteToRemove.trim().isEmpty()) {
            List<String> favorites = model.getFavorites();
            if (favorites.contains(favoriteToRemove)) {
                favorites.remove(favoriteToRemove);
                model.setFavorites(favorites);
                view.removeFavoriteFromList(favoriteToRemove);
            } else {
                view.showErrorMessage("Favorite not found.");
            }
        }
    }

    private void saveAssociation() {
        String extension = view.getFileExtension();
        ViewerType viewerType = view.getSelectedViewerType();

        if (extension != null && !extension.trim().isEmpty()) {
            Map<String, ViewerType> viewerMappings = model.getViewerMappings();
            viewerMappings.put(extension, viewerType);
            model.setViewerMappings(viewerMappings);
            view.addAssociationToList(extension, viewerType);
            view.clearInputFields();
        }
    }

    private void removeAssociation() {
        Pair<String, ViewerType> selectedAssociation = view.getSelectedAssociation();
        String extensionToRemove = selectedAssociation.first();
        if (extensionToRemove != null && !extensionToRemove.trim().isEmpty()) {
            Map<String, ViewerType> viewerMappings = model.getViewerMappings();
            if (viewerMappings.containsKey(extensionToRemove)) {
                viewerMappings.remove(extensionToRemove);
                model.setViewerMappings(viewerMappings);
                view.removeAssociationFromList(selectedAssociation.first(), selectedAssociation.second());
            } else {
                view.showErrorMessage("Association not found.");
            }
        }
    }
}