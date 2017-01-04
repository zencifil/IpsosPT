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
            PTContract.Fam.COLUMN_COUNTRY_CODE,
            PTContract.Fam.COLUMN_FAM_CODE,
            PTContract.Fam.COLUMN_FAM_NAME,
            PTContract.Fam.COLUMN_REG_BEG,
            PTContract.Fam.COLUMN_DISTRICT,
            PTContract.Fam.COLUMN_PROVINCE,
            PTContract.Fam.COLUMN_NEIGHBORHOOD,
            PTContract.Fam.COLUMN_ADDRESS,
            PTContract.Fam.COLUMN_LANDLINE,
            PTContract.Fam.COLUMN_WORKLINE,
            PTContract.Fam.COLUMN_CELLULAR,
            PTContract.Fam.COLUMN_AVP,
            PTContract.Fam.COLUMN_ALK,
            PTContract.Fam.COLUMN_SP,
            PTContract.Fam.COLUMN_EK_ALK,
            PTContract.Fam.COLUMN_BABY,
            PTContract.Fam.COLUMN_POINT
    };

    public static final int COL_ID = 0;
    public static final int COL_COUNTRY_CODE = 1;
    public static final int COL_FAM_CODE = 2;
    public static final int COL_FAM_NAME = 3;
    public static final int COL_REG_BEG = 4;
    public static final int COL_DISTRICT = 5;
    public static final int COL_PROVINCE = 6;
    public static final int COL_NEIGHBORHOOD = 7;
    public static final int COL_ADDRESS = 8;
    public static final int COL_LANDLINE = 9;
    public static final int COL_WORKLINE = 10;
    public static final int COL_CELLULAR = 11;
    public static final int COL_AVP = 12;
    public static final int COL_ALK = 13;
    public static final int COL_SP = 14;
    public static final int COL_EK_ALK = 15;
    public static final int COL_BABY = 16;
    public static final int COL_POINT = 17;

    public static final String IND_LIST_BUTTON = "ind_list";
    //public static final String FAM_PANELS_BUTTON = "panel_list";

    private TextView _famCodeTextView;
    private TextView _regBegTextView;
    private TextView _districtTextView;
    private TextView _neighborhoodTextView;
    private TextView _addressTextView;
    private TextView _landlineTextView;
    private TextView _worklineTextView;
    private TextView _cellularTextView;
    private CheckBox _avpCheckBox;
    private CheckBox _alkCheckBox;
    private CheckBox _spCheckBox;
    private CheckBox _ekAlkCheckBox;
    private CheckBox _babyCheckBox;
    private TextView _pointTextView;

    OnFamDetailButtonClickedListener _buttonClickedListener;

    public interface OnFamDetailButtonClickedListener {
        void onFamDetailButtonClicked(String buttonTag, String famCode);
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
        _regBegTextView = (TextView) rootView.findViewById(R.id.famDetailRegBeg);
        _districtTextView = (TextView) rootView.findViewById(R.id.famDetailDistrict);
        _neighborhoodTextView = (TextView) rootView.findViewById(R.id.famDetailNeighborhood);
        _addressTextView = (TextView) rootView.findViewById(R.id.famDetailAddress);
        _landlineTextView = (TextView) rootView.findViewById(R.id.famDetailLandLine);
        _worklineTextView = (TextView) rootView.findViewById(R.id.famDetailWorkLine);
        _cellularTextView = (TextView) rootView.findViewById(R.id.famDetailCellular);
        _avpCheckBox = (CheckBox) rootView.findViewById(R.id.famDetailAVP);
        _alkCheckBox = (CheckBox) rootView.findViewById(R.id.famDetailAlkol);
        _spCheckBox = (CheckBox) rootView.findViewById(R.id.famDetailSP);
        _ekAlkCheckBox = (CheckBox) rootView.findViewById(R.id.famDetailEkAlkol);
        _babyCheckBox = (CheckBox) rootView.findViewById(R.id.famDetailBaby);
        _pointTextView = (TextView) rootView.findViewById(R.id.famDetailPoint);

        Button indList = (Button) rootView.findViewById(R.id.famDetailIndListButton);
        Button panels = (Button) rootView.findViewById(R.id.famDetailPanelsButton);

        indList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _buttonClickedListener.onFamDetailButtonClicked(IND_LIST_BUTTON, _famCodeTextView.getText().subSequence(0, 7).toString());
            }
        });

        panels.setOnClickListener(new View.OnClickListener() {
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
                _regBegTextView.setText(data.getString(COL_REG_BEG));
                _districtTextView.setText(data.getString(COL_DISTRICT));
                _neighborhoodTextView.setText(data.getString(COL_NEIGHBORHOOD));
                _addressTextView.setText(data.getString(COL_ADDRESS));

                String landline = data.getString(COL_LANDLINE);
                if (landline != null && !landline.startsWith("0") && landline.length() == 10)
                    landline = "0" + landline;
                _landlineTextView.setText(landline);

                String workline = data.getString(COL_WORKLINE);
                if (workline != null && !workline.startsWith("0") && workline.length() == 10)
                    workline = "0" + workline;
                _worklineTextView.setText(workline);

                String cellular = data.getString(COL_CELLULAR);
                if (cellular != null && !cellular.startsWith("0") && cellular.length() == 10)
                    cellular = "0" + cellular;
                _cellularTextView.setText(cellular);

                _pointTextView.setText(data.getString(COL_POINT));

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
