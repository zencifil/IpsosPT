package com.ipsos.cpm.ipsospt;

import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TabHost;

import com.ipsos.cpm.ipsospt.Data.PTContract;

import java.util.ArrayList;

public class PanelActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Spinner _panelTypesSpinner;

    private static final int PANELTYPE_LOADER = 0;

    private static final String[] PANELTYPES_COLUMNS = {
            PTContract.PanelsWeeks.TABLE_NAME + "." + PTContract.PanelsWeeks._ID,
            PTContract.PanelsWeeks.COLUMN_PANEL_TYPE
    };

    static final int COL_ID = 0;
    static final int COL_PANEL_TYPE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);

        getSupportLoaderManager().initLoader(PANELTYPE_LOADER, null, this);

        _panelTypesSpinner = (Spinner) findViewById(R.id.panel_types_spinner);
        _panelTypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO load weeks
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Do nothing
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String sortOrder = PTContract.PanelsWeeks.COLUMN_PANEL_TYPE + " ASC";
        Uri panelsUri = PTContract.PanelsWeeks.buildPanelsUri();
        return new CursorLoader(getApplicationContext(), panelsUri, PANELTYPES_COLUMNS, null, null, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ArrayList<String> panelTypes = new ArrayList<>();
        data.moveToFirst();
        while(!data.isAfterLast()) {
            panelTypes.add(data.getString(data.getColumnIndex(PTContract.PanelsWeeks.COLUMN_PANEL_TYPE)));
            data.moveToNext();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, panelTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _panelTypesSpinner.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //TODO ?
    }
}
