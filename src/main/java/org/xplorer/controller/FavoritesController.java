package org.xplorer.controller;

import org.xplorer.interfaces.ConfigurationObserver;
import org.xplorer.interfaces.FavoriteSelectionListener;
import org.xplorer.model.FavoritesModel;
import org.xplorer.util.config.Configuration;
import org.xplorer.util.config.ConfigurationManager;
import org.xplorer.view.FavoritesView;

public class FavoritesController implements ConfigurationObserver {
    private final FavoritesModel model;
    private final FavoritesView view;
    private final FavoriteSelectionListener selectionListener;

    public FavoritesController(FavoritesModel model, FavoritesView view, FavoriteSelectionListener selectionListener) {
        this.model = model;
        this.view = view;
        this.selectionListener = selectionListener;

        view.setFavoritesList(model.getFavorites());
        initController();
    }

    private void initController() {
        view.getFavoritesList().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedPath = view.getFavoritesList().getSelectedValue();
                selectionListener.onFavoriteSelected(selectedPath);
            }
        });

        ConfigurationManager.addObserver(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfiguration) {
        model.setFavorites(newConfiguration.getFavorites());
        view.setFavoritesList(model.getFavorites());
    }
}