package net.squirrel.poshtar.client.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import net.squirrel.poshtar.client.AppPoshtar;
import net.squirrel.poshtar.client.DAO.SQLitePoshtarHelper;
import net.squirrel.poshtar.client.DAO.SavedTrackDAO;
import net.squirrel.poshtar.client.dialog.DialogReplaceSaveTrack;
import net.squirrel.poshtar.client.dialog.DialogSaveTrack;
import net.squirrel.poshtar.client.entity.SavedTrack;
import net.squirrel.poshtar.dto.Request;
import net.squirrel.postar.client.R;

public class NewTrackActivity extends TrackActivity implements View.OnClickListener, DialogSaveTrack.DialogueResultListener {

    private SavedTrackDAO savedTrackDAO;
    private Button bTrack;
    private Button bSavedTrack;
    private EditText eTrackNumber;
    private AdView mAdView;

    private DialogReplaceSaveTrack dialogReplaceSaveTrack;
    private DialogFragment dialogSaveTrack;

    private String trackNumber;

    private int providerId;
    private String providerName;
    private int existInId;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("status", tStatus.getText());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            tStatus.setText(savedInstanceState.getCharSequence("status"));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_track);
        savedTrackDAO = new SQLitePoshtarHelper(this);
        setProvider();
        findViews();
        setDialog();
        bTrack.setOnClickListener(this);
        bSavedTrack.setOnClickListener(this);
        eTrackNumber.setOnEditorActionListener((textView, i, keyEvent) -> {
            trackNumber = eTrackNumber.getText().toString();
            if (trackNumber.isEmpty()) {
                Toast.makeText(this, R.string.enter_track_number, Toast.LENGTH_SHORT).show();
                return false;
            }
            hideKeyboard();
            onClickTrack();
            return true;
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(eTrackNumber.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


    private void setDialog() {
        dialogReplaceSaveTrack = new DialogReplaceSaveTrack();
        dialogSaveTrack = new DialogSaveTrack();
    }

    private void setProvider() {
        Intent intent = getIntent();
        providerId = intent.getIntExtra(ProvidersActivity.PARAM_PROVIDER_ID, 42);
        providerName = intent.getStringExtra(ProvidersActivity.PARAM_PROVIDER_NAME);

    }

    private void findViews() {
        bTrack = (Button) findViewById(R.id.bTrack);
        bSavedTrack = (Button) findViewById(R.id.bSaveTrack);
        eTrackNumber = (EditText) findViewById(R.id.eTrackN);
        tStatus = (TextView) findViewById(R.id.textResponse);
        mAdView = (AdView) findViewById(R.id.adView);
    }

    @Override
    public void onClick(View v) {
        trackNumber = eTrackNumber.getText().toString();
        if (trackNumber.isEmpty()) {
            Toast.makeText(this, R.string.enter_track_number, Toast.LENGTH_SHORT).show();
            return;
        }
        switch (v.getId()) {
            case R.id.bTrack:
                onClickTrack();
                break;
            case R.id.bSaveTrack:
                onClickSavedTrack();
                break;
        }
    }

    private void onClickTrack() {
        progressDialog.show();
        String language = AppPoshtar.getLanguage();
        request = new Request(providerId, trackNumber, language);
        executeOrResumeTask(request);
    }

    private void onClickSavedTrack() {
        existInId = savedTrackDAO.isExistThere(providerId, eTrackNumber.getText().toString());
        if (existInId > -1) {
            dialogReplaceSaveTrack.show(getSupportFragmentManager(), "DialogReplaceSaveTrack");
        } else {
            dialogSaveTrack.show(getSupportFragmentManager(), "DialogSaveTrack");
        }
    }

    @Override
    public void onDialogResult(String res) {
        existInId = savedTrackDAO.isExistThere(providerId, eTrackNumber.getText().toString());
        trackNumber = eTrackNumber.getText().toString();
        String trackResult = tStatus.getText().toString();
        SavedTrack savedTrack = new SavedTrack(existInId, providerId, providerName, trackNumber, trackResult, res);
        if (existInId > -1) {
            savedTrackDAO.updateTrack(existInId, savedTrack);
        } else {
            savedTrackDAO.addTrack(savedTrack);
        }
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
