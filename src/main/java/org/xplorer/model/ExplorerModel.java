package org.xplorer.model;

import java.util.List;

public class ExplorerModel {
    private List<String> favorites;

    private String currentPath;

    public ExplorerModel() {
        this.favorites = List.of();
        this.currentPath = System.getProperty("user.home");
    }

    public List<String> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<String> favorites) {
        this.favorites = favorites;
    }

    public String getCurrentPath() {
        return currentPath;
    }

    public void setCurrentPath(String currentPath) {
        this.currentPath = currentPath;
    }

    public void addFavorite(String favorite) {
        favorites.add(favorite);
    }
}