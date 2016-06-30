package net.squirrel.postar.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *Load properties from "/config.properties file".
 */
public class ConfigManager {
    private Properties property;
    private String baseUrl;
    private String providersUrl;
    private String trackingUrl;

    public ConfigManager() {
        this.property = new Properties();
        try {
            property.load(new FileInputStream(new File("/config.properties")));
            loadFields();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFields() {
        baseUrl = property.getProperty("BASE_URL");
        providersUrl = property.getProperty("PROVIDERS_URL");
        trackingUrl = property.getProperty("TRACKING_URL");
    }

    public String getTrackingUrl() {
        return trackingUrl;
    }

    public String getProvidersUrl() {
        return providersUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
