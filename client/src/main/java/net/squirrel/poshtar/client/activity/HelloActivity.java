package net.squirrel.poshtar.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import net.squirrel.poshtar.client.AppPoshtar;
import net.squirrel.postar.client.R;

public class HelloActivity extends BaseActivityIncludingAsyncTask implements View.OnClickListener {
    private Intent intent;
    private Button btnNewTrack, btnSavedTrack;
    private ImageView imgInternetStatus;
    private TextView txtInternetStatus;

    @Override
    protected TiedToActivityTask createConcreteTask() {
        return new StatusInternetTask();
    }

    @Override
    protected void onResume() {
        super.onResume();
        taskInitAndExecute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        task.cancel(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_hello, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        findViews();
        setListeners();
    }

    private void findViews() {
        btnNewTrack = (Button) findViewById(R.id.newTrack);
        btnSavedTrack = (Button) findViewById(R.id.savedTrack);
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
                break;
            case R.id.savedTrack:
                intent = new Intent(this, SaveTracksActivity.class);
                break;
        }
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_newTrack:
                intent = new Intent(this, ProvidersActivity.class);
                break;
            case R.id.menu_savedTrack:
                intent = new Intent(this, SaveTracksActivity.class);
                break;
            case R.id.menu_settings:
                intent = new Intent(this, SettingsActivity.class);
                break;
            case R.id.menu_about:
                intent = new Intent(this, AboutActivity.class);
                break;
        }
        startActivity(intent);
        return true;
    }

    private static class StatusInternetTask extends AsyncTask<Void, Void, Void> implements TiedToActivityTask {
        HelloActivity activity;
        private boolean oldInternetStatus = false;

        @Override
        public void linkActivity(Activity activity) {
            this.activity = (HelloActivity) activity;
        }

        @Override
        public void unLinkActivity() {
            this.activity = null;
        }

        @Override
        public void exe() {
            if (Build.VERSION.SDK_INT >= 11) {
                super.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                super.execute();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            while (true) {
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    publishProgress();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            boolean isInternetConnect = AppPoshtar.getConnectManager().isInternetStatus();

            if (oldInternetStatus == isInternetConnect) {
                return;
            }
            if (isInternetConnect) {
                activity.imgInternetStatus.setImageResource(R.drawable.internet_connected);
                activity.txtInternetStatus.setText(R.string.internet_status_online);
                activity.btnNewTrack.setEnabled(true);
            } else {
                activity.imgInternetStatus.setImageResource(R.drawable.internet_not_connected);
                activity.txtInternetStatus.setText(R.string.internet_status_ofline);
                activity.btnNewTrack.setEnabled(false);
            }
            oldInternetStatus = isInternetConnect;
        }
    }
}
