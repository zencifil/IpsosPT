package com.ipsos.savascilve.ipsospt;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ipsos.savascilve.ipsospt.Data.PTContract;
import com.ipsos.savascilve.ipsospt.Model.Family;

import java.util.ArrayList;
import java.util.List;

public class FamListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private FamListAdapter _famListAdapter;
    private ListView _listView;
    private int _position = ListView.INVALID_POSITION;

    private static final String SELECTED_KEY = "selected_position";

    private static final int FAM_LIST_LOADER = 0;
    //specify the columns we need
    private static final String[] FAM_LIST_COLUMNS = {
            PTContract.Fam.TABLE_NAME + "." + PTContract.Fam._ID,
            PTContract.Fam.COLUMN_FAM_CODE,
            PTContract.Fam.COLUMN_FAM_NAME,
            PTContract.Fam.COLUMN_CITY,
            PTContract.Fam.COLUMN_TOWN,
            PTContract.Fam.COLUMN_DISTRICT,
            PTContract.Fam.COLUMN_STREET,
            PTContract.Fam.COLUMN_ROAD,
            PTContract.Fam.COLUMN_HOUSE_NO,
            PTContract.Fam.COLUMN_DOOR_NO
    };

    static final int COL_ID = 0;
    static final int COL_FAM_CODE = 1;
    static final int COL_FAM_NAME = 2;
    static final int COL_CITY = 3;
    static final int COL_TOWN = 4;
    static final int COL_DISTRICT = 5;
    static final int COL_STREET = 6;
    static final int COL_ROAD = 7;
    static final int COL_HOUSE_NO = 8;
    static final int COL_DOOR_NO = 9;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callback {
        //DetailFragmentCallback for when an item has been selected.
        public void onItemSelected(Uri dateUri);
    }

    public FamListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Add this line in order for this fragment to handle menu events.
        //setHasOptionsMenu(true);
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.forecastfragment, menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_refresh) {
//            updateWeather();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        _famListAdapter = new FamListAdapter(getActivity(), null, 0);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        _listView = (ListView) rootView.findViewById(R.id.fam_list_fragment_main);
        _listView.setAdapter(_famListAdapter);
        _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (cursor != null) {

                }
                _position = position;
            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            _position = savedInstanceState.getInt(SELECTED_KEY);
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(FAM_LIST_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (_position != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, _position);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
