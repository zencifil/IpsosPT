package com.ipsos.cpm.ipsospt;

import android.content.Context;
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

public class ShippingFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String LOG_TAG = ShippingFragment.class.getSimpleName();
    private ShippingAdapter _shippingAdapter;
    private String _famCode;
    private String _panelType;
    private int _weekCode;

    private static final int SHIPPING_LOADER = 0;
    //specify the columns we need
    private static final String[] SHIPPING_COLUMNS = {
            PTContract.Panel.TABLE_NAME + "." + PTContract.Fam._ID,
            PTContract.Panel.COLUMN_PANEL_TYPE,
            PTContract.Panel.COLUMN_FAM_CODE,
            PTContract.Panel.COLUMN_IND_CODE,
            PTContract.Ind.COLUMN_IND_NAME,
            PTContract.Panel.COLUMN_WEEK_CODE,
            PTContract.PanelWeek.COLUMN_WEEK_DESC,
            PTContract.Panel.COLUMN_WEEK_CHECK
    };

    static final int COL_ID = 0;
    static final int COL_PANEL_TYPE = 1;
    static final int COL_FAM_CODE = 2;
    static final int COL_IND_CODE = 3;
    static final int COL_IND_NAME = 4;
    static final int COL_WEEK_CODE = 5;
    static final int COL_WEEK_DESC = 6;
    static final int COL_WEEK_CHECK = 7;

    public ShippingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _shippingAdapter = new ShippingAdapter(getActivity(), null, 0);
        View rootView = inflater.inflate(R.layout.fragment_shipping, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.shipping_list_fragment);
        listView.setAdapter(_shippingAdapter);

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
        getLoaderManager().restartLoader(SHIPPING_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String sortOrder = PTContract.Panel.COLUMN_FAM_CODE + " ASC ";
        Uri shippingUri = PTContract.Panel.CONTENT_URI;
        String selection = PTContract.Panel.COLUMN_WEEK_CHECK + " = 2 ";
        return new CursorLoader(getActivity(), shippingUri, SHIPPING_COLUMNS, selection, null, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        _shippingAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        _shippingAdapter.swapCursor(null);
    }
}
