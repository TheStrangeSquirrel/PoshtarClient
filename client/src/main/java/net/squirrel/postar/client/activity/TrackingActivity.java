package net.squirrel.postar.client.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import net.squirrel.postar.client.R;
import net.squirrel.postar.client.dto.Request;
import net.squirrel.postar.client.entity.Response;
import net.squirrel.postar.client.receiver.DataManager;

public class TrackingActivity extends Activity implements View.OnClickListener {
    private Button track;
    private EditText editText;
    private TextView textResponse;
    private String codePost;
    private Response response;
    private Request request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViews();
        setContentView(R.layout.activity_tracking);
    }

    private void findViews() {
        track = (Button) findViewById(R.id.bTrack);
        editText = (EditText) findViewById(R.id.editRequest);
        textResponse = (TextView) findViewById(R.id.textResponse);
    }

    @Override
    public void onClick(View v) {
        Editable editable = editText.getText();
        if (editable.toString().isEmpty()) {
            Toast.makeText(this, R.string.enter_track_number, Toast.LENGTH_SHORT);
            return;
        }
        codePost = editable.toString();
        request = new Request(codePost);
        response = DataManager.track(request);
    }
}
