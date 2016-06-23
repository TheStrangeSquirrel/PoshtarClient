package net.squirrel.postar.client.receiver;

import android.app.Activity;

public abstract class BaseAsyncTaskIncludingActivity extends Activity {
    private TiedToActivityTask task;

    public Object onRetainNonConfigurationInstance() {
        task.unLinkActivity();
        return task;
    }

    private void taskInit() {
        task = (TiedToActivityTask) getLastNonConfigurationInstance();
        if (task == null) {
            task = createConcreteTask();
            task.linkActivity(this);
            task.execute();
        }
        task.linkActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        taskInit();
    }

    protected abstract TiedToActivityTask createConcreteTask();
}
