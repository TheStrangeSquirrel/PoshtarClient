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
import net.squirrel.poshtar.client.receiver.DataReceiver;
import net.squirrel.poshtar.client.utils.LogUtil;
import net.squirrel.poshtar.dto.Request;
import net.squirrel.poshtar.dto.Response;
import net.squirrel.postar.client.R;

public abstract class TrackActivity extends FragmentActivity implements View.OnClickListener {
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


    private static class ReceiverTask extends AsyncTask<Request, Void, Response> {
        private TrackActivity activity;
        private DataReceiver dataReceiver;

        {
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

            activity.progressDialog.dismiss();
            activity.tStatus.setText(Html.fromHtml(response.getStatus()));
        }


        void linkActivity(Activity activity) {
            this.activity = (TrackActivity) activity;
        }

        void unLinkActivity() {
            this.activity = null;
        }

    }

}
