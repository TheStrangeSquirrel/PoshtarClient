/*
 * Copyright © 2016, Malyshev Vladislav,  thestrangesquirrel@gmail.com. This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package net.squirrel.poshtar.client;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import net.squirrel.poshtar.client.utils.LogUtil;

import java.util.Locale;

public class AppPoshtar extends Application {
    private static Context context;
    private static InternetStatusManager internetStatusManager;
    private static ProviderManager providerManager;

    private static String language;
    private SharedPreferences preferences;
    private Locale locale;


    public static Context getContext() {
        return context;
    }

    public static InternetStatusManager getInternetStatusManager() {
        return internetStatusManager;
    }

    public static String getLanguage() {
        return language;
    }

    public static void setLanguage(String language) {
        AppPoshtar.language = language;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        internetStatusManager = new InternetStatusManager();
        providerManager = ProviderManager.getInstance();
        langConfig();

    }

    private void langConfig() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        language = preferences.getString("lang", "default");
        if ("default".equals(language)) {
            language = Resources.getSystem().getConfiguration().locale.getLanguage();
        }
        locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LogUtil.d("onConfigurationChanged | language = " + language);
        locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
}
