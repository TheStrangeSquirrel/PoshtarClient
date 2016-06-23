package net.squirrel.postar.client.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import net.squirrel.postar.client.ProviderAdapter;
import net.squirrel.postar.client.R;
import net.squirrel.postar.client.entity.Provider;
import net.squirrel.postar.client.receiver.DataManager;

import java.util.List;

public class ProvidersActivity extends Activity implements View.OnClickListener {
    private ListView listView;
    private ProgressDialog progressDialog;
    private LoadProvidersTask loadProvidersTask;

    public Object onRetainNonConfigurationInstance() {
        loadProvidersTask.unLinkActivity();
        return loadProvidersTask;
    }

    @Override
    protected void onResume() {
        super.onResume();
        taskInit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_post);
        progressDialogCreate();

        listView = (ListView) findViewById(R.id.list_post);
//        listView.setOnClickListener(this);// TODO: Обрабатывать нажатия
    }

    private void taskInit() {
        loadProvidersTask = (LoadProvidersTask) getLastNonConfigurationInstance();
        if (loadProvidersTask == null) {//TODO :
            loadProvidersTask = new LoadProvidersTask();
            loadProvidersTask.linkActivity(this);
            loadProvidersTask.execute();
        }
        loadProvidersTask.linkActivity(this);
    }

    private void progressDialogCreate() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getString(R.string.dialog_message));
        progressDialog.setIndeterminate(true);
    }

    @Override
    public void onClick(View v) {
        //// TODO: 22.06.2016

    }


    public static class LoadProvidersTask extends AsyncTask<Void, Void, List<Provider>> {
        protected ProvidersActivity activity;

        public synchronized void linkActivity(ProvidersActivity activity) {
            this.activity = activity;
        }

        public synchronized void unLinkActivity() {
            this.activity = null;
        }

        @Override
        protected List<Provider> doInBackground(Void... params) {
            List<Provider> listProvider = DataManager.receiveProviders();

            while (activity == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    //NOP
                }
            }
            return listProvider;
        }

        @Override
        protected void onPostExecute(List<Provider> providers) {
            if (providers != null) {
                activity.progressDialog.dismiss();
                activity.listView.setAdapter(new ProviderAdapter(providers, activity));
            } else {
                Toast.makeText(activity, activity.getText(R.string.failed), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(activity, HelloActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                activity.startActivity(intent);
            }
        }
    }
}
