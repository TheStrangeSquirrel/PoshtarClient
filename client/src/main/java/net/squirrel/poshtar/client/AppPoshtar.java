package net.squirrel.poshtar.client;

import android.app.Application;
import android.content.Context;
import net.squirrel.poshtar.dto.Provider;

import java.util.List;

public class AppPoshtar extends Application {
    private static Context context;
    private static ConnectManager connectManager;
    private static ProviderManager providerManager;
    private static List<Provider> providers;

    public static Context getContext() {
        return context;
    }

    public static ConnectManager getConnectManager() {
        return connectManager;
    }

    public static List<Provider> getProviders() {//TODO Обработать ситуацию когда провайдыры не получены?
        if (providers == null) {
            providers = providerManager.getProviders();
        }
        return providers;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = (Context) this;
        connectManager = new ConnectManager();
        providerManager = new ProviderManager();
        providers = providerManager.getProviders();
    }

}
