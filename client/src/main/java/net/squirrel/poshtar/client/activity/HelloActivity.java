/*
 * Copyright © 2016, Malyshev Vladislav,  thestrangesquirrel@gmail.com. This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package net.squirrel.poshtar.client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.ads.MobileAds;
import net.squirrel.poshtar.client.AppPoshtar;
import net.squirrel.poshtar.client.InternetStatusManager;
import net.squirrel.postar.client.R;

public class HelloActivity extends AppCompatActivity implements View.OnClickListener, InternetStatusManager.OnInternetStatusChangeListener {
    private Intent intent;
    private Button btnNewTrack, btnSavedTrack;
    private ImageView imgInternetStatus;
    private TextView txtInternetStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        findViews();
        setListeners();
        getSupportActionBar().setHomeButtonEnabled(true);
        MobileAds.initialize(getApplicationContext(), getString(R.string.ad_app_id));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_hello, menu);
        return true;
    }

    private void findViews() {
        btnNewTrack = (Button) findViewById(R.id.newTrack);
        btnSavedTrack = (Button) findViewById(R.id.savedTrack);
        imgInternetStatus = (ImageView) findViewById(R.id.imageConnection);
        txtInternetStatus = (TextView) findViewById(R.id.textConnection);
    }

    private void setListeners() {
        btnSavedTrack.setOnClickListener(this);
        btnNewTrack.setOnClickListener(this);
        AppPoshtar.getInternetStatusManager().setOnInternetStatusChangeListenerAndSetValue(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.newTrack:
                intent = new Intent(this, ProvidersActivity.class);
                break;
            case R.id.savedTrack:
                intent = new Intent(this, SaveTracksActivity.class);
                break;
        }
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_newTrack:
                intent = new Intent(this, ProvidersActivity.class);
                break;
            case R.id.menu_savedTrack:
                intent = new Intent(this, SaveTracksActivity.class);
                break;
            case R.id.menu_settings:
                intent = new Intent(this, SettingsActivity.class);
                break;
            case R.id.menu_about:
                intent = new Intent(this, AboutActivity.class);
                break;
        }
        startActivity(intent);
        return true;
    }

    @Override
    public void onInternetStatusChange(boolean internetStatus) {
        if (internetStatus) {
            imgInternetStatus.setImageResource(R.drawable.internet_connected);
            txtInternetStatus.setText(R.string.internet_status_online);
            btnNewTrack.setEnabled(true);
        } else {
            imgInternetStatus.setImageResource(R.drawable.internet_not_connected);
            txtInternetStatus.setText(R.string.internet_status_ofline);
            btnNewTrack.setEnabled(false);
        }
    }
}
