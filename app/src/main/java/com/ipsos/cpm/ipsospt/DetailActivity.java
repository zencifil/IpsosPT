package com.ipsos.cpm.ipsospt;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class DetailActivity extends AppCompatActivity {

    private SessionManager _sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        _sessionManager = new SessionManager(getApplicationContext());
        _sessionManager.checkLogin();

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

            Bundle arguments = new Bundle();
            arguments.putParcelable(DetailFragment.DETAIL_URI, getIntent().getData());

            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_detail, fragment)
                    .commit();
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

//    @Override
//    public void onItemSelected(Uri contentUri) {
//        Intent intent;
//        String path = contentUri.getPathSegments().get(0);
//        switch (path) {
//            case "ind":
//                intent = new Intent(this, IndDetailActivity.class).setData(contentUri);
//                break;
//            case "panel":
//                intent = new Intent(this, DetailActivity.class).setData(contentUri);
//                break;
//            default:
//                intent = new Intent(this, DetailActivity.class).setData(contentUri);
//                break;
//        }
//
//        startActivity(intent);
//    }
}
