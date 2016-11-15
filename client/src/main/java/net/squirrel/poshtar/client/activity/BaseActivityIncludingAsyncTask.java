/*
 * Copyright © 2016, Malyshev Vladislav,  thestrangesquirrel@gmail.com. This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

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
