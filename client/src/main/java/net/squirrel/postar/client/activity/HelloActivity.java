package net.squirrel.postar.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import net.squirrel.postar.client.R;

//TODO: РЕализовать статус соединения; блок кнопки newTrack
public class HelloActivity extends Activity implements View.OnClickListener {
    private Intent intent;
    private Button btnNewTrack, btnSavedTrack;
    private ImageView imgInternetStatus;
    private TextView txtInternetStatus;
    private StatusInternetTask statusInternetTask;


    public Object onRetainNonConfigurationInstance() {
        statusInternetTask.unLinkActivity();
        return statusInternetTask;
    }

    @Override
    protected void onResume() {
        super.onResume();
        taskInit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        statusInternetTask.cancel(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        findViews();
        setListeners();
    }

    private void taskInit() {
        statusInternetTask = (StatusInternetTask) getLastNonConfigurationInstance();
        if (statusInternetTask == null) {
            statusInternetTask = new StatusInternetTask();
            statusInternetTask.linkActivity(this);
            statusInternetTask.execute();
        }
        statusInternetTask.linkActivity(this);
    }

    private void findViews() {
        btnNewTrack = (Button) findViewById(R.id.savedTrack);
        btnSavedTrack = (Button) findViewById(R.id.newTrack);
        imgInternetStatus = (ImageView) findViewById(R.id.imageConnection);
        txtInternetStatus = (TextView) findViewById(R.id.textConnection);
    }

    private void setListeners() {
        btnSavedTrack.setOnClickListener(this);
        btnNewTrack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.newTrack:
                intent = new Intent(this, ProvidersActivity.class);
                startActivity(intent);
                break;
            case R.id.savedTrack:
                break;
        }
    }

    public static class StatusInternetTask extends AsyncTask<Void, Boolean, Void> {
        protected HelloActivity activity;
        private NetworkInfo netInfo;
        private boolean isConnect;
        public void linkActivity(HelloActivity activity) {
            this.activity = activity;
        }

        public void unLinkActivity() {
            this.activity = null;
        }

        @Override
        protected void onPreExecute() {
            ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            netInfo = connectivityManager.getActiveNetworkInfo();
        }

        @Override
        protected Void doInBackground(Void... params) {
            while (true) {
                boolean internetOn = (netInfo != null && netInfo.isConnectedOrConnecting());
                publishProgress(internetOn);
                if (isCancelled()) {
                    return null;
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onProgressUpdate(Boolean... values) {
            if (isConnect == values[0]) {
                return;
            }
            if (values[0]) {
                activity.imgInternetStatus.setImageResource(R.drawable.internet_connected);
                activity.txtInternetStatus.setText(R.string.internet_status_online);
                activity.btnNewTrack.setEnabled(true);
            } else {
                activity.imgInternetStatus.setImageResource(R.drawable.internet_not_connected);
                activity.txtInternetStatus.setText(R.string.internet_status_ofline);
                activity.btnNewTrack.setEnabled(false);
            }
            isConnect = values[0];
        }
    }
}
