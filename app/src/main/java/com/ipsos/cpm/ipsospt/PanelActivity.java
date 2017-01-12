package com.ipsos.cpm.ipsospt;

import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ipsos.cpm.ipsospt.data.PTContract;
import com.ipsos.cpm.ipsospt.helper.Constants;

import java.util.ArrayList;
import java.util.HashMap;

public class PanelActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Spinner _panelTypesSpinner;
    private Spinner _weeksSpinner;
    private String _panelType;
    private HashMap<String, Integer> _weeks;
    private String _famCode;

    private PanelActivity _self = this;

    private static final int PANELTYPE_LOADER = 0;
    private static final int WEEK_LOADER = 1;

    private static final String[] PANELTYPES_COLUMNS = {
            PTContract.PanelWeek.TABLE_NAME + "." + PTContract.PanelWeek._ID,
            PTContract.PanelWeek.TABLE_NAME + "." + PTContract.PanelWeek.COLUMN_PANEL_TYPE
    };

    private static final String[] WEEK_COLUMNS = {
            PTContract.PanelWeek.TABLE_NAME + "." + PTContract.PanelWeek._ID,
            PTContract.PanelWeek.TABLE_NAME + "." + PTContract.PanelWeek.COLUMN_WEEK_CODE,
            PTContract.PanelWeek.TABLE_NAME + "." + PTContract.PanelWeek.COLUMN_WEEK_DESC
    };

    static final int COL_ID = 0;
    static final int COL_PANEL_TYPE = 1;

    static final int COL_W_ID = 0;
    static final int COL_WEEK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);

        _weeks = new HashMap<>();
        _famCode = getIntent().getStringExtra(Constants.EXTRA_FAMCODE);
        _panelTypesSpinner = (Spinner) findViewById(R.id.panel_types_spinner);
        _weeksSpinner = (Spinner) findViewById(R.id.panel_weeks_spinner);

        getSupportLoaderManager().initLoader(PANELTYPE_LOADER, null, this);

        _panelTypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > -1) {
                    _panelType = adapterView.getItemAtPosition(i).toString();
                    getSupportLoaderManager().initLoader(WEEK_LOADER, null, _self);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Do nothing
            }
        });

        _weeksSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String weekDesc = adapterView.getItemAtPosition(i).toString();
                int weekCode = _weeks.get(weekDesc);
                PanelFragment panelFragment = (PanelFragment) getSupportFragmentManager().findFragmentById(R.id.panel_fragment);
                if (panelFragment == null)
                    return;
                panelFragment.setFamCode(_famCode);
                panelFragment.setPanelType(_panelType);
                panelFragment.setWeekCode(weekCode);
                panelFragment.restartCursorLoader();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String sortOrder;
        switch (id) {
            case PANELTYPE_LOADER:
                sortOrder = PTContract.PanelWeek.COLUMN_PANEL_TYPE + " ASC";
                //Uri panelsUri = PTContract.PanelsWeeks.buildPanelsUri(_famCode);
                //TODO make it famCode dependant
                Uri panelsUri = PTContract.PanelWeek.buildPanelsUri();
                return new CursorLoader(getApplicationContext(), panelsUri, PANELTYPES_COLUMNS, null, null, sortOrder);
            case WEEK_LOADER:
                sortOrder = PTContract.PanelWeek.COLUMN_WEEK_CODE + " DESC";
                Uri weeksUri = PTContract.PanelWeek.buildWeeksUri(_panelType);
                return new CursorLoader(getApplicationContext(), weeksUri, WEEK_COLUMNS, null, null, sortOrder);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == PANELTYPE_LOADER) {
            ArrayList<String> panelTypes = new ArrayList<>();
            data.moveToFirst();
            while (!data.isAfterLast()) {
                panelTypes.add(data.getString(data.getColumnIndex(PTContract.PanelWeek.COLUMN_PANEL_TYPE)));
                data.moveToNext();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, panelTypes);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            _panelTypesSpinner.setAdapter(adapter);
        }
        else if (loader.getId() == WEEK_LOADER) {
            _weeks.clear();
            data.moveToFirst();
            ArrayList<String> weeksArray = new ArrayList<>();
            while (!data.isAfterLast()) {
                _weeks.put(data.getString(data.getColumnIndex(PTContract.PanelWeek.COLUMN_WEEK_DESC)),
                        data.getInt(data.getColumnIndex(PTContract.PanelWeek.COLUMN_WEEK_CODE)));
                weeksArray.add(data.getString(data.getColumnIndex(PTContract.PanelWeek.COLUMN_WEEK_DESC)));
                data.moveToNext();
            }
            ArrayAdapter<String> weeksAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, weeksArray);
            weeksAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            _weeksSpinner.setAdapter(weeksAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //TODO ?
    }
}
