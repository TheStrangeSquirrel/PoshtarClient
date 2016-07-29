package net.squirrel.poshtar.client.activity;

import android.app.Activity;

public abstract class BaseActivityIncludingAsyncTask extends Activity {
    protected TiedToActivityTask task;

    @Override
    public Object onRetainNonConfigurationInstance() {
        task.unLinkActivity();
        return task;
    }

    /**
     * It creates and executes the task if the task is what keeps activity to task.
     */
    protected void taskInitAndExecute() {
        task = (TiedToActivityTask) getLastNonConfigurationInstance();
        if (task == null) {
            task = createConcreteTask();
            task.linkActivity(this);
            task.execute();
        } else {
            task.linkActivity(this);
        }

    }
    protected abstract TiedToActivityTask createConcreteTask();
}
