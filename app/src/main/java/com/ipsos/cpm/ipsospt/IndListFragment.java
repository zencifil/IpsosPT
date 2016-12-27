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
 * A simple {@link Fragment} subclass.
 */
public class IndListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String LOG_TAG = IndListFragment.class.getSimpleName();
    private IndListAdapter _indListAdapter;
    private ListView _listView;
    private Uri _famUri;

    private static final int IND_LIST_LOADER = 0;
    //specify the columns we need
    private static final String[] IND_LIST_COLUMNS = {
            PTContract.Ind.TABLE_NAME + "." + PTContract.Ind._ID,
            PTContract.Ind.COLUMN_FAM_CODE,
            PTContract.Ind.COLUMN_IND_CODE,
            PTContract.Ind.COLUMN_IND_NAME
    };

    static final int COL_ID = 0;
    static final int COL_FAM_CODE = 1;
    static final int COL_IND_CODE = 2;
    static final int COL_IND_NAME = 3;



    public IndListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ind_list, container, false);
        _indListAdapter = new IndListAdapter(getActivity(), null, 0);

        _listView = (ListView) rootView.findViewById(R.id.ind_list_fragment);
        _listView.setAdapter(_indListAdapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(IND_LIST_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String sortOrder = PTContract.Ind.COLUMN_IND_CODE + " ASC";
        if (_famUri == null) {
            Bundle a = getArguments();
            String famCode;
            if (a != null) {
                famCode = a.getString("famCode");
                _famUri = PTContract.Ind.buildIndUriWithFamCode(famCode);
            }
        }
        Uri indListForFamUri = PTContract.Ind.buildIndUriFromFamUri(_famUri);
        return new CursorLoader(getActivity(), indListForFamUri, IND_LIST_COLUMNS, null, null, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        _indListAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        _indListAdapter.swapCursor(null);
    }

    public void setFamCode(String famCode) {
        _famUri = PTContract.Ind.buildIndUriWithFamCode(famCode);
    }

    public void restartCursorLoader() {
        getLoaderManager().restartLoader(IND_LIST_LOADER, null, this);
    }
}
