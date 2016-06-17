package net.squirrel.postar.client.receiver;

import android.os.AsyncTask;

@Deprecated //TODO:  Расматриваемый вариант реализации
public abstract class BaseAsyncTask<Params, Progress, Result, ActivityClass> extends AsyncTask<Params, Progress, Result> {
    protected ActivityClass activity;

    public void linkActivity(ActivityClass activity) {
        this.activity = activity;
    }

    public void unLinkActivity() {
        this.activity = null;
    }

}
