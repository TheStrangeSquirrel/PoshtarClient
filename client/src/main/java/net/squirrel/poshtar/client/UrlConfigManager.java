package net.squirrel.poshtar.client;

import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;


public class UrlConfigManager {
    public static final String PROVIDERS_URL_SUFFIX = "/providers";
    public static final String TRACKING_URL_SUFFIX = "/track";
    public static final String SERVER_SETTINGS_URL_SUFFIX = "/settings";
    private static final String KEY_BASE_URL = "url";
    private static final String DEFAULT_BASE_URL = "http://poshtar.us-west-2.elasticbeanstalk.com/";
    private String baseUrl;
    private SharedPreferences sPref;

    public UrlConfigManager() {
        sPref = AppPoshtar.getContext().getSharedPreferences("config", MODE_PRIVATE);
        baseUrl = sPref.getString(KEY_BASE_URL, DEFAULT_BASE_URL);
    }

    public synchronized void updateUrl(String newUrl) {
        SharedPreferences.Editor editor = sPref.edit();
        baseUrl = newUrl;
        editor.putString(KEY_BASE_URL, newUrl);
        editor.commit();
    }

    public synchronized String getBaseUrl() {
        return baseUrl;
    }
}
