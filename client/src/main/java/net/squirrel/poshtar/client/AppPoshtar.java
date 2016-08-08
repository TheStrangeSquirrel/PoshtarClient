package net.squirrel.poshtar.client;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

public class AppPoshtar extends Application {
    private static Context context;
    private static ConnectManager connectManager;
    private static ProviderManager providerManager;
    private static String language;


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
        context = (Context) this;
        connectManager = new ConnectManager();
        providerManager = ProviderManager.getInstance();
        language = Resources.getSystem().getConfiguration().locale.getLanguage();
    }
}
