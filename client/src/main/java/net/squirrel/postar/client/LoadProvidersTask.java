package net.squirrel.postar.client;

import android.os.AsyncTask;
import net.squirrel.postar.client.entity.ListProvider;
import net.squirrel.postar.client.entity.Provider;
import net.squirrel.postar.client.receiver.DataManager;

import java.util.List;

public class LoadProvidersTask extends AsyncTask<Void, Void, List<Provider>> {
    private ListProvider listProvider;

    @Override
    protected List<Provider> doInBackground(Void... params) {
        listProvider = DataManager.receiveProviders();
        return listProvider.getProviders();
    }
}
