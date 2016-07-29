package net.squirrel.poshtar.client;

import android.app.Application;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

public class PoshtarApp extends Application {
    private static AssetManager assetManager;
    private boolean wifiStatus;
    private boolean mInternetStatus;

    public static AssetManager getAssetManager() {
        return assetManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        assetManager = this.getAssets();
        StatusInternetTask internetTask = new StatusInternetTask();
        internetTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    public boolean isWifiStatus() {
        return wifiStatus;
    }

    public boolean isMInternetStatus() {
        return mInternetStatus;
    }

    public class StatusInternetTask extends AsyncTask<Void, Boolean, Void> {
        private ConnectivityManager connManager;

        @Override
        protected void onPreExecute() {
            connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = connManager.getActiveNetworkInfo();
        }

        @Override
        protected Void doInBackground(Void... params) {
            while (true) {
                hasConnection();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
