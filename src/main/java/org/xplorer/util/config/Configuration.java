package org.xplorer.util.config;

import org.xplorer.model.viewer.ViewerType;

import java.util.List;
import java.util.Map;

public class Configuration {
    private List<String> favorites;
    private Map<String, ViewerType> viewerMappings;

    // Getters and setters
    public List<String> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<String> favorites) {
        this.favorites = favorites;
    }

    public Map<String, ViewerType> getViewerMappings() {
        return viewerMappings;
    }

    public void setViewerMappings(Map<String, ViewerType> viewerMappings) {
        this.viewerMappings = viewerMappings;
    }
}
