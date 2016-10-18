package net.squirrel.poshtar.client.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import net.squirrel.poshtar.client.DAO.SQLitePoshtarHelper;
import net.squirrel.postar.client.R;

import java.util.Locale;

public class SettingsActivity extends PreferenceActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        Preference langPref = (Preference) findPreference("lang");
        Preference cleaningPref = (Preference) findPreference("clean");
        cleaningPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                new SQLitePoshtarHelper(getApplicationContext()).cleanTrack();
                return true;
            }
        });

        langPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                updateLoc((String) newValue);
                return true;
            }
        });

    }

    private void updateLoc(String newValue) {
        Locale locale = new Locale(newValue);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, null);
        resetApp();
    }

    private void resetApp() {
        Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
