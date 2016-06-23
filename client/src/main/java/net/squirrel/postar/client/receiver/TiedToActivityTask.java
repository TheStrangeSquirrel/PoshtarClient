package net.squirrel.postar.client.receiver;

import android.app.Activity;

public interface TiedToActivityTask {
    public void linkActivity(Activity activity);
    public void unLinkActivity();
    public void execute();

    public boolean cancel(boolean mayInterruptIfRunning);
}
