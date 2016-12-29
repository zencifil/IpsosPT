package com.ipsos.cpm.ipsospt;

import android.content.Context;
import android.content.Intent;
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

public class FamDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = FamDetailFragment.class.getSimpleName();
    static final String DETAIL_URI = "URI";
    private Uri _famUri;
    private String _famCode;

    private static final int DETAIL_LOADER = 0;

    private static final String[] DETAIL_COLUMNS = {
            PTContract.Fam.TABLE_NAME + "." + PTContract.Fam._ID,
            PTContract.Fam.COLUMN_FAM_CODE,
            PTContract.Fam.COLUMN_FAM_NAME,
            PTContract.Fam.COLUMN_TOWN,
            PTContract.Fam.COLUMN_DISTRICT,
            PTContract.Fam.COLUMN_STREET,
            PTContract.Fam.COLUMN_ROAD,
            PTContract.Fam.COLUMN_HOUSE_NO,
            PTContract.Fam.COLUMN_DOOR_NO,
            PTContract.Fam.COLUMN_PHONE,
            PTContract.Fam.COLUMN_PHONE2,
            PTContract.Fam.COLUMN_AVP,
            PTContract.Fam.COLUMN_ALK,
            PTContract.Fam.COLUMN_SP,
            PTContract.Fam.COLUMN_EK_ALK,
            PTContract.Fam.COLUMN_BABY,
            PTContract.Fam.COLUMN_POINT
    };

    public static final int COL_ID = 0;
    public static final int COL_FAM_CODE = 1;
    public static final int COL_FAM_NAME = 2;
    public static final int COL_TOWN = 3;
    public static final int COL_DISTRICT = 4;
    public static final int COL_STREET = 5;
    public static final int COL_ROAD = 6;
    public static final int COL_HOUSE_NO = 7;
    public static final int COL_DOOR_NO = 8;
    public static final int COL_PHONE = 9;
    public static final int COL_PHONE2 = 10;
    public static final int COL_AVP = 11;
    public static final int COL_ALK = 12;
    public static final int COL_SP = 13;
    public static final int COL_EK_ALK = 14;
    public static final int COL_BABY = 15;
    public static final int COL_POINT = 16;

    public static final String IND_LIST_BUTTON = "ind_list";
    public static final String FAM_PANELS_BUTTON = "panel_list";

    private TextView _famCodeTextView;
    private TextView _townTextView;
    private TextView _districtTextView;
    private TextView _streetTextView;
    private TextView _roadTextView;
    private TextView _houseNoTextView;
    private TextView _doorNoTextView;
    private TextView _phoneTextView;
    private TextView _phone2TextView;
    private CheckBox _avpCheckBox;
    private CheckBox _alkCheckBox;
    private CheckBox _spCheckBox;
    private CheckBox _ekAlkCheckBox;
    private CheckBox _babyCheckBox;
    private CheckBox _hpCheckBox;
    private TextView _pointTextView;
    private Button _indList;
    private Button _panels;

    OnFamDetailButtonClickedListener _buttonClickedListener;

    public interface OnFamDetailButtonClickedListener {
        public void onFamDetailButtonClicked(String buttonTag, String famCode);
    }

    public FamDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            _famUri = arguments.getParcelable(FamDetailFragment.DETAIL_URI);
        }

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_fam_detail, container, false);
        _famCodeTextView = (TextView) rootView.findViewById(R.id.famDetailFamCode);
        _townTextView = (TextView) rootView.findViewById(R.id.famDetailTown);
        _districtTextView = (TextView) rootView.findViewById(R.id.famDetailDistrict);
        _streetTextView = (TextView) rootView.findViewById(R.id.famDetailStreet);
        _roadTextView = (TextView) rootView.findViewById(R.id.famDetailRoad);
        _houseNoTextView = (TextView) rootView.findViewById(R.id.famDetailHouseNo);
        _doorNoTextView = (TextView) rootView.findViewById(R.id.famDetailDoorNo);
        _phoneTextView = (TextView) rootView.findViewById(R.id.famDetailPhone);
        _phone2TextView = (TextView) rootView.findViewById(R.id.famDetailPhone2);
        _avpCheckBox = (CheckBox) rootView.findViewById(R.id.famDetailAVP);
        _alkCheckBox = (CheckBox) rootView.findViewById(R.id.famDetailAlkol);
        _spCheckBox = (CheckBox) rootView.findViewById(R.id.famDetailSP);
        _ekAlkCheckBox = (CheckBox) rootView.findViewById(R.id.famDetailEkAlkol);
        _babyCheckBox = (CheckBox) rootView.findViewById(R.id.famDetailBaby);
        _pointTextView = (TextView) rootView.findViewById(R.id.famDetailPoint);
        _indList = (Button) rootView.findViewById(R.id.famDetailIndListButton);
        _panels = (Button) rootView.findViewById(R.id.famDetailPanelsButton);

        _indList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _buttonClickedListener.onFamDetailButtonClicked(IND_LIST_BUTTON, _famCodeTextView.getText().subSequence(0, 7).toString());
            }
        });

        _panels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PanelActivity.class);
                intent.putExtra("FAM_CODE", _famCode);
                startActivity(intent);
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
        if (id == DETAIL_LOADER) {
            if (null != _famUri)
                return new CursorLoader(getActivity(), _famUri, DETAIL_COLUMNS, null, null, null);
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == DETAIL_LOADER) {
            if (data != null && data.moveToFirst()) {
                _famCode = data.getString(COL_FAM_CODE);
                String famCodeName = _famCode + " - " + data.getString(COL_FAM_NAME);
                _famCodeTextView.setText(famCodeName);

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
                if (!phone.startsWith("0") && phone.length() == 10)
                    phone = "0" + phone;
                _phoneTextView.setText(phone);

                String phone2 = data.getString(COL_PHONE2);
                if (!phone2.startsWith("0") && phone2.length() == 10)
                    phone2 = "0" + phone2;
                _phone2TextView.setText(phone2);

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
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //if (loader.getId() == IND_LIST_LOADER)
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            _buttonClickedListener = (OnFamDetailButtonClickedListener) context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnHeadlineSelectedListener");
        }
    }
}
