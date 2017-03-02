package com.ipsos.cpm.ipsospt;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.widget.Toast;

import com.ipsos.cpm.ipsospt.data.PTContract;
import com.ipsos.cpm.ipsospt.helper.Utils;
import com.ipsos.cpm.ipsospt.sync.SyncAdapter;

import static com.ipsos.cpm.ipsospt.helper.Constants.KEY_SYNC_STATUS;

public class FamListFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>,
                   SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String LOG_TAG = FamListFragment.class.getSimpleName();
    private FamListAdapter _famListAdapter;
    private ListView _listView;
    private int _position = ListView.INVALID_POSITION;
    private int _visitDay;
    private String _sortOrder;

    private static final String SELECTED_KEY = "selected_position";

    private static final int FAM_LIST_LOADER = 0;
    //specify the columns we need
    private static final String[] FAM_LIST_COLUMNS = {
            PTContract.Fam.TABLE_NAME + "." + PTContract.Fam._ID,
            PTContract.Fam.COLUMN_FAM_CODE,
            PTContract.Fam.COLUMN_FAM_NAME,
            PTContract.Fam.COLUMN_DISTRICT,
            PTContract.Fam.COLUMN_NEIGHBORHOOD,
            PTContract.Fam.COLUMN_ADDRESS,
            PTContract.Fam.COLUMN_AVP,
            PTContract.Fam.COLUMN_SP,
            PTContract.Fam.COLUMN_ALK,
            PTContract.Fam.COLUMN_BABY,
            PTContract.Fam.COLUMN_EK_ALK
    };

    static final int COL_ID = 0;
    static final int COL_FAM_CODE = 1;
    static final int COL_FAM_NAME = 2;
    static final int COL_DISTRICT = 3;
    static final int COL_NEIGHBORHOOD = 4;
    static final int COL_ADDRESS = 5;
    static final int COL_AVP = 6;
    static final int COL_SP = 7;
    static final int COL_ALK = 8;
    static final int COL_BABY = 9;
    static final int COL_EK_ALK = 10;

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals(KEY_SYNC_STATUS)) {
            String message = Utils.getLastSyncStatus(getContext());
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callback {
        //DetailFragmentCallback for when an item has been selected.
        void onItemSelected(Uri famUri);
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

    public void setVisitDay(int visitDay) {
        _visitDay = visitDay;
    }

    public void setOrder(String order) {
        _sortOrder = order;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _famListAdapter = new FamListAdapter(getActivity(), null, 0);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        _visitDay = Utils.getTodayIndex();
        _sortOrder = PTContract.Fam.COLUMN_FAM_NAME + " ASC";

        _listView = (ListView) rootView.findViewById(R.id.fam_list_fragment_main);
        _listView.setAdapter(_famListAdapter);
        _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (cursor != null)
                    ((Callback) getActivity()).onItemSelected(PTContract.Fam.buildFamilyUriWithFamCode("_" + cursor.getString(COL_FAM_CODE)));

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
    public void onResume() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sp.registerOnSharedPreferenceChangeListener(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sp.unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    public void restartCursorLoader() {
        getLoaderManager().restartLoader(FAM_LIST_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri famListForTodayUri = PTContract.Fam.buildFamilyUriForDay(_visitDay);
        return new CursorLoader(getActivity(), famListForTodayUri, FAM_LIST_COLUMNS, null, null, _sortOrder);
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
