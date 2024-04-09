package org.xplorer.controller;

import org.xplorer.interfaces.FavoriteSelectionListener;
import org.xplorer.interfaces.NavigationSelectionListener;
import org.xplorer.model.ExplorerModel;
import org.xplorer.model.FavoritesModel;
import org.xplorer.model.NavigationModel;
import org.xplorer.model.ViewerModel;
import org.xplorer.view.ExplorerView;
import org.xplorer.view.FavoritesView;
import org.xplorer.view.NavigationView;
import org.xplorer.view.ViewerView;

public class ExplorerController implements FavoriteSelectionListener, NavigationSelectionListener {
    private final ExplorerModel model;
    private final ExplorerView view;
    private final FavoritesController favoritesController;
    private final NavigationController navigationController;

    private final ViewerController viewerController;

    public ExplorerController() {
        this.model = new ExplorerModel();
        NavigationModel navigationModel = new NavigationModel();
        NavigationView navigationView = new NavigationView();
        this.navigationController = new NavigationController(navigationModel, navigationView, this);

        FavoritesModel favoritesModel = new FavoritesModel();
        FavoritesView favoritesView = new FavoritesView();
        this.favoritesController = new FavoritesController(favoritesModel, favoritesView, this);

        ViewerModel viewerModel = new ViewerModel();
        ViewerView viewerView = new ViewerView();
        this.viewerController = new ViewerController(viewerModel, viewerView);

        this.view = new ExplorerView();
        this.view.addComponent(favoritesView);
        this.view.addComponent(navigationView);
        this.view.addComponent(viewerView);
    }

    @Override
    public void onFavoriteSelected(String path) {
        navigationController.setCurrentPath(-1, path);
    }

    @Override
    public void onFileSelected(String path) {
        viewerController.updateViewer(path);
    }


    public void showExplorer() {
        view.setVisible(true);
    }
}
