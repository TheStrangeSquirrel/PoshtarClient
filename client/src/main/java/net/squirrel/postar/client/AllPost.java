package net.squirrel.postar.client;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class AllPost extends Activity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_post);

        listView = (ListView) findViewById(R.id.list_post);

    }
}
