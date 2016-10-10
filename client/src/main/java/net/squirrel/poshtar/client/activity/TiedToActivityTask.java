package net.squirrel.poshtar.client.activity;

import android.app.Activity;

interface TiedToActivityTask {

    void linkActivity(Activity activity);

    void unLinkActivity();

    void exe();

    boolean cancel(boolean mayInterruptIfRunning);
}
