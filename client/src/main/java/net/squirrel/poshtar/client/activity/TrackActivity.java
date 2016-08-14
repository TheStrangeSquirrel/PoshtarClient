package net.squirrel.poshtar.client.activity;


import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import net.squirrel.poshtar.client.receiver.DataReceiver;
import net.squirrel.poshtar.client.utils.LogUtil;
import net.squirrel.poshtar.dto.Request;
import net.squirrel.poshtar.dto.Response;
import net.squirrel.postar.client.R;

public abstract class TrackActivity extends Activity implements View.OnClickListener {
    TextView tResponse;
    Request request;
    private ReceiverTask task;

    @Override
    public Object onRetainNonConfigurationInstance() {
        task.unLinkActivity();
        return task;
    }

    void executeOrResumeTask(Request request) {
        task = (ReceiverTask) getLastNonConfigurationInstance();

        if (task != null) {
            if (task.getStatus() != AsyncTask.Status.RUNNING) {
                task.linkActivity(this);
                return;
            }
        }
        task = new ReceiverTask();
        task.linkActivity(this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request);
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
                Toast.makeText(activity, R.string.failed, Toast.LENGTH_SHORT).show();
                return;
            }
            String status = response.getStatus().replaceAll("№ewLine#", "\n");
            activity.tResponse.setText(status);
        }


        void linkActivity(Activity activity) {
            this.activity = (TrackActivity) activity;
        }

        void unLinkActivity() {
            this.activity = null;
        }


    }

}
