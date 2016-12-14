package com.ipsos.cpm.ipsospt;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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
