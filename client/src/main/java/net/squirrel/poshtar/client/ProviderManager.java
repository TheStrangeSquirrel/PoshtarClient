/*
 * Copyright © 2016, Malyshev Vladislav,  thestrangesquirrel@gmail.com. This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package net.squirrel.poshtar.client;

import android.os.AsyncTask;
import android.os.Build;
import net.squirrel.poshtar.client.DAO.ProvidersDAO;
import net.squirrel.poshtar.client.DAO.XMLProviderDAO;
import net.squirrel.poshtar.client.receiver.DataReceiver;
import net.squirrel.poshtar.client.utils.LogUtil;
import net.squirrel.poshtar.dto.ListProvider;
import net.squirrel.poshtar.dto.Provider;
import net.squirrel.poshtar.dto.ServerSettings;

import java.util.ArrayList;
import java.util.List;

import static net.squirrel.poshtar.client.AppPoshtar.getConnectManager;

/**
 * Singleton and Observer
 */
public class ProviderManager {
    private static final long TIME_TO_UPDATE = 86400000;  //1 Day
    private static ProviderManager instance;
    private ProvidersDAO providersDAO;
    private DataReceiver dataReceiver;
    private List<Provider> providers;
    private List<SetProvidersListener> listeners;
    private long timeLastUpdateProviders;
    private BaseProvidersTask task;

    private ProviderManager() {
        listeners = new ArrayList<SetProvidersListener>();
        providersDAO = new XMLProviderDAO();
        dataReceiver = new DataReceiver();
        updateProviders();
    }

    public static synchronized ProviderManager getInstance() {
        if (instance == null) {
            instance = new ProviderManager();
        }
        return instance;
    }

    public synchronized List<Provider> getProviders() {
        return providers;
    }

    public void addListeners(SetProvidersListener listener) {
        listeners.add(listener);
    }


    public void updateProviders() {
        boolean internetStatus = getConnectManager().isInternetStatus();
        boolean isTimeCheckVersion = isTimeCheckVersion();


        if (internetStatus && isTimeCheckVersion) {
            LogUtil.d("Download providers");
            task = new CheckVersionAndDownloadProvidersTask();
        } else if (!isTimeCheckVersion || (!internetStatus && isFileExists())) {
            LogUtil.d("Load providers ");
            task = new LoadProvidersTask();
        } else {
            LogUtil.i("The list of providers is not available");
            return;
        }
        task.exe();
    }


    private boolean isTimeCheckVersion() {
        timeLastUpdateProviders = providersDAO.getTimeLastUpdateProvidersFile();
        long delta = System.currentTimeMillis() - TIME_TO_UPDATE;
        return timeLastUpdateProviders < delta;
    }


    private boolean isFileExists() {
        return timeLastUpdateProviders != 0;
    }

    public interface SetProvidersListener {
        void setProviders(List<Provider> providers);
    }

    private abstract class BaseProvidersTask extends AsyncTask<Void, Void, List<Provider>> {
        void exe() {
            if (Build.VERSION.SDK_INT >= 11) {
                super.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                super.execute();
            }
        }
    }

    private class CheckVersionAndDownloadProvidersTask extends BaseProvidersTask {

        @Override
        protected List<Provider> doInBackground(Void... params) {
            List<Provider> prov = null;
            try {
                ServerSettings serverSettings = dataReceiver.receiveServerSettings();
                if (providersDAO.getTimeLastUpdateProvidersFile() == 0) {
                    return downloadProviders();
                } else {
                    ListProvider listProvider = providersDAO.loadProviders();
                    String versionProviders = listProvider.getVersionProviders();
                    if (serverSettings.getCurrentVersion().equals(versionProviders)) {
                        providersDAO.setTimeLastUpdateProvidersFile(System.currentTimeMillis());
                        prov = listProvider.getProviders();
                    } else {
                        prov = downloadProviders();
                    }
                }
            } catch (Exception e) {
                LogUtil.w("Error CheckVersionAndDownloadProvidersTas", e);
            }
            return prov;
        }

        private List<Provider> downloadProviders() throws Exception {
            String providersXML = dataReceiver.receiveProvidersXML();
            providersDAO.saveProviders(providersXML);
            return dataReceiver.getProviderListFromXML(providersXML);
        }

        @Override
        protected void onPostExecute(List<Provider> prov) {
            if (prov == null) {
                return;
            }
            providers = prov;
            for (SetProvidersListener listener : listeners) {
                listener.setProviders(prov);
            }
        }
    }

    private class LoadProvidersTask extends BaseProvidersTask {

        @Override
        protected List<Provider> doInBackground(Void... params) {
            List<Provider> prov = null;
            try {
                prov = providersDAO.loadProviders().getProviders();
            } catch (Exception e) {
                LogUtil.w("Error load providers", e);
            }
            return prov;
        }

        @Override
        protected void onPostExecute(List<Provider> prov) {
            if (prov == null) {
                return;
            }
            providers = prov;
            for (SetProvidersListener listener : listeners) {
                listener.setProviders(prov);
            }
        }
    }
}
