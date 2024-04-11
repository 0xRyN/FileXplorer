package org.xplorer.controller;

import org.xplorer.interfaces.FavoriteSelectionListener;
import org.xplorer.interfaces.NavigationFileSelectionListener;
import org.xplorer.interfaces.NavigationFolderSelectionListener;
import org.xplorer.interfaces.SearchFileSelectionListener;
import org.xplorer.model.*;
import org.xplorer.view.*;

public class ExplorerController implements FavoriteSelectionListener, NavigationFileSelectionListener, NavigationFolderSelectionListener, SearchFileSelectionListener {
    private final ExplorerView view;
    private final NavigationController navigationController;
    private final ViewerController viewerController;
    private final SearchController searchController;

    public ExplorerController() {
        ExplorerModel model = new ExplorerModel();
        NavigationModel navigationModel = new NavigationModel();
        NavigationView navigationView = new NavigationView();
        this.navigationController = new NavigationController(navigationModel, navigationView, this, this);

        FavoritesModel favoritesModel = new FavoritesModel();
        FavoritesView favoritesView = new FavoritesView();
        FavoritesController favoritesController = new FavoritesController(favoritesModel, favoritesView, this);

        ViewerModel viewerModel = new ViewerModel();
        ViewerView viewerView = new ViewerView();
        this.viewerController = new ViewerController(viewerModel, viewerView);

        SearchModel searchModel = new SearchModel();
        SearchView searchView = new SearchView();
        this.searchController = new SearchController(searchModel, searchView, this);

        this.view = new ExplorerView();
        this.view.addComponent(favoritesView);
        this.view.addComponent(navigationView);
        this.view.addComponent(viewerView);
        this.view.addComponent(searchView);
    }

    @Override
    public void onFavoriteSelected(String path) {
        navigationController.setCurrentPath(-1, path);
        searchController.onFolderSelected(path);
    }

    @Override
    public void onNavigationFileSelected(String path) {
        viewerController.updateViewer(path);
    }

    @Override
    public void onFolderSelected(String path) {
        System.out.println("Folder selected: " + path);
        searchController.onFolderSelected(path);
    }


    public void showExplorer() {
        view.setVisible(true);
    }

    @Override
    public void onSearchFileSelected(String path) {
        viewerController.updateViewer(path);
    }
}
