package net.squirrel.poshtar.client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import net.squirrel.poshtar.client.entity.SavedTrack;
import net.squirrel.postar.client.R;

import java.util.List;

public class TracksAdapter extends BaseAdapter {
    private List<SavedTrack> tracks;
    private Context context;
    private LayoutInflater inflater;

    public TracksAdapter(List<SavedTrack> tracks, Context context) {
        this.tracks = tracks;
        this.context = context;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public SavedTrack getTrack(int position) {
        return tracks.get(position);
    }

    @Override
    public int getCount() {
        return tracks.size();
    }

    @Override
    public Object getItem(int position) {
        return tracks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tracks.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_track, parent, false);
        }
        SavedTrack track = tracks.get(position);

        String providerText = context.getText(R.string.provider) + ": " + track.getProviderName();
        String trackNText = context.getText(R.string.track_number) + " " + track.getTrackNumber();
        String descriptionText = context.getText(R.string.description) + ": " + track.getDescription();

        ((TextView) view.findViewById(R.id.provider)).setText(providerText);
        ((TextView) view.findViewById(R.id.track_n)).setText(trackNText);
        ((TextView) view.findViewById(R.id.eDescription)).setText(descriptionText);
        return view;
    }
}