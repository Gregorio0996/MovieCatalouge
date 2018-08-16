package com.example.gregorio.moviecatalouge;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_main)
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;

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
        public boolean onNavigationItemSelected (MenuItem item){
            int id = item.getItemId();
            Bundle bundle = new Bundle();
            Fragment fragment = null;
            String home = getString(R.string.home);
            String title = "";
            String search = getString(R.string.cari);
            if (id == R.id.nav_home) {
                title = home;
                fragment = new HomeFragment();
            } else if (id == R.id.nav_search) { //cek idnya
                title = search;
                fragment = new SearchingFragment();
                fragment.setArguments(bundle);
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
