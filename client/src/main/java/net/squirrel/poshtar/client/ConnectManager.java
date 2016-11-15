/*
 * Copyright © 2016, Malyshev Vladislav,  thestrangesquirrel@gmail.com. This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package net.squirrel.poshtar.client;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import net.squirrel.poshtar.client.utils.LogUtil;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static net.squirrel.poshtar.client.AppPoshtar.getContext;

public class ConnectManager {
    private boolean wifiStatus;
    private boolean mInternetStatus;

    ConnectManager() {
        StatusInternetTask task = new StatusInternetTask();
        task.exe();
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

        void exe() {
            if (Build.VERSION.SDK_INT >= 11) {
                super.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                super.execute();
            }
        }

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
            if (networkInfo != null) {
                wifiStatus = networkInfo.isConnected();
            }
            networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (networkInfo != null) {
                mInternetStatus = networkInfo.isConnected();
            }
            networkInfo = connManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                mInternetStatus = networkInfo.isConnected();
            }
        }
    }
}
