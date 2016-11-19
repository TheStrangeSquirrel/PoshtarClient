/*
 * Copyright © 2016, Malyshev Vladislav,  thestrangesquirrel@gmail.com. This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package net.squirrel.poshtar.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import net.squirrel.poshtar.client.AppPoshtar;
import net.squirrel.poshtar.client.DAO.SQLitePoshtarHelper;
import net.squirrel.poshtar.client.TracksAdapter;
import net.squirrel.poshtar.client.entity.SavedTrack;
import net.squirrel.postar.client.R;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.List;

public class SaveTracksActivity extends Activity implements AdapterView.OnItemClickListener, Serializable {
    static final String PARAM_SAVE_TRACK = "save_track";
    private ListView listView;
    private TracksAdapter tracksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_tracks);
        listView = (ListView) findViewById(R.id.list);
        LoadTrackTask task = new LoadTrackTask();
        task.linkActivity(this);
        task.exe();
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        ListView listView = (ListView) parent;
//        TracksAdapter adapter = (TracksAdapter) listView.getAdapter();
//        SavedTrack track = adapter.getTrack(position);
        SavedTrack track = tracksAdapter.getTrack(position);

        Intent intent = new Intent(this, SaveTrackActivity.class);
        intent.putExtra(PARAM_SAVE_TRACK, track);
        startActivity(intent);
    }

    private void updateListView(List<SavedTrack> tracks) {
        tracksAdapter = new TracksAdapter(tracks, getApplicationContext());
        listView.setAdapter(tracksAdapter);
        listView.setOnItemClickListener(this);
    }

    private static class LoadTrackTask extends AsyncTask<Void, Void, List<SavedTrack>> {
        WeakReference<SaveTracksActivity> activityReference;
        List<SavedTrack> tracks;

        public void linkActivity(SaveTracksActivity activity) {
            this.activityReference = new WeakReference<>(activity);
        }

        public void exe() {
            if (Build.VERSION.SDK_INT >= 11) {
                executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                execute();
            }
        }

        @Override
        protected List<SavedTrack> doInBackground(Void... params) {
            SQLitePoshtarHelper sqLitePoshtarHelper = new SQLitePoshtarHelper(AppPoshtar.getContext());
            return sqLitePoshtarHelper.getTracks();
        }

        @Override
        protected void onPostExecute(List<SavedTrack> tracks) {
            SaveTracksActivity activity = activityReference.get();
            if (activity != null) {
                if (tracks != null) {
                    activity.updateListView(tracks);
                } else {
                    Toast.makeText(activity, activity.getText(R.string.failed), Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
