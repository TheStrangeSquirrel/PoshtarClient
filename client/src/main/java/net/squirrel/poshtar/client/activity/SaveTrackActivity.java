/*
 * Copyright © 2016, Malyshev Vladislav,  thestrangesquirrel@gmail.com. This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package net.squirrel.poshtar.client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import net.squirrel.poshtar.client.AppPoshtar;
import net.squirrel.poshtar.client.DAO.SQLitePoshtarHelper;
import net.squirrel.poshtar.client.DAO.SavedTrackDAO;
import net.squirrel.poshtar.client.entity.SavedTrack;
import net.squirrel.poshtar.dto.Request;
import net.squirrel.postar.client.R;

public class SaveTrackActivity extends TrackActivity implements View.OnClickListener {
    private Button bRefresh;
    private Button bStopTr;
    private EditText eTrackNumber;
    private AdView mAdView;

    private SavedTrackDAO savedTrackDAO;
    private SavedTrack track;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_track);
        savedTrackDAO = new SQLitePoshtarHelper(this);
        findViews();
        getExtra();
        setContentView();
        request = new Request(track.getProviderID(), track.getTrackNumber(), AppPoshtar.getLanguage());

        bRefresh.setOnClickListener(this);
        bStopTr.setOnClickListener(this);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void findViews() {
        wStatus = (WebView) findViewById(R.id.textResponse);
        bRefresh = (Button) findViewById(R.id.bTrack);
        bStopTr = (Button) findViewById(R.id.bStopTr);
        eTrackNumber = (EditText) findViewById(R.id.eTrackN);
        mAdView = (AdView) findViewById(R.id.adView);
    }

    private void getExtra() {
        Intent intent = getIntent();
        track = (SavedTrack) intent.getSerializableExtra(SaveTracksActivity.PARAM_SAVE_TRACK);
    }

    private void setContentView() {
        eTrackNumber.setText(track.getTrackNumber());
        updateFields(track.getTrackResult());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bTrack:
                progressDialog.show();
                executeOrResumeTask(request);
                break;
            case R.id.bStopTr:
                savedTrackDAO.removeTrack(track.getId());
                Intent intent = new Intent(getBaseContext(), HelloActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void updateFields(String status) {
        track.setTrackResult(status);
        savedTrackDAO.updateTrack(track.getId(), track);
        super.updateFields(status);
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}
