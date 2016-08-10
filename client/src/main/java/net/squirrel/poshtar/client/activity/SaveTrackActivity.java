package net.squirrel.poshtar.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import net.squirrel.poshtar.client.AppPoshtar;
import net.squirrel.poshtar.client.DAO.SQLitePoshtarHelper;
import net.squirrel.poshtar.client.DAO.SavedTrackDAO;
import net.squirrel.poshtar.client.entity.SavedTrack;
import net.squirrel.poshtar.client.receiver.DataReceiver;
import net.squirrel.poshtar.client.utils.LogUtil;
import net.squirrel.poshtar.dto.Request;
import net.squirrel.poshtar.dto.Response;
import net.squirrel.postar.client.R;

public class SaveTrackActivity extends Activity implements View.OnClickListener {
    private ReceiverTask task;
    private SavedTrackDAO savedTrackDAO;
    private Button bRefresh;
    private Button bStopTr;
    private EditText eTrackNumber;
    private TextView tResponse;
    private Request request;

    private SavedTrack track;

    @Override
    public Object onRetainNonConfigurationInstance() {
        task.unLinkActivity();
        return task;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_track);
        savedTrackDAO = new SQLitePoshtarHelper(this);
        String language = AppPoshtar.getLanguage();
        getExtra();
        findViews();
        setContentView();

        request = new Request(track.getProviderID(), track.getTrackNumber(), language);
        executeOrResumeTask(request);

        bRefresh.setOnClickListener(this);
        bStopTr.setOnClickListener(this);
    }
    private void findViews() {
        bRefresh = (Button) findViewById(R.id.bTrack);
        bStopTr = (Button) findViewById(R.id.bStopTr);
        eTrackNumber = (EditText) findViewById(R.id.eTrackN);
        tResponse = (TextView) findViewById(R.id.textResponse);
    }

    private void getExtra() {
        Intent intent = getIntent();
        track = (SavedTrack) intent.getSerializableExtra(SaveTracksActivity.PARAM_SAVE_TRACK);
    }

    private void setContentView() {
        eTrackNumber.setText(track.getTrackNumber());
        tResponse.setText(track.getTrackResult());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bTrack:
                executeOrResumeTask(request);
                break;
            case R.id.bStopTr:
                savedTrackDAO.removeTrack(track.getId());
                finish();//TODO
                break;
        }
    }


    private void executeOrResumeTask(Request request) {
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


    static class ReceiverTask extends AsyncTask<Request, Void, Response> {
        private SaveTrackActivity activity;
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


        public void linkActivity(Activity activity) {
            this.activity = (SaveTrackActivity) activity;
        }

        public void unLinkActivity() {
            this.activity = null;
        }


    }
}
