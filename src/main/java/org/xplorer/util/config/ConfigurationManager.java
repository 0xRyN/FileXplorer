package org.xplorer.util.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.xplorer.model.viewer.ViewerType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class ConfigurationManager {
    private static final String CONFIG_FILE_PATH = System.getProperty("user.home") + "/.explorer.conf";
    private static Configuration configuration;

    public static synchronized Configuration loadConfiguration() {
        File configFile = new File(CONFIG_FILE_PATH);
        if (!configFile.exists()) {
            createDefaultConfiguration();
        }

        if (configuration == null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                configuration = mapper.readValue(configFile, Configuration.class);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to load configuration from " + CONFIG_FILE_PATH, e);
            }
        }
        return configuration;
    }

    private static void createDefaultConfiguration() {
        Configuration defaultConfig = new Configuration();

        Path desktopPath = Paths.get(System.getProperty("user.home"), "Desktop").toAbsolutePath();
        Path documentsPath = Paths.get(System.getProperty("user.home"), "Documents").toAbsolutePath();
        Path downloadsPath = Paths.get(System.getProperty("user.home"), "Downloads").toAbsolutePath();

        defaultConfig.setFavorites(List.of(
                desktopPath.toString(),
                documentsPath.toString(),
                downloadsPath.toString()
        ));

        Map<String, ViewerType> defaultMappings = Map.of(
                "txt", ViewerType.TEXT_VIEWER,
                "png", ViewerType.IMAGE_VIEWER,
                "jpg", ViewerType.IMAGE_VIEWER,
                "jpeg", ViewerType.IMAGE_VIEWER,
                "gif", ViewerType.IMAGE_VIEWER
        );

        defaultConfig.setViewerMappings(defaultMappings);

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(CONFIG_FILE_PATH), defaultConfig);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create default configuration at " + CONFIG_FILE_PATH, e);
        }
    }
}

