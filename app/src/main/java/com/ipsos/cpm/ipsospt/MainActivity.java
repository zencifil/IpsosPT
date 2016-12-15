package com.ipsos.cpm.ipsospt;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ipsos.cpm.ipsospt.Data.PTContract;
import com.ipsos.cpm.ipsospt.Helper.Utils;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import static com.ipsos.cpm.ipsospt.Helper.Constants.EXTRA_EMAIL;
import static com.ipsos.cpm.ipsospt.Helper.Constants.EXTRA_FLDNAME;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FamListFragment.Callback {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String DETAILFRAGMENT_TAG = "DFTAG";

    private SessionManager _sessionManager;
    private Spinner _daysSpinner;

    @Override
    protected void onResume() {
        super.onResume();

        checkForCrashes();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterManagers();
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterManagers();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _sessionManager = new SessionManager(getApplicationContext());
        _sessionManager.checkLogin();
        HashMap<String, String> user = _sessionManager.getUserDetails();
        String name = user.get(SessionManager.KEY_NAME);
        String email = user.get(SessionManager.KEY_EMAIL);
        Toast.makeText(getApplicationContext(),
                "User Login Status: " + _sessionManager.isLoggedIn() + "\r\n" +
                        "Name: " + name + "\r\n" + "Email: " + email, Toast.LENGTH_LONG).show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView fldNameTextView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.fld_name_nav_header);
        fldNameTextView.setText(getIntent().getStringExtra(EXTRA_FLDNAME));
        TextView emailTextView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.fld_email_nav_header);
        emailTextView.setText(getIntent().getStringExtra(EXTRA_EMAIL));

        _daysSpinner = (Spinner) findViewById(R.id.days_spinner);
        int today = Utils.getTodayIndex();
        _daysSpinner.setSelection(today);
        _daysSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadFamListForDay(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //do nothing
            }
        });

        checkForUpdates();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            _sessionManager.logoutUser();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("finish", true); // if you are checking for this in your other Activities
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_today_list) {
            //loadFamListForDay(Utils.getTodayIndex());
            _daysSpinner.setSelection(Utils.getTodayIndex());
        }
        else if (id == R.id.nav_all_list) {
            //loadFamListForDay(0);
            _daysSpinner.setSelection(0);
        }
        else if (id == R.id.nav_call) {

        }
        else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemSelected(Uri contentUri) {
        Intent intent = new Intent(this, DetailActivity.class).setData(contentUri);
        startActivity(intent);
    }

    private void loadFamListForDay(int day) {
        // if day == 0 load all families
        FamListFragment famListFragment = (FamListFragment) getSupportFragmentManager().findFragmentById(R.id.content_main_fragment);
        if (famListFragment == null)
            return;
        famListFragment.setVisitDay(day);
        famListFragment.restartCursorLoader();
    }

    private void checkForCrashes() {
        CrashManager.register(this);
    }

    private void checkForUpdates() {
        // Remove this for store builds!
        UpdateManager.register(this);
    }

    private void unregisterManagers() {
        UpdateManager.unregister();
    }
}
