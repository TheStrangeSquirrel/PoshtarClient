package net.squirrel.poshtar.client.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import net.squirrel.poshtar.client.ProviderAdapter;
import net.squirrel.poshtar.client.ProviderManager;
import net.squirrel.poshtar.dto.Provider;
import net.squirrel.postar.client.R;

import java.util.List;

public class ProvidersActivity extends Activity implements AdapterView.OnItemClickListener, ProviderManager.SetProvidersListener {
    public static final String PARAM_PROVIDER_ID = "providerId";
    public static final String PARAM_PROVIDER_NAME = "providerName";
    private ListView listView;
    private ProviderManager providerManager;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_providers);
        listView = (ListView) findViewById(R.id.list_providers);
        providerManager = ProviderManager.getInstance();
        providerManager.addListeners(this);
        List<Provider> providers = providerManager.getProviders();
        if (providers == null) {
            waitLoadProviders();
        } else {
            listView.setAdapter(new ProviderAdapter(providers, this));
            listView.setOnItemClickListener(this);
        }
    }

    private void waitLoadProviders() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getString(R.string.dialog_message));
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        providerManager.updateProviders();
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        ListView lv = (ListView) parent;
        ProviderAdapter adapter = (ProviderAdapter) lv.getAdapter();
        int providerId = (int) adapter.getItemId(position);
        String providerName = adapter.getProvider(position).getName();
        Intent intent = new Intent(this, NewTrackingActivity.class);
        intent.putExtra(PARAM_PROVIDER_ID, providerId);
        intent.putExtra(PARAM_PROVIDER_NAME, providerName);
        startActivity(intent);
    }

    @Override
    public void setProviders(List<Provider> providers) {
        listView.setAdapter(new ProviderAdapter(providers, this));
        listView.setOnItemClickListener(this);
        progressDialog.cancel();
    }


}
