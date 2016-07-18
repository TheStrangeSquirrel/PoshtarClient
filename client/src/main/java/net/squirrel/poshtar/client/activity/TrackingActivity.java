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
import net.squirrel.poshtar.client.receiver.DataManager;
import net.squirrel.poshtar.dto.Provider;
import net.squirrel.poshtar.dto.Request;
import net.squirrel.poshtar.dto.Response;
import net.squirrel.postar.client.R;

import java.util.Locale;

public class TrackingActivity extends BaseAsyncTaskIncludingActivity implements View.OnClickListener {
    private Button track;
    private EditText editText;
    private TextView textResponse;
    private String codePost;
    private Request request;
    private Provider provider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViews();
        setProvider();
        setContentView(R.layout.activity_tracking);
    }

    private void setProvider() {
        Intent intent = getIntent();
        provider = (Provider) intent.getSerializableExtra(ProvidersActivity.PARAM_PROVIDER);
    }

    private void findViews() {
        track = (Button) findViewById(R.id.bTrack);
        editText = (EditText) findViewById(R.id.editRequest);
        textResponse = (TextView) findViewById(R.id.textResponse);
    }

    @Override
    public void onClick(View v) {
        Editable editable = editText.getText();
        if (editable.toString().isEmpty()) {
            Toast.makeText(this, R.string.enter_track_number, Toast.LENGTH_SHORT);
            return;
        }
        codePost = editable.toString();
        Locale locale = Resources.getSystem().getConfiguration().locale;
        request = new Request(codePost, provider, locale);
        ReceiverTask task = new ReceiverTask();
        task.execute(request);
    }

    @Override
    protected TiedToActivityTask createConcreteTask() {
        return new ReceiverTask();
    }

    static class ReceiverTask extends AsyncTask<Request, Void, Response> implements TiedToActivityTask {
        private TrackingActivity activity;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Response doInBackground(Request... params) {
            return DataManager.track(params[0]);
        }

        @Override
        protected void onPostExecute(Response response) {
            if (response == null) {
                Toast.makeText(activity, R.string.failed, Toast.LENGTH_SHORT).show();
                return;
            }
            activity.textResponse.setText(response.getStatus());
        }

        @Override
        public void linkActivity(Activity activity) {
            this.activity = (TrackingActivity) activity;
        }

        @Override
        public void unLinkActivity() {
            this.activity = null;
        }

        @Override
        public void execute() {
            super.execute();
        }
    }
}
