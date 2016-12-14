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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ipsos.cpm.ipsospt.Data.PTContract;

public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = DetailFragment.class.getSimpleName();
    static final String DETAIL_URI = "URI";
    private Uri _uri;

    private static final int DETAIL_LOADER = 0;

    private static final String[] DETAIL_COLUMNS = {
            PTContract.Fam.TABLE_NAME + "." + PTContract.Fam._ID,
            PTContract.Fam.COLUMN_FAM_CODE,
            PTContract.Fam.COLUMN_FAM_NAME,
            PTContract.Fam.COLUMN_CITY,
            PTContract.Fam.COLUMN_TOWN,
            PTContract.Fam.COLUMN_DISTRICT,
            PTContract.Fam.COLUMN_STREET,
            PTContract.Fam.COLUMN_ROAD,
            PTContract.Fam.COLUMN_HOUSE_NO,
            PTContract.Fam.COLUMN_DOOR_NO,
            PTContract.Fam.COLUMN_PHONE,
            PTContract.Fam.COLUMN_PHONE2,
            PTContract.Fam.COLUMN_ACTIVE,
            PTContract.Fam.COLUMN_AVP,
            PTContract.Fam.COLUMN_ALK,
            PTContract.Fam.COLUMN_SP,
            PTContract.Fam.COLUMN_EK_ALK,
            PTContract.Fam.COLUMN_BABY,
            PTContract.Fam.COLUMN_HP,
            PTContract.Fam.COLUMN_VISIT_DAY,
            PTContract.Fam.COLUMN_POINT
    };

    public static final int COL_ID = 0;
    public static final int COL_FAM_CODE = 1;
    public static final int COL_FAM_NAME = 2;
    public static final int COL_CITY = 3;
    public static final int COL_TOWN = 4;
    public static final int COL_DISTRICT = 5;
    public static final int COL_STREET = 6;
    public static final int COL_ROAD = 7;
    public static final int COL_HOUSE_NO = 8;
    public static final int COL_DOOR_NO = 9;
    public static final int COL_PHONE = 10;
    public static final int COL_PHONE2 = 11;
    public static final int COL_ACTIVE = 12;
    public static final int COL_AVP = 13;
    public static final int COL_ALK = 14;
    public static final int COL_SP = 15;
    public static final int COL_EK_ALK = 16;
    public static final int COL_BABY = 17;
    public static final int COL_HP = 18;
    public static final int COL_VISIT_DAY = 19;
    public static final int COL_POINT = 20;

    private TextView _famCodeTextView;
    private TextView _cityTextView;
    private TextView _townTextView;
    private TextView _districtTextView;
    private TextView _streetTextView;
    private TextView _roadTextView;
    private TextView _houseNoTextView;
    private TextView _doorNoTextView;
    private TextView _phoneTextView;
    private TextView _phone2TextView;
    private CheckBox _activeCheckBox;
    private CheckBox _avpCheckBox;
    private CheckBox _alkCheckBox;
    private CheckBox _spCheckBox;
    private CheckBox _ekAlkCheckBox;
    private CheckBox _babyCheckBox;
    private CheckBox _hpCheckBox;
    private TextView _visitDayTextView;
    private TextView _pointTextView;
    //private Button _indList;
    private Button _panels;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null)
            _uri = arguments.getParcelable(DetailFragment.DETAIL_URI);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        _famCodeTextView = (TextView) rootView.findViewById(R.id.famDetailFamCode);
        //_cityTextView = (TextView) rootView.findViewById(R.id.famDetailCity);
        _townTextView = (TextView) rootView.findViewById(R.id.famDetailTown);
        _districtTextView = (TextView) rootView.findViewById(R.id.famDetailDistrict);
        _streetTextView = (TextView) rootView.findViewById(R.id.famDetailStreet);
        _roadTextView = (TextView) rootView.findViewById(R.id.famDetailRoad);
        _houseNoTextView = (TextView) rootView.findViewById(R.id.famDetailHouseNo);
        _doorNoTextView = (TextView) rootView.findViewById(R.id.famDetailDoorNo);
        _phoneTextView = (TextView) rootView.findViewById(R.id.famDetailPhone);
        _phone2TextView = (TextView) rootView.findViewById(R.id.famDetailPhone2);
        //_activeCheckBox = (CheckBox) rootView.findViewById(R.id.famDetailActive);
        _avpCheckBox = (CheckBox) rootView.findViewById(R.id.famDetailAVP);
        _alkCheckBox = (CheckBox) rootView.findViewById(R.id.famDetailAlkol);
        _spCheckBox = (CheckBox) rootView.findViewById(R.id.famDetailSP);
        _ekAlkCheckBox = (CheckBox) rootView.findViewById(R.id.famDetailEkAlkol);
        _babyCheckBox = (CheckBox) rootView.findViewById(R.id.famDetailBaby);
        _hpCheckBox = (CheckBox) rootView.findViewById(R.id.famDetailHP);
        //_visitDayTextView = (TextView) rootView.findViewById(R.id.famDetailVisitDay);
        _pointTextView = (TextView) rootView.findViewById(R.id.famDetailPoint);
        //_indList = (Button) rootView.findViewById(R.id.famDetailIndListButton);
        _panels = (Button) rootView.findViewById(R.id.famDetailPanelsButton);

//        _indList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //((IndListFragment.Callback) getActivity()).onItemSelected(PTContract.Ind.buildIndUriWithFamCode(_famCodeTextView.getText().subSequence(0, 7).toString()));
//            }
//        });

        _panels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO panel listesini ac

            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //Don't get that but they wrote this way...
        if (null != _uri)
            return new CursorLoader(getActivity(), _uri, DETAIL_COLUMNS, null, null, null);
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            String famCodeName = data.getString(COL_FAM_CODE) + " - " + data.getString(COL_FAM_NAME);
            _famCodeTextView.setText(famCodeName);

            //String city = data.getString(COL_CITY);
            //_cityTextView.setText(city);

            String town = data.getString(COL_TOWN);
            _townTextView.setText(town);

            String district = data.getString(COL_DISTRICT);
            _districtTextView.setText(district);

            String street = data.getString(COL_STREET);
            _streetTextView.setText(street);

            String road = data.getString(COL_ROAD);
            _roadTextView.setText(road);

            String houseNo = data.getString(COL_HOUSE_NO);
            _houseNoTextView.setText(houseNo);

            String doorNo = data.getString(COL_DOOR_NO);
            _doorNoTextView.setText(doorNo);

            String phone = data.getString(COL_PHONE);
            _phoneTextView.setText(phone);

            String phone2 = data.getString(COL_PHONE2);
            _phone2TextView.setText(phone2);

            //boolean active = data.getInt(COL_ACTIVE) == 1;
            //_activeCheckBox.setChecked(active);

            int point = data.getInt(COL_POINT);
            _pointTextView.setText(Integer.toString(point));

            boolean avp = data.getInt(COL_AVP) == 1;
            _avpCheckBox.setChecked(avp);

            boolean alk = data.getInt(COL_ALK) == 1;
            _alkCheckBox.setChecked(alk);

            boolean sp = data.getInt(COL_SP) == 1;
            _spCheckBox.setChecked(sp);

            boolean ekAlkol = data.getInt(COL_EK_ALK) == 1;
            _ekAlkCheckBox.setChecked(ekAlkol);

            boolean baby = data.getInt(COL_BABY) == 1;
            _babyCheckBox.setChecked(baby);

            boolean hp = data.getInt(COL_HP) == 1;
            _hpCheckBox.setChecked(hp);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
