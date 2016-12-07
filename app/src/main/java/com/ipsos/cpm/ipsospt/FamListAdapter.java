package com.ipsos.cpm.ipsospt;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by zencifil on 29/11/2016.
 */

public class FamListAdapter extends CursorAdapter {

    public static class ViewHolder {
        public final TextView famCodeView;
        public final TextView famNameView;
        public final TextView addressView;

        public ViewHolder (View view) {
            famCodeView = (TextView) view.findViewById(R.id.fam_code_list_item);
            famNameView = (TextView) view.findViewById(R.id.fam_name_list_item);
            addressView = (TextView) view.findViewById(R.id.fam_address_list_item);
        }
    }

    public FamListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_fam, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        String famCode = cursor.getString(FamListFragment.COL_FAM_CODE);
        viewHolder.famCodeView.setText(famCode);
        String famName = cursor.getString(FamListFragment.COL_FAM_NAME);
        viewHolder.famNameView.setText(famName);
        String address = joinAddressColumns(cursor);
        viewHolder.addressView.setText(address);
    }

    private String joinAddressColumns(Cursor cursor) {
        String city = cursor.getString(FamListFragment.COL_CITY);
        String town = cursor.getString(FamListFragment.COL_TOWN);
        String district = cursor.getString(FamListFragment.COL_DISTRICT);
        String street = cursor.getString(FamListFragment.COL_STREET);
        String road = cursor.getString(FamListFragment.COL_ROAD);
        String houseNo = cursor.getString(FamListFragment.COL_HOUSE_NO);
        String doorNo = cursor.getString(FamListFragment.COL_DOOR_NO);

        String address = district + " ";
        if (street != null && !street.isEmpty()) {
            if (!street.contains("Cad.") && !street.contains("CAD.") && !street.contains("Caddesi") && !street.contains("CADDESI"))
                street += " Cad.";
            address += street + " ";
        }
        if (road != null && !road.isEmpty()) {
            if (!road.contains("Sok.") && !road.contains("SOK.") && !road.contains("Sokak") && !road.contains("SOKAK") && !road.contains("Sokağı") && !road.contains("SOKAĞI"))
                road += " Sok.";
            address += road + " ";
        }
        if (houseNo != null && !houseNo.isEmpty()) {
            if (houseNo.matches("^\\d+$"))
                houseNo = "No: " + houseNo;
            address += houseNo + " ";
        }
        if (doorNo != null && !doorNo.isEmpty()) {
            if (doorNo.matches("^\\d+$"))
                doorNo = "D: " + doorNo;
            address += doorNo + " ";
        }

        address += town + " ";
        address += city;

        return address;
    }
}
