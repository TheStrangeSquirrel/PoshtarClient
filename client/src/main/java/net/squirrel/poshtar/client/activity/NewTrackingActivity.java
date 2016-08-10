package net.squirrel.poshtar.client.activity;

import android.app.Activity;
import android.app.DialogFragment;
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
import net.squirrel.poshtar.client.dialog.DialogReplaceSaveTrack;
import net.squirrel.poshtar.client.dialog.DialogSaveTrack;
import net.squirrel.poshtar.client.dialog.DialogueResultListener;
import net.squirrel.poshtar.client.entity.SavedTrack;
import net.squirrel.poshtar.client.receiver.DataReceiver;
import net.squirrel.poshtar.client.utils.LogUtil;
import net.squirrel.poshtar.dto.Request;
import net.squirrel.poshtar.dto.Response;
import net.squirrel.postar.client.R;

public class NewTrackingActivity extends Activity implements View.OnClickListener, DialogueResultListener {
    private ReceiverTask task;
    private SavedTrackDAO savedTrackDAO;
    private Button bTrack;
    private Button bSavedTrack;
    private EditText eTrackNumber;
    private TextView textResponse;
    private String trackNumber;
    private Request request;
    private int providerId;
    private String providerName;
    private int existInId;

    @Override
    public Object onRetainNonConfigurationInstance() {
        task.unLinkActivity();
        return task;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        savedTrackDAO = new SQLitePoshtarHelper(this);
        setProvider();
        findViews();
        bTrack.setOnClickListener(this);
        bSavedTrack.setOnClickListener(this);
    }

    private void setProvider() {
        Intent intent = getIntent();
        providerId = intent.getIntExtra(ProvidersActivity.PARAM_PROVIDER_ID, 42);
        providerName = intent.getStringExtra(ProvidersActivity.PARAM_PROVIDER_NAME);

    }

    private void findViews() {
        bTrack = (Button) findViewById(R.id.bTrack);
        bSavedTrack = (Button) findViewById(R.id.bSaveTrack);
        eTrackNumber = (EditText) findViewById(R.id.eTrackN);
        textResponse = (TextView) findViewById(R.id.textResponse);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bTrack:
                onClickTrack();
                break;
            case R.id.bSaveTrack:
                onClickSavedTrack();
                break;
        }
    }

    public void onClickTrack() {
        trackNumber = eTrackNumber.getText().toString();
        if (trackNumber.isEmpty()) {
            Toast.makeText(this, R.string.enter_track_number, Toast.LENGTH_SHORT).show();
            return;
        }
        String language = AppPoshtar.getLanguage();
        request = new Request(providerId, trackNumber, language);
        executeOrResumeTask(request);
    }


    private void onClickSavedTrack() {
        DialogReplaceSaveTrack dialogReplaceSaveTrack = new DialogReplaceSaveTrack();
        DialogFragment dialogSaveTrack = new DialogSaveTrack();
        dialogSaveTrack.setStyle(DialogFragment.STYLE_NORMAL, 7);


        dialogReplaceSaveTrack.setChildDialog(dialogSaveTrack);
        existInId = savedTrackDAO.isExistThere(providerId, eTrackNumber.getText().toString());
        if (existInId > -1) {
            dialogReplaceSaveTrack.show(getFragmentManager(), "DialogReplaceSaveTrack");
        } else {
            dialogSaveTrack.show(getFragmentManager(), "DialogSaveTrack");
        }
    }

    @Override
    public void onDialogResult(String res) {
        if (existInId > -1) {
            updateRows(res);
        } else {
            addRows(res);
        }
    }

    private void updateRows(String res) {
        String trackResult = textResponse.getText().toString();
        SavedTrack savedTrack = new SavedTrack(existInId, providerId, providerName, trackNumber, trackResult, res);
        savedTrackDAO.updateTrack(existInId, savedTrack);
    }

    private void addRows(String res) {
        String trackResult = textResponse.getText().toString();
        SavedTrack savedTrack = new SavedTrack(existInId, providerId, providerName, trackNumber, trackResult, res);
        savedTrackDAO.addTrack(savedTrack);
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
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request);//TODO Передалать возможно
    }


    static class ReceiverTask extends AsyncTask<Request, Void, Response> {
        private NewTrackingActivity activity;
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
            activity.textResponse.setText(status);
        }


        public void linkActivity(Activity activity) {
            this.activity = (NewTrackingActivity) activity;
        }

        public void unLinkActivity() {
            this.activity = null;
        }


    }
}
