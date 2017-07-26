package com.example.robbie.fitnesstracker;

import android.app.FragmentManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.robbie.fitnesstracker.fragments.RoomFragment;
import com.example.robbie.fitnesstracker.fragments.SettingsFragment;

public class SummaryScreenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      //  Toolbar myToolbar = (Toolbar) findViewById(R.id.);
       // setSupportActionBar(myToolbar);

        SQLiteDatabase db=openOrCreateDatabase("GoalsDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS goal(steps VARCHAR,date VARCHAR,goal VARCHAR);");


       FragmentManager manager = getFragmentManager();
        RoomFragment roomFragment = new RoomFragment();
        manager.beginTransaction().replace(R.id.content_summary_screen, roomFragment, roomFragment.getTag()).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_summary) {
            RoomFragment roomFragment = new RoomFragment();
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().replace(R.id.content_summary_screen, roomFragment, roomFragment.getTag()).commit();
        }  else if (id == R.id.nav_settings) {
            SettingsFragment settingsFragment = new SettingsFragment();
           FragmentManager manager = getFragmentManager();
           manager.beginTransaction().replace(R.id.content_summary_screen, settingsFragment, settingsFragment.getTag()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
