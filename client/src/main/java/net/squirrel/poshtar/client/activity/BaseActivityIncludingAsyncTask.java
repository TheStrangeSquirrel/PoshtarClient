package net.squirrel.poshtar.client.activity;

import android.support.v4.app.FragmentActivity;

public abstract class BaseActivityIncludingAsyncTask extends FragmentActivity {
    TiedToActivityTask task;

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        task.unLinkActivity();
        return task;
    }

    /**
     * It creates and executes the task if the task is what keeps activity to task.
     */
    void taskInitAndExecute() {
        task = (TiedToActivityTask) getLastCustomNonConfigurationInstance();
        if (task == null) {
            task = createConcreteTask();
            task.linkActivity(this);
            task.exe();
        } else {
            task.linkActivity(this);
        }
    }
    protected abstract TiedToActivityTask createConcreteTask();
}
