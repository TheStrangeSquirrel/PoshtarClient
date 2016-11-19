/*
 * Copyright © 2016, Malyshev Vladislav,  thestrangesquirrel@gmail.com. This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package net.squirrel.poshtar.client.activity;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import net.squirrel.poshtar.client.AppPoshtar;
import net.squirrel.poshtar.client.DAO.SQLitePoshtarHelper;
import net.squirrel.postar.client.R;

import java.util.Locale;

public class SettingsActivity extends PreferenceActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        Preference langPref = findPreference("lang");
        Preference cleaningPref = findPreference("clean");
        cleaningPref.setOnPreferenceClickListener(preference -> {
            new SQLitePoshtarHelper(getApplicationContext()).cleanTrack();
            return true;
        });
        langPref.setOnPreferenceChangeListener((preference, newValue) -> {
            updateLoc((String) newValue);
            return true;
        });
    }

    private void updateLoc(String newValue) {
        AppPoshtar.setLanguage(newValue);
        Locale locale = new Locale(newValue);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        new ResetHandler().sendEmptyMessageDelayed(0, 300);
    }

    private void resetApp() {
//        Intent intent = getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());//TODO :   Anchor
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
    }

    static class ResetHandler extends Handler {
//        private WeakReference<Context> contextWeakReference;//TODO :   Anchor
//        public ResetHandler(Context context) {
//            contextWeakReference = new WeakReference<Context>(context);
//        }

        @Override
        public void handleMessage(Message msg) {
//            Context context = contextWeakReference.get();//TODO :   Anchor
//            Intent intent = new Intent(context,HelloActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//            AlarmManager mgr = (AlarmManager) contextWeakReference.get().getSystemService(Context.ALARM_SERVICE);
//            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 700, pendingIntent);
            System.exit(0);
        }
    }
}
