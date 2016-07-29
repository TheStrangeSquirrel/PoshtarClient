package net.squirrel.poshtar.client;

import net.squirrel.poshtar.client.utils.LogUtil;

import java.util.Properties;

/**
 * Load properties from "/config.properties file".
 */
public class ConfigManager {
    private Properties property;
    private String baseUrl;
    private String providersUrl;
    private String trackingUrl;

    public ConfigManager() {
        this.property = new Properties();
        try {
            property.load(PoshtarApp.getAssetManager().open("config.properties"));
            loadFields();
        } catch (Exception e) {
            LogUtil.e("Error read properties file", e);
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
