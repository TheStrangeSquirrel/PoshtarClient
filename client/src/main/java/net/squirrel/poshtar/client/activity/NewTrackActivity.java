package net.squirrel.poshtar.client.activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import net.squirrel.poshtar.client.AppPoshtar;
import net.squirrel.poshtar.client.DAO.SQLitePoshtarHelper;
import net.squirrel.poshtar.client.DAO.SavedTrackDAO;
import net.squirrel.poshtar.client.dialog.DialogReplaceSaveTrack;
import net.squirrel.poshtar.client.dialog.DialogSaveTrack;
import net.squirrel.poshtar.client.dialog.DialogueResultListener;
import net.squirrel.poshtar.client.entity.SavedTrack;
import net.squirrel.poshtar.dto.Request;
import net.squirrel.postar.client.R;

public class NewTrackActivity extends TrackActivity implements View.OnClickListener, DialogueResultListener {

    private SavedTrackDAO savedTrackDAO;
    private Button bTrack;
    private Button bSavedTrack;
    private EditText eTrackNumber;

    private String trackNumber;

    private int providerId;
    private String providerName;
    private int existInId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        savedTrackDAO = new SQLitePoshtarHelper(this);
        setProvider();
        findViews();
        bTrack.setOnClickListener(this);
        bSavedTrack.setOnClickListener(this);
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
        tResponse = (TextView) findViewById(R.id.textResponse);
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
        String language = AppPoshtar.getLanguage();
        request = new Request(providerId, trackNumber, language);
        executeOrResumeTask(request);
    }

    private void onClickSavedTrack() {
        DialogReplaceSaveTrack dialogReplaceSaveTrack = new DialogReplaceSaveTrack();
        DialogFragment dialogSaveTrack = new DialogSaveTrack();
        dialogReplaceSaveTrack.setChildDialog(dialogSaveTrack);

        existInId = savedTrackDAO.isExistThere(providerId, eTrackNumber.getText().toString());
        if (existInId > -1) {
            dialogReplaceSaveTrack.show(getFragmentManager(), "DialogReplaceSaveTrack");
        } else {
            dialogSaveTrack.show(getFragmentManager(), "DialogSaveTrack");
        }
    }

    @Override
    public void onDialogResult(String res) {
        if (existInId > -1) {
            updateRows(res);
        } else {
            addRows(res);
        }
    }

    private void updateRows(String res) {
        String trackResult = tResponse.getText().toString();
        SavedTrack savedTrack = new SavedTrack(existInId, providerId, providerName, trackNumber, trackResult, res);
        savedTrackDAO.updateTrack(existInId, savedTrack);
    }

    private void addRows(String res) {
        String trackResult = tResponse.getText().toString();
        SavedTrack savedTrack = new SavedTrack(existInId, providerId, providerName, trackNumber, trackResult, res);
        savedTrackDAO.addTrack(savedTrack);
    }
}
