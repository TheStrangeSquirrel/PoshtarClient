/*
 * Copyright © 2016, Malyshev Vladislav,  thestrangesquirrel@gmail.com. This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package net.squirrel.poshtar.client;

import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;


public class UrlConfigManager {
    public static final String PROVIDERS_URL_SUFFIX = "/providers";
    public static final String TRACKING_URL_SUFFIX = "/track";
    public static final String SERVER_SETTINGS_URL_SUFFIX = "/settings";
    private static final String KEY_BASE_URL = "url";
    private static final String DEFAULT_BASE_URL = "***";
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
