package net.squirrel.poshtar.client;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import java.util.Locale;

public class AppPoshtar extends Application {
    private static Context context;
    private static ConnectManager connectManager;
    private static ProviderManager providerManager;
    private static String language;
    private SharedPreferences preferences;
    private Locale locale;


    public static Context getContext() {
        return context;
    }

    public static ConnectManager getConnectManager() {
        return connectManager;
    }

    public static String getLanguage() {
        return language;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        connectManager = new ConnectManager();
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
        getResources().updateConfiguration(config, null);
    }
}
