package org.xplorer.interfaces;

import org.xplorer.util.config.Configuration;

public interface ConfigurationObserver {
    void onConfigurationChanged(Configuration newConfiguration);
}
