package com.ipsos.cpm.ipsospt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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

import com.ipsos.cpm.ipsospt.data.PTContract;
import com.ipsos.cpm.ipsospt.helper.ConnectivityReceiver;
import com.ipsos.cpm.ipsospt.helper.Constants;
import com.ipsos.cpm.ipsospt.helper.Utils;
import com.ipsos.cpm.ipsospt.sync.SyncAdapter;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.Certificate;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
            FamListFragment.Callback,
            ConnectivityReceiver.ConnectivityReceiverListener {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String DETAILFRAGMENT_TAG = "DFTAG";

    private Spinner _daysSpinner;
    private String _fldCode;
    private String _fldName;
    private String _fldEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkConnection();
        String authKey = IpsosPTApplication.getInstance().getAuthKey();
        if (authKey == null || authKey.isEmpty()) {
            IpsosPTApplication.getInstance().logout();
        }

        HashMap<String, String> user = IpsosPTApplication.getInstance().getUserDetails();
        _fldName = user.get(Constants.KEY_FLD_NAME);
        _fldEmail = user.get(Constants.KEY_FLD_EMAIL);
        _fldCode = user.get(Constants.KEY_FLD_CODE);
//        Toast.makeText(getApplicationContext(),
//                "Token: " + authKey + "\r\n" +
//                        "Name: " + _fldName + "\r\n" + "Email: " + _fldEmail, Toast.LENGTH_LONG).show();

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
        fldNameTextView.setText(_fldCode + " - " + _fldName);
        TextView emailTextView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.fld_email_nav_header);
        emailTextView.setText(_fldEmail);

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

        SyncAdapter.initializeSyncAdapter(this);
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
            ((IpsosPTApplication)getApplication()).logout();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("finish", true); // if you are checking for this in your other Activities
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        else if (id == R.id.action_sync) {
            SyncAdapter.syncImmediately(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_today_list) {
            _daysSpinner.setSelection(Utils.getTodayIndex());
        }
        else if (id == R.id.nav_all_list) {
            _daysSpinner.setSelection(0);
        }
        else if (id == R.id.nav_shipping) {
            Intent intent = new Intent(getApplicationContext(), ShippingActivity.class);
            intent.setData(PTContract.Panel.buildShippingUri());
            startActivity(intent);
        }
        else if (id == R.id.nav_call) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + Constants.PHONE_NUMBER));
            startActivity(Intent.createChooser(intent, getString(R.string.navigation_call)));
        }
        else if (id == R.id.nav_send) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("plain/text");
            intent.putExtra(Intent.EXTRA_EMAIL, Constants.EMAIL_TO_LIST);
            intent.putExtra(Intent.EXTRA_SUBJECT, Constants.EMAIL_SUBJECT.replace("{0}", _fldCode + " - " + _fldName));
            intent.putExtra(Intent.EXTRA_TEXT, "");
            startActivity(Intent.createChooser(intent, getString(R.string.navigation_send_mail)));
        }
        else if (id == R.id.nav_new_fam_form) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(Constants.LINK_TO_NEW_FORM));
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (!IpsosPTApplication.getInstance().isLoggedIn())
            IpsosPTApplication.getInstance().logout();
        IpsosPTApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onItemSelected(Uri contentUri) {
        Intent intent = new Intent(this, FamDetailActivity.class).setData(contentUri);
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

    private boolean checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
        return isConnected;
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = getString(R.string.internet_connection);
            color = Color.WHITE;
        } else {
            message = getString(R.string.internet_no_connection);
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar.make(findViewById(R.id.content_main_fragment), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

}
