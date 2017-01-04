/*
 * Copyright © 2016, Malyshev Vladislav,  thestrangesquirrel@gmail.com. This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package net.squirrel.poshtar.client;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import net.squirrel.poshtar.client.utils.LogUtil;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static net.squirrel.poshtar.client.AppPoshtar.getContext;

public class InternetStatusManager {
    private StatusInternetTask task = new StatusInternetTask();
    private AtomicBoolean internetStatus = new AtomicBoolean(false);

    InternetStatusManager() {
        task.exe();
    }

    public boolean getInternetStatus() {
        return internetStatus.get();
    }

    public void setOnInternetStatusChangeListenerAndSetValue(OnInternetStatusChangeListener listener) {
        task.setOnInternetStatusChangeListener(listener);
        listener.onInternetStatusChange(internetStatus.get());
    }

    public interface OnInternetStatusChangeListener {
        void onInternetStatusChange(boolean internetStatus);
    }

    private class StatusInternetTask extends AsyncTask<Void, Boolean, Void> {
        private ConnectivityManager connManager;
        private WeakReference<OnInternetStatusChangeListener> listenerWR;

        void setOnInternetStatusChangeListener(OnInternetStatusChangeListener listener) {
            listenerWR = new WeakReference<>(listener);
        }

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
            while (!this.isCancelled()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    LogUtil.w("Sleep error", e);
                }
                publishProgress(hasConnection());
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Boolean... isConnected) {
            if (internetStatus.get() != isConnected[0]) {
                internetStatus.set(isConnected[0]);
                if (listenerWR != null) {
                    OnInternetStatusChangeListener listener = listenerWR.get();
                    if (listener != null) {
                        listener.onInternetStatusChange(isConnected[0]);
                    }
                }
            }
        }

        private boolean hasConnection() {
            boolean isConnected;
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                isConnected = networkInfo.isConnected();
            } else {
                isConnected = false;
            }
            return isConnected;
        }
    }
}
