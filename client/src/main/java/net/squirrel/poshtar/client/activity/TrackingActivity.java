package net.squirrel.poshtar.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import net.squirrel.poshtar.dto.Provider;
import net.squirrel.poshtar.dto.Request;
import net.squirrel.poshtar.dto.Response;
import net.squirrel.poshtar.client.receiver.DataManager;

import net.squirrel.postar.client.R;

public class TrackingActivity extends Activity implements View.OnClickListener {
    ReceiverTask task;
    private Button bTrack;
    private EditText editText;
    private TextView textResponse;
    private String codePost;
    private Request request;
    private Provider provider;

    public Object onRetainNonConfigurationInstance() {
        task.unLinkActivity();
        return task;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        setProvider();
        findViews();
        bTrack.setOnClickListener(this);
    }

    private void setProvider() {
        Intent intent = getIntent();
        provider = (Provider) intent.getSerializableExtra(ProvidersActivity.PARAM_PROVIDER);
    }

    private void findViews() {
        bTrack = (Button) findViewById(R.id.bTrack);
        editText = (EditText) findViewById(R.id.editRequest);
        textResponse = (TextView) findViewById(R.id.textResponse);
    }

    @Override
    public void onClick(View v) {
        Editable editable = editText.getText();
        if (editable.toString().isEmpty()) {
            Toast.makeText(this, R.string.enter_track_number, Toast.LENGTH_SHORT).show();
            return;
        }
        codePost = editable.toString();
        String language = Resources.getSystem().getConfiguration().locale.getLanguage();
        request = new Request(codePost, provider, language);
        executeOrResumeTask(request);
    }

    public void executeOrResumeTask(Request request) {
        task = (ReceiverTask) getLastNonConfigurationInstance();

        if (task == null) {
            //NOP
        } else {
            if (task.getStatus() != AsyncTask.Status.RUNNING) {
                task.linkActivity(this);
                return;
            }
        }
        task = new ReceiverTask();
        task.linkActivity(this);
        task.execute(request);//TODO Передалать возможно
    }

    static class ReceiverTask extends AsyncTask<Request, Void, Response> {
        private TrackingActivity activity;

        @Override
        protected Response doInBackground(Request... params) {
            Response response = DataManager.track(params[0]);
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
            activity.textResponse.setText(response.getStatus());
        }


        public void linkActivity(Activity activity) {
            this.activity = (TrackingActivity) activity;
        }

        public void unLinkActivity() {
            this.activity = null;
        }


    }
}
