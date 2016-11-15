/*
 * Copyright © 2016, Malyshev Vladislav,  thestrangesquirrel@gmail.com. This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package net.squirrel.poshtar.client.activity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import net.squirrel.poshtar.client.RateManager;
import net.squirrel.poshtar.client.receiver.DataReceiver;
import net.squirrel.poshtar.client.utils.LogUtil;
import net.squirrel.poshtar.dto.Request;
import net.squirrel.poshtar.dto.Response;
import net.squirrel.postar.client.R;

public abstract class TrackActivity extends FragmentActivity implements View.OnClickListener {
    private static final RateManager rateManager = new RateManager();
    TextView tStatus;
    ProgressDialog progressDialog;
    Request request;
    private ReceiverTask task;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getString(R.string.package_tracking));
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);

    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        if (task != null) {
            task.unLinkActivity();
        }
        return task;
    }

    void executeOrResumeTask(Request request) {
        task = (ReceiverTask) getLastCustomNonConfigurationInstance();

        if (task != null) {
            if (task.getStatus() != AsyncTask.Status.RUNNING) {
                task.linkActivity(this);
                return;
            }
        }
        task = new ReceiverTask();
        task.linkActivity(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request);
        } else {
            task.execute(request);
        }

    }

    protected void updateFields(String status) {
        progressDialog.dismiss();
        tStatus.setText(Html.fromHtml(status));
    }

    private class ReceiverTask extends AsyncTask<Request, Void, Response> {
        private TrackActivity activity;
        private DataReceiver dataReceiver;

        ReceiverTask() {
            dataReceiver = new DataReceiver();
        }

        @Override
        protected Response doInBackground(Request... params) {
            Response response = null;
            try {
                response = dataReceiver.track(params[0]);
            } catch (Exception e) {
                LogUtil.w("Unable to trace the package", e);
            }
            while (activity == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    //NOP
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            if (response == null) {
                activity.progressDialog.dismiss();
                Toast.makeText(activity, R.string.failed, Toast.LENGTH_SHORT).show();
                return;
            }
            updateFields(response.getStatus());
            rateManager.show(getSupportFragmentManager());
        }


        void linkActivity(Activity activity) {
            this.activity = (TrackActivity) activity;
        }

        void unLinkActivity() {
            this.activity = null;
        }

    }
}
