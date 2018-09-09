package com.example.gregorio.moviecatalouge;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_main)
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    public static final String LANG = "en-US";
    public static final String API_KEY = BuildConfig.MOVIE_DB_API_KEY;
    public static final int NOTIFICATION_ID = 123;

    AlarmManager alarmManager;
    PendingIntent notifyPendingIntent;
    private NotificationManager notificationManager;

    private SchedullerTask schedullerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            Fragment currentFragment = new HomeFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_content, currentFragment)
                    .commit();
        }

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.gregorio.moviecatalouge", MODE_PRIVATE);

        Boolean dailyPref = sharedPreferences.getBoolean(SettingActivity.KEY_DAILY_SWITCH, true);
        Boolean upcomePref = sharedPreferences.getBoolean(SettingActivity.KEY_UPCOMING_SWITCH, true);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent notifyIntent = new Intent(this, AlarmReceiver.class);
        notifyPendingIntent = PendingIntent.getBroadcast(
                this,
                NOTIFICATION_ID,
                notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        long trigerTime;
        trigerTime = SystemClock.elapsedRealtime();
        Log.d("Triger", "triger time: " + trigerTime);
        long repeatInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;

        if (dailyPref) {
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, trigerTime, repeatInterval, notifyPendingIntent);
        } else {
            alarmManager.cancel(notifyPendingIntent);
            notificationManager.cancelAll();
            Toast.makeText(this, "daily canceled", Toast.LENGTH_SHORT).show();
        }

        if (upcomePref) {
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, trigerTime, repeatInterval, notifyPendingIntent);
        } else {
            alarmManager.cancel(notifyPendingIntent);
            notificationManager.cancelAll();
            Toast.makeText(this, "upcoming canceled", Toast.LENGTH_SHORT).show();
        }

        schedullerTask = new SchedullerTask(this);

        if (upcomePref) {
            SharedPreferences.Editor editor = getSharedPreferences("com.example.gregorio.moviecatalouge", MODE_PRIVATE).edit();
            schedullerTask.createPeriodicTask();
            editor.putBoolean(SettingActivity.KEY_UPCOMING_SWITCH, true);
            editor.commit();
        } else {
            SharedPreferences.Editor editor = getSharedPreferences("com.example.gregorio.moviecatalouge", MODE_PRIVATE).edit();
            schedullerTask.cancelPeriodicTask();
            editor.putBoolean(SettingActivity.KEY_UPCOMING_SWITCH, false);
            editor.commit();
        }

        if (dailyPref) {
            SharedPreferences.Editor editor = getSharedPreferences("com.example.gregorio.moviecatalouge", MODE_PRIVATE).edit();
            schedullerTask.createPeriodicTask();
            editor.putBoolean(SettingActivity.KEY_DAILY_SWITCH, true);
            editor.commit();
        } else {
            schedullerTask.cancelPeriodicTask();
            SharedPreferences.Editor editor = getSharedPreferences("com.example.gregorio.moviecatalouge", MODE_PRIVATE).edit();
            editor.putBoolean(SettingActivity.KEY_DAILY_SWITCH, false);
            editor.commit();
        }

    }



    @Override
    protected void onResume() {
        super.onResume();
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_open, R.string.navigation_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    protected void onPause() {
        super.onPause();
        drawer.removeDrawerListener(toggle);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String title = null;
        switch (item.getItemId()) {
            case R.id.list_view:
                title = "Mode List";

                break;

            case R.id.card_view:
                title = "Card List";
                break;

            case R.id.action_setting:
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @SuppressWarnings("StatementKosong")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Bundle bundle = new Bundle();
        Fragment fragment = null;
        String home = getString(R.string.home);
        String title = "";
        String search = getString(R.string.cari);
        String favourite = getString(R.string.favorit);
        String settings = getString(R.string.setting);
        if (id == R.id.nav_home) {
            title = home;
            fragment = new HomeFragment();
        } else if (id == R.id.nav_search) {
            title = search;
            fragment = new SearchingFragment();
            fragment.setArguments(bundle);
        } else if (id == R.id.nav_favorite) {
            title = favourite;
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.example.gregorio.favouritelist");
            if (launchIntent != null) {
                startActivity(launchIntent);
            }
        } else if (id == R.id.nav_setting) {
            title = settings;
            Intent setIntent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(setIntent);
        }

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_content, fragment)
                    .commit();
        }
        getSupportActionBar().setTitle(title);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
