package net.squirrel.poshtar.client.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import net.squirrel.poshtar.client.ProviderAdapter;
import net.squirrel.poshtar.dto.Provider;
import net.squirrel.poshtar.client.receiver.DataManager;

import net.squirrel.postar.client.R;

import java.util.List;

public class ProvidersActivity extends BaseAsyncTaskIncludingActivity implements AdapterView.OnItemClickListener {
    public static final String PARAM_PROVIDER = "provider";
    private ListView listView;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_post);
        progressDialogCreate();
        listView = (ListView) findViewById(R.id.list_post);
    }

    private void progressDialogCreate() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getString(R.string.dialog_message));
        progressDialog.setIndeterminate(true);
    }

    @Override
    protected TiedToActivityTask createConcreteTask() {
        return new LoadProvidersTask();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        ProviderAdapter adapter = (ProviderAdapter) (((ListView) parent).getAdapter());
        Provider provider = (Provider) adapter.getItem(position);
        Intent intent = new Intent(this, TrackingActivity.class);
        intent.putExtra(PARAM_PROVIDER, provider);
        startActivity(intent);
    }


    public static class LoadProvidersTask extends AsyncTask<Void, Void, List<Provider>> implements TiedToActivityTask {
        protected ProvidersActivity activity;

        public void linkActivity(Activity activity) {
            this.activity = (ProvidersActivity) activity;
        }
        public synchronized void unLinkActivity() {
            this.activity = null;
        }

        @Override
        public void execute() {
            super.execute();
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
                activity.listView.setOnItemClickListener(activity);//TODO:
            } else {
                Toast.makeText(activity, activity.getText(R.string.failed), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(activity, HelloActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                activity.startActivity(intent);
            }
        }
    }
}
