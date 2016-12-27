package com.ipsos.cpm.ipsospt;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ipsos.cpm.ipsospt.Data.PTContract;

/**
 * Created by zencifil on 26/12/2016.
 */

public class PanelFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String LOG_TAG = PanelFragment.class.getSimpleName();
    private PanelAdapter _panelAdapter;
    private ListView _listView;
    private String _famCode;
    private String _panelType;

    private static final int PANEL_LOADER = 0;
    //specify the columns we need
    private static final String[] PANEL_COLUMNS = {
            PTContract.Panel.TABLE_NAME + "." + PTContract.Fam._ID,
            PTContract.Panel.COLUMN_PANEL_TYPE,
            PTContract.Panel.COLUMN_FAM_CODE,
            PTContract.Panel.COLUMN_IND_CODE,
            PTContract.Panel.COLUMN_IND_NAME,
            PTContract.Panel.COLUMN_WEEK1_CODE,
            PTContract.Panel.COLUMN_WEEK1_CHECK,
            PTContract.Panel.COLUMN_WEEK2_CODE,
            PTContract.Panel.COLUMN_WEEK2_CHECK,
            PTContract.Panel.COLUMN_WEEK3_CODE,
            PTContract.Panel.COLUMN_WEEK3_CHECK,
            PTContract.Panel.COLUMN_WEEK4_CODE,
            PTContract.Panel.COLUMN_WEEK4_CHECK,
            PTContract.Panel.COLUMN_WEEK5_CODE,
            PTContract.Panel.COLUMN_WEEK5_CHECK
    };

    static final int COL_ID = 0;
    static final int COL_PANEL_TYPE = 1;
    static final int COL_FAM_CODE = 2;
    static final int COL_IND_CODE = 3;
    static final int COL_IND_NAME = 4;
    static final int COL_WEEK1_CODE = 5;
    static final int COL_WEEK1_CHECK = 6;
    static final int COL_WEEK2_CODE = 7;
    static final int COL_WEEK2_CHECK = 8;
    static final int COL_WEEK3_CODE = 9;
    static final int COL_WEEK3_CHECK = 10;
    static final int COL_WEEK4_CODE = 11;
    static final int COL_WEEK4_CHECK = 12;
    static final int COL_WEEK5_CODE = 13;
    static final int COL_WEEK5_CHECK = 14;

    public PanelFragment() {
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
        _panelAdapter = new PanelAdapter(getActivity(), null, 0);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_panel, container, false);

        _listView = (ListView) rootView.findViewById(R.id.panel_list_fragment_panel);
        _listView.setAdapter(_panelAdapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(PANEL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void restartCursorLoader() {
        getLoaderManager().restartLoader(PANEL_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String sortOrder = PTContract.Panel.COLUMN_IND_CODE + " ASC";
        Uri panelsUri = PTContract.Panel.buildPanelUriWithPanelTypeAndFamCode(_panelType, _famCode);
        return new CursorLoader(getActivity(), panelsUri, PANEL_COLUMNS, null, null, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        _panelAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        _panelAdapter.swapCursor(null);
    }

    public void setFamCode(String famCode) {
        _famCode = famCode;
    }

    public void setPanelType(String panelType) {
        _panelType = panelType;
    }
}
