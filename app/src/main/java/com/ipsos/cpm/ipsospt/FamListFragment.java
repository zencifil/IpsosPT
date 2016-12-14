package com.ipsos.cpm.ipsospt;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ipsos.cpm.ipsospt.Data.PTContract;

import java.util.Calendar;
import java.util.Locale;

public class FamListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String LOG_TAG = FamListFragment.class.getSimpleName();
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
        public void onItemSelected(Uri famUri);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _famListAdapter = new FamListAdapter(getActivity(), null, 0);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        _listView = (ListView) rootView.findViewById(R.id.fam_list_fragment_main);
        _listView.setAdapter(_famListAdapter);
        _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (cursor != null)
                    ((Callback) getActivity()).onItemSelected(PTContract.Fam.buildFamilyUriWithFamCode(cursor.getString(COL_FAM_CODE)));

                _position = position;
            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            _position = savedInstanceState.getInt(SELECTED_KEY);
        }

        Log.d(LOG_TAG, "view created");
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
        String sortOrder = PTContract.Fam.COLUMN_FAM_NAME + " ASC";

        Calendar c = Calendar.getInstance();
        String day = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
        int visitDay;
        switch (day) {
            case "Monday":
                visitDay = 1;
                break;
            case "Tuesday":
                visitDay = 2;
                break;
            case "Wednesday":
                visitDay = 3;
                break;
            case "Thursday":
                visitDay = 4;
                break;
            case "Friday":
                visitDay = 5;
                break;
            case "Saturday":
                visitDay = 6;
                break;
            case "Sunday":
                visitDay = 7;
                break;
            default:
                visitDay = -1;
                break;
        }

        Uri famListForTodayUri = PTContract.Fam.buildFamilyUriForDay(visitDay);
        return new CursorLoader(getActivity(), famListForTodayUri, FAM_LIST_COLUMNS, null, null, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        _famListAdapter.swapCursor(data);
        if (_position != ListView.INVALID_POSITION)
            _listView.smoothScrollToPosition(_position);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        _famListAdapter.swapCursor(null);
    }
}
