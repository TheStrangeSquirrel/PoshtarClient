package net.squirrel.poshtar.client;

import android.os.AsyncTask;
import net.squirrel.poshtar.client.DAO.ProvidersDAO;
import net.squirrel.poshtar.client.DAO.XMLProviderDAO;
import net.squirrel.poshtar.client.receiver.DataReceiver;
import net.squirrel.poshtar.client.utils.LogUtil;
import net.squirrel.poshtar.dto.Provider;

import java.util.List;

import static net.squirrel.poshtar.client.AppPoshtar.getConnectManager;

public class ProviderManager {
    static final long TIME_VALID_PROVIDERS_WIFI = 86400000;  //1 Day
    static final long TIME_VALID_PROVIDERS_M_INTERNET = 86400000 * 3; //3 Days
    private ProvidersDAO providersDAO;
    private DataReceiver dataReceiver;
    private List<Provider> providers;
    private long timeLastUpdateProviders;
    private AsyncTask task;

    public ProviderManager() {
        providersDAO = new XMLProviderDAO();
        dataReceiver = new DataReceiver();
        updateProviders();
    }

    public synchronized List<Provider> getProviders() {
        if (this.providers == null && task != null) {
            if (task.getStatus() == AsyncTask.Status.FINISHED) {
                updateProviders();
            }
        }
        return providers;
    }

    private void setProviders(List<Provider> providers) {
        this.providers = providers;
    }

    private void updateProviders() {
        boolean internetStatus = getConnectManager().isInternetStatus();
        timeLastUpdateProviders = providersDAO.getTimeLastUpdateProviders();

        if (internetStatus && isNeedUpdateFile()) {
            new DownloadProvidersTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        if (!isNeedUpdateFile() || (!internetStatus && isFileExists())) {
            new LoadProvidersTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            LogUtil.i("The list of providers is not available");
        }
    }


    private boolean isNeedUpdateFile() {
        if (timeLastUpdateProviders < timeToUpdate()) {
            return true;
        }
        return false;
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
        if (timeLastUpdateProviders == 0) {
            return false;
        }
        return true;
    }

    private class DownloadProvidersTask extends AsyncTask<Void, Void, List<Provider>> {

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
            setProviders(prov);
        }
    }

    private class LoadProvidersTask extends AsyncTask<Void, Void, List<Provider>> {

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
            setProviders(prov);
        }
    }
}
