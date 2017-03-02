package com.ipsos.cpm.ipsospt;

import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ipsos.cpm.ipsospt.data.PTContract;

import java.util.ArrayList;
import java.util.HashMap;

public class ShippingActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private ShippingAdapter _shippingAdapter;
    private ArrayList _selectedItems;
    private ListView _listView;
    private Spinner _panelTypesSpinner;
    private Spinner _weeksSpinner;
    private String _panelType;
    private int _weekCode;
    private HashMap<String, Integer> _weeks;
    private static final int SHIPPING_LOADER = 0;
    private static final int PANELTYPE_LOADER = 1;
    private static final int WEEK_LOADER = 2;
    private ShippingActivity _this;

    //specify the columns we need
    private static final String[] SHIPPING_COLUMNS = {
            PTContract.Panel.TABLE_NAME + "." + PTContract.Fam._ID,
            PTContract.Panel.TABLE_NAME + "." + PTContract.Panel.COLUMN_PANEL_TYPE,
            PTContract.Panel.TABLE_NAME + "." + PTContract.Panel.COLUMN_FAM_CODE,
            PTContract.Panel.TABLE_NAME + "." + PTContract.Panel.COLUMN_IND_CODE,
            PTContract.Ind.TABLE_NAME + "." + PTContract.Ind.COLUMN_IND_NAME,
            PTContract.Panel.TABLE_NAME + "." + PTContract.Panel.COLUMN_WEEK_CODE,
            PTContract.PanelWeek.TABLE_NAME + "." + PTContract.PanelWeek.COLUMN_WEEK_DESC,
            PTContract.Panel.TABLE_NAME + "." + PTContract.Panel.COLUMN_WEEK_CHECK
    };

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
    static final int COL_FAM_CODE = 2;
    static final int COL_IND_CODE = 3;
    static final int COL_IND_NAME = 4;
    static final int COL_WEEK_CODE = 5;
    static final int COL_WEEK_DESC = 6;
    static final int COL_WEEK_CHECK = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);

        _this = this;

        _weeks = new HashMap<>();
        _selectedItems = new ArrayList();
        _shippingAdapter = new ShippingAdapter(this, null, 0);

        _panelTypesSpinner = (Spinner) findViewById(R.id.panel_types_shipping_spinner);
        _weeksSpinner = (Spinner) findViewById(R.id.panel_weeks_shipping_spinner);

        _listView = (ListView) findViewById(R.id.shipping_list_fragment);
        _listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        _listView.setAdapter(_shippingAdapter);

        _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                _shippingAdapter.onItemSelect(view);
                if (_selectedItems.contains(l))
                    _selectedItems.remove(l);
                else
                    _selectedItems.add(l);
            }
        });

        Button sendButton = (Button) findViewById(R.id.send_shipping_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_selectedItems.size() == 0)
                    Toast.makeText(ShippingActivity.this, R.string.form_shipping_nothing_selected, Toast.LENGTH_LONG).show();
                else
                    new AlertDialog.Builder(_this).setTitle(R.string.form_shipping_confirmation_title)
                            .setMessage(R.string.form_shipping_send_confirmation)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    _shippingAdapter.sendItems(getBaseContext(), _selectedItems);
                                    restartCursorLoader(SHIPPING_LOADER);
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
            }
        });

        Button sendAllButton = (Button) findViewById(R.id.send_all_shipping_button);
        sendAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(_this).setTitle(R.string.form_shipping_confirmation_title)
                        .setMessage(R.string.form_shipping_send_all_confirmation)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int count = _shippingAdapter.getCount();
                                Cursor item;
                                long id;
                                for (int c = 0; c < count; c++) {
                                    item = (Cursor) _shippingAdapter.getItem(c);
                                    id = item.getLong(COL_ID);
                                    if (!_selectedItems.contains(id))
                                        _selectedItems.add(id);
                                }
                                _shippingAdapter.sendItems(getBaseContext(), _selectedItems);
                                restartCursorLoader(SHIPPING_LOADER);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        _panelTypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > -1) {
                    _panelType = adapterView.getItemAtPosition(i).toString();
                    _this.restartCursorLoader(WEEK_LOADER);
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
                _weekCode = _weeks.get(weekDesc);
                _this.restartCursorLoader(SHIPPING_LOADER);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getSupportLoaderManager().initLoader(SHIPPING_LOADER, null, this);
        getSupportLoaderManager().initLoader(PANELTYPE_LOADER, null, this);
    }

    public void restartCursorLoader(int loader) {
        getSupportLoaderManager().restartLoader(loader, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String sortOrder;
        switch (id) {
            case SHIPPING_LOADER:
                sortOrder = PTContract.Panel.TABLE_NAME + "." + PTContract.Panel.COLUMN_FAM_CODE + " ASC ";
                Uri shippingUri = PTContract.Panel.buildShippingUri();
                return new CursorLoader(this, shippingUri, SHIPPING_COLUMNS, null, null, sortOrder);
            case PANELTYPE_LOADER:
                sortOrder = PTContract.PanelWeek.COLUMN_PANEL_TYPE + " ASC";
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
        if (loader.getId() == SHIPPING_LOADER) {
            _shippingAdapter.swapCursor(data);
        }
        else if (loader.getId() == PANELTYPE_LOADER) {
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

        if (data.getCount() == 0)
            Toast.makeText(_this, R.string.no_data_found, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        _shippingAdapter.swapCursor(null);
    }

}
