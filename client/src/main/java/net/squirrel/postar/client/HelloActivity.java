package net.squirrel.postar.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//TODO: РЕализовать статус соединения; блок кнопки newTrack
public class HelloActivity extends Activity implements View.OnClickListener {
    private Intent intent;
    private Button btnNewTrack, btnSavedTrack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        findViews();
        setListeners();
    }

    private void findViews() {
        btnSavedTrack = (Button) findViewById(R.id.newTrack);
        btnNewTrack = (Button) findViewById(R.id.savedTrack);
    }

    private void setListeners() {
        btnSavedTrack.setOnClickListener(this);
        btnNewTrack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.newTrack:
                intent = new Intent(this, ProvidersActivity.class);
                startActivity(intent);
                break;
            case R.id.savedTrack:
                break;
        }
    }
}
