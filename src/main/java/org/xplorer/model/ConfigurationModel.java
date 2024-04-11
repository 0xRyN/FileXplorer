package org.xplorer.model;

import org.xplorer.model.viewer.ViewerType;
import org.xplorer.util.config.Configuration;
import org.xplorer.util.config.ConfigurationManager;

import java.util.List;
import java.util.Map;

public class ConfigurationModel {
    private Configuration configuration;

    public ConfigurationModel() {
        this.configuration = ConfigurationManager.loadConfiguration();
    }

    public List<String> getFavorites() {
        return configuration.getFavorites();
    }

    public void setFavorites(List<String> favorites) {
        configuration.setFavorites(favorites);
        ConfigurationManager.saveConfiguration(configuration);
    }

    public Map<String, ViewerType> getViewerMappings() {
        return configuration.getViewerMappings();
    }

    public void setViewerMappings(Map<String, ViewerType> viewerMappings) {
        configuration.setViewerMappings(viewerMappings);
        ConfigurationManager.saveConfiguration(configuration);
    }
}
