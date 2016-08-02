package net.squirrel.poshtar.client;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import net.squirrel.poshtar.client.utils.LogUtil;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static net.squirrel.poshtar.client.AppPoshtar.getContext;

public class ConnectManager {
    private boolean wifiStatus;
    private boolean mInternetStatus;

    public ConnectManager() {
        StatusInternetTask internetTask = new StatusInternetTask();
        internetTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public boolean isWifiStatus() {
        return wifiStatus;
    }

    public boolean isMInternetStatus() {
        return mInternetStatus;
    }

    public boolean isInternetStatus() {
        return mInternetStatus || wifiStatus;
    }


    private class StatusInternetTask extends AsyncTask<Void, Boolean, Void> {
        private ConnectivityManager connManager;

        @Override
        protected void onPreExecute() {
            connManager = (ConnectivityManager) getContext().getSystemService(CONNECTIVITY_SERVICE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            while (true) {
                hasConnection();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    LogUtil.w("Sleep error", e);
                }
            }
        }

        private void hasConnection() {
            NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (networkInfo != null && networkInfo.isConnected()) {
                wifiStatus = true;
            }
            networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (networkInfo != null && networkInfo.isConnected()) {
                mInternetStatus = true;
            }
            networkInfo = connManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                mInternetStatus = true;
            }
        }

    }

}
