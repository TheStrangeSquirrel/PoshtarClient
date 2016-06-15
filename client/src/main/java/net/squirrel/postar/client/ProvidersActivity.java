package net.squirrel.postar.client;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import net.squirrel.postar.client.entity.ListProvider;
import net.squirrel.postar.client.entity.Provider;
import net.squirrel.postar.client.exception.AppException;
import net.squirrel.postar.client.receiver.DataManager;

import java.util.List;

public class ProvidersActivity extends Activity {
    private ListView listView;
    private ProgressDialog progressDialog;
    private LoadProvidersTask loadProvidersTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_post);
        progressDialogCreate();

        loadProvidersTask = (LoadProvidersTask) getLastNonConfigurationInstance();
        if (loadProvidersTask == null) {
            loadProvidersTask = new LoadProvidersTask();
            loadProvidersTask.linkActivity(this);
            loadProvidersTask.execute();
        }
        loadProvidersTask.linkActivity(this);
        listView = (ListView) findViewById(R.id.list_post);
    }

    public Object onRetainNonConfigurationInstance() {
        loadProvidersTask.unLinkActivity();
        return loadProvidersTask;
    }

    private void progressDialogCreate() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getString(R.string.dialog_message));
        progressDialog.setIndeterminate(true);
    }


    public static class LoadProvidersTask extends AsyncTask<Void, Void, List<Provider>> {
        private ProvidersActivity activity;

        public void linkActivity(ProvidersActivity activity) {
            this.activity = activity;
        }

        public void unLinkActivity() {
            this.activity = null;
        }

        @Override
        protected List<Provider> doInBackground(Void... params) {
            List<Provider> listProvider = null;
            try {
                ListProvider providers = DataManager.receiveProviders();
                listProvider = providers.getProviders();
            } catch (AppException e) {
                //NOP
            }
            while (activity == null) {
                //NOP
            }
            return listProvider;
        }

        @Override
        protected void onPostExecute(List<Provider> providers) {
            if (providers != null) {
                activity.progressDialog.dismiss();//TODO ДОделать
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
