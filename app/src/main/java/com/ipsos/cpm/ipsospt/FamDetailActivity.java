package com.ipsos.cpm.ipsospt;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ipsos.cpm.ipsospt.helper.Constants;
import com.ipsos.cpm.ipsospt.sync.SyncAdapter;

public class FamDetailActivity extends AppCompatActivity implements FamDetailFragment.OnFamDetailButtonClickedListener {

    private SessionManager _sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fam_detail);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

            Bundle arguments = new Bundle();
            arguments.putParcelable(FamDetailFragment.DETAIL_URI, getIntent().getData());

            FamDetailFragment fragment = new FamDetailFragment();
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
            IpsosPTApplication.getInstance().logout();
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

    @Override
    public void onFamDetailButtonClicked(String buttonTag, String famCode) {
        IndListFragment indListFragment = (IndListFragment) getSupportFragmentManager().findFragmentById(R.id.ind_list_fragment);
        if (indListFragment == null) {
            IndListFragment newIndListFragment = new IndListFragment();
            Bundle args = new Bundle();
            args.putString(Constants.EXTRA_FAMCODE, famCode);
            newIndListFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.activity_detail, newIndListFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
        else {
            indListFragment.setFamCode(famCode);
            indListFragment.restartCursorLoader();
        }
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
//                intent = new Intent(this, FamDetailActivity.class).setData(contentUri);
//                break;
//            default:
//                intent = new Intent(this, FamDetailActivity.class).setData(contentUri);
//                break;
//        }
//
//        startActivity(intent);
//    }
}
