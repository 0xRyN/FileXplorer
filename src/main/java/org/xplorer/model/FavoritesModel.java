package org.xplorer.model;

import org.xplorer.util.config.ConfigurationManager;

import java.util.ArrayList;
import java.util.List;

public class FavoritesModel {
    private List<String> favorites;

    public FavoritesModel() {
        try {
            this.favorites = ConfigurationManager.loadConfiguration().getFavorites();
        } catch (Exception e) {
            System.out.println("Failed to load configuration, creating new list");
            // If we can't load the configuration, we create a new list
            this.favorites = new ArrayList<>();
        }

    }

    public List<String> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<String> favorites) {
        this.favorites = favorites;
    }

}
