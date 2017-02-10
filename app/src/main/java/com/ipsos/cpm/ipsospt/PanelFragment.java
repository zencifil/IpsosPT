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
import android.widget.Toast;

import com.ipsos.cpm.ipsospt.data.PTContract;

public class PanelFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String LOG_TAG = PanelFragment.class.getSimpleName();
    private PanelAdapter _panelAdapter;
    private String _famCode;
    private String _panelType;
    private int _weekCode;

    private static final int PANEL_LOADER = 0;
    //specify the columns we need
    private static final String[] PANEL_COLUMNS = {
            PTContract.Panel.TABLE_NAME + "." + PTContract.Fam._ID,
            PTContract.Panel.TABLE_NAME + "." + PTContract.Panel.COLUMN_PANEL_TYPE,
            PTContract.Panel.TABLE_NAME + "." + PTContract.Panel.COLUMN_FAM_CODE,
            PTContract.Panel.TABLE_NAME + "." + PTContract.Panel.COLUMN_IND_CODE,
            PTContract.Ind.TABLE_NAME + "." + PTContract.Ind.COLUMN_IND_NAME,
            PTContract.Panel.TABLE_NAME + "." + PTContract.Panel.COLUMN_WEEK_CODE,
            PTContract.Panel.TABLE_NAME + "." + PTContract.Panel.COLUMN_WEEK_CHECK,
            PTContract.PanelWeek.TABLE_NAME + "." + PTContract.PanelWeek.COLUMN_WEEK_DESC
    };

    static final int COL_ID = 0;
    static final int COL_PANEL_TYPE = 1;
    static final int COL_FAM_CODE = 2;
    static final int COL_IND_CODE = 3;
    static final int COL_IND_NAME = 4;
    static final int COL_WEEK_CODE = 5;
    static final int COL_WEEK_CHECK = 6;
    static final int COL_WEEK_DESC = 7;

    public PanelFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _panelAdapter = new PanelAdapter(getActivity(), null, 0);
        View rootView = inflater.inflate(R.layout.fragment_panel, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.panel_list_fragment_panel);
        listView.setAdapter(_panelAdapter);

        View emptyView = rootView.findViewById(R.id.panel_list_fragment_empty);
        listView.setEmptyView(emptyView);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
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
        String sortOrder = PTContract.Panel.TABLE_NAME + "." + PTContract.Panel.COLUMN_IND_CODE + " ASC";
        Uri panelsUri = PTContract.Panel.buildPanelUriWithPanelTypeFamCodeAndWeekCode(_panelType, _famCode, _weekCode);
        return new CursorLoader(getActivity(), panelsUri, PANEL_COLUMNS, null, null, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        _panelAdapter.swapCursor(data);
        if (data.getCount() == 0)
            Toast.makeText(getActivity(), R.string.no_data_found, Toast.LENGTH_SHORT).show();
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

    public void setWeekCode(int weekCode) {
        _weekCode = weekCode;
    }

}
