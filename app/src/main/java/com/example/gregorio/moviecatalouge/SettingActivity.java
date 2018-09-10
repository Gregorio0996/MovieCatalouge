package com.example.gregorio.moviecatalouge;

import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingActivity extends PreferenceActivity {
    public static final String KEY_DAILY_SWITCH = "daily_switch";
    public static final String KEY_UPCOMING_SWITCH = "upcoming_switch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingFragment())
                .commit();
    }
}
