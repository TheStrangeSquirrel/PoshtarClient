package net.squirrel.poshtar.client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import net.squirrel.poshtar.dto.Provider;
import net.squirrel.postar.client.R;

import java.util.List;


public class ProviderAdapter extends BaseAdapter {
    private List<Provider> providers;
    private Context context;
    private LayoutInflater inflater;

    public ProviderAdapter(List<Provider> providers, Context context) {
        this.providers = providers;
        this.context = context;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public Provider getProvider(int position) {
        return providers.get(position);
    }

    @Override
    public int getCount() {
        return providers.size();
    }

    @Override
    public Object getItem(int position) {
        return providers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return providers.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_providers, parent, false);
        }
        Provider p = providers.get(position);

        ((TextView) view.findViewById(R.id.textView)).setText(p.getName());
        ((ImageView) view.findViewById(R.id.imageView)).setImageBitmap(p.getBitmap());
        return view;
    }
}