package org.xplorer.controller;

import org.xplorer.model.ConfigurationModel;
import org.xplorer.model.viewer.ViewerType;
import org.xplorer.view.ConfigurationView;

import java.util.List;
import java.util.Map;

public class ConfigurationController {
    private ConfigurationModel model;
    private ConfigurationView view;

    public ConfigurationController(ConfigurationModel model, ConfigurationView view) {
        this.model = model;
        this.view = view;


        initViewListeners();

        initializeViewFromModel();
    }

    private void initViewListeners() {
        view.addFavoriteActionListener(e -> addFavorite());

        view.saveAssociationActionListener(e -> saveAssociation());
    }

    private void initializeViewFromModel() {

        List<String> favorites = model.getFavorites();
        for (String favorite : favorites) {
            view.addFavoriteToList(favorite);
        }


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

    private void saveAssociation() {
        String extension = view.getFileExtension();
        String viewerTypeStr = view.getSelectedViewerType();
        ViewerType viewerType = ViewerType.valueOf(viewerTypeStr.toUpperCase());

        if (extension != null && !extension.trim().isEmpty()) {
            Map<String, ViewerType> viewerMappings = model.getViewerMappings();
            viewerMappings.put(extension, viewerType);
            model.setViewerMappings(viewerMappings);


            view.clearInputFields();
        }
    }
}
