package net.squirrel.poshtar.client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import net.squirrel.poshtar.client.entity.SavedTrack;
import net.squirrel.poshtar.dto.Provider;
import net.squirrel.postar.client.R;

import java.util.List;

public class TracksAdapter extends BaseAdapter {
    private List<SavedTrack> tracks;
    private Context context;
    private LayoutInflater inflater;
    private List<Provider> providers;

    public TracksAdapter(List<SavedTrack> tracks, Context context) {
        this.tracks = tracks;
        this.context = context;
        providers = ProviderManager.getInstance().getProviders();
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            view = inflater.inflate(R.layout.item_providers, parent, false);
        }
        SavedTrack track = tracks.get(position);
        String providerName = null;
        for (Provider p : providers) {
            if (p.getId() == track.getProviderID()) {//TODO
                providerName = p.getName();
            }
        }

        ((TextView) view.findViewById(R.id.provider)).setText(providerName);
        ((TextView) view.findViewById(R.id.track_n)).setText(track.getTrackNumber());
        ((TextView) view.findViewById(R.id.description)).setText(track.getDescription());
        return view;
    }
}