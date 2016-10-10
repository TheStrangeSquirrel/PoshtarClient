package net.squirrel.poshtar.client;

import android.os.AsyncTask;
import android.os.Build;
import net.squirrel.poshtar.client.DAO.ProvidersDAO;
import net.squirrel.poshtar.client.DAO.XMLProviderDAO;
import net.squirrel.poshtar.client.receiver.DataReceiver;
import net.squirrel.poshtar.client.utils.LogUtil;
import net.squirrel.poshtar.dto.Provider;

import java.util.ArrayList;
import java.util.List;

import static net.squirrel.poshtar.client.AppPoshtar.getConnectManager;

/**
 * Singleton and Observer
 */
public class ProviderManager {
    private static final long TIME_VALID_PROVIDERS_WIFI = 86400000;  //1 Day
    private static final long TIME_VALID_PROVIDERS_M_INTERNET = 86400000 * 3; //3 Days
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
        boolean isNeedUpdateFile = isNeedUpdateFile();
        timeLastUpdateProviders = providersDAO.getTimeLastUpdateProviders();

        if (internetStatus && isNeedUpdateFile) {
            LogUtil.d("Download providers");
            task = new DownloadProvidersTask();
        } else if (!isNeedUpdateFile || (!internetStatus && isFileExists())) {
            LogUtil.d("Load providers ");
            task = new LoadProvidersTask();
        } else {
            LogUtil.i("The list of providers is not available");
            return;
        }
        task.exe();
    }


    private boolean isNeedUpdateFile() {
        return timeLastUpdateProviders < timeToUpdate();
    }

    private long timeToUpdate() {
        long deltaTime;
        if (getConnectManager().isInternetStatus()) {
            deltaTime = TIME_VALID_PROVIDERS_WIFI;
        } else {
            deltaTime = TIME_VALID_PROVIDERS_M_INTERNET;
        }
        return System.currentTimeMillis() - deltaTime;
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

    private class DownloadProvidersTask extends BaseProvidersTask {

        @Override
        protected List<Provider> doInBackground(Void... params) {

            List<Provider> prov = null;
            try {
                String providersXML = dataReceiver.receiveProvidersXML();
                providersDAO.saveProviders(providersXML);
                prov = dataReceiver.getProviderListFromXML(providersXML);
            } catch (Exception e) {
                LogUtil.w("DownloadProvidersTask", e);
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

    private class LoadProvidersTask extends BaseProvidersTask {

        @Override
        protected List<Provider> doInBackground(Void... params) {
            List<Provider> prov = null;
            try {
                prov = providersDAO.loadProviders();
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
