package net.squirrel.poshtar.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import net.squirrel.poshtar.client.DAO.SQLitePoshtarHelper;
import net.squirrel.poshtar.client.TracksAdapter;
import net.squirrel.poshtar.client.entity.SavedTrack;
import net.squirrel.postar.client.R;

import java.io.Serializable;
import java.util.List;

public class SaveTracksActivity extends BaseActivityIncludingAsyncTask implements AdapterView.OnItemClickListener, Serializable {
    public static final String PARAM_SAVE_TRACK = "save_track";
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_tracks);
        listView = (ListView) findViewById(R.id.list);

    }

    @Override
    protected void onStart() {
        super.onStart();
        taskInitAndExecute();
    }

    @Override
    protected TiedToActivityTask createConcreteTask() {
        return new SaveTracksActivity.LoadTrackTask();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        ListView listView = (ListView) parent;
        TracksAdapter adapter = (TracksAdapter) listView.getAdapter();
        SavedTrack track = adapter.getTrack(position);

        Intent intent = new Intent(this, SaveTrackActivity.class);
        intent.putExtra(PARAM_SAVE_TRACK, track);
        startActivity(intent);
    }


    private static class LoadTrackTask extends AsyncTask<Void, Void, List<SavedTrack>> implements TiedToActivityTask {
        SaveTracksActivity activity;
        List<SavedTrack> tracks;

        @Override
        public void linkActivity(Activity activity) {
            this.activity = (SaveTracksActivity) activity;
        }

        @Override
        public synchronized void unLinkActivity() {
            this.activity = null;
        }

        @Override
        public void execute() {
            super.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        @Override
        protected List<SavedTrack> doInBackground(Void... params) {
            SQLitePoshtarHelper sqLitePoshtarHelper = new SQLitePoshtarHelper(activity);
            tracks = sqLitePoshtarHelper.getTracks();

            while (activity == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    //NOP
                }
            }
            return tracks;
        }

        @Override
        protected void onPostExecute(List<SavedTrack> tracks) {
            if (tracks != null) {
                activity.listView.setAdapter(new TracksAdapter(tracks, activity));
                activity.listView.setOnItemClickListener(activity);
            } else {
                Toast.makeText(activity, activity.getText(R.string.failed), Toast.LENGTH_LONG).show();
            }
        }
    }
}
