package com.ipsos.cpm.ipsospt;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ipsos.cpm.ipsospt.data.PTContract;

class FamListAdapter extends CursorAdapter {

    private static class ViewHolder {
        final TextView famCodeView;
        final TextView famNameView;
        final TextView addressView;
        final TextView panelView;

        ViewHolder (View view) {
            famCodeView = (TextView) view.findViewById(R.id.fam_code_list_item);
            famNameView = (TextView) view.findViewById(R.id.fam_name_list_item);
            addressView = (TextView) view.findViewById(R.id.fam_address_list_item);
            panelView = (TextView) view.findViewById(R.id.fam_active_panels_list_item);
        }
    }

    FamListAdapter(Context context, Cursor c, int flags) {
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
        String activePanels = getActivePanels(cursor);
        viewHolder.panelView.setText(activePanels);
    }

    private String joinAddressColumns(Cursor cursor) {
        return cursor.getString(FamListFragment.COL_NEIGHBORHOOD) + " " +
                cursor.getString(FamListFragment.COL_ADDRESS) + " " +
                cursor.getString(FamListFragment.COL_DISTRICT);
    }

    private String getActivePanels(Cursor cursor) {
        int avp = cursor.getInt(FamListFragment.COL_AVP);
        int sp = cursor.getInt(FamListFragment.COL_SP);
        int alk = cursor.getInt(FamListFragment.COL_ALK);
        int baby = cursor.getInt(FamListFragment.COL_BABY);
        int ekAlk = cursor.getInt(FamListFragment.COL_EK_ALK);
        String retVal = "";
        if (avp == 1)
            retVal = "AVP - ";
        if (sp == 1)
            retVal += "SP - ";
        if (alk == 1)
            retVal += "ALK - ";
        if (baby == 1)
            retVal += "BP - ";
        if (ekAlk == 1)
            retVal += "EK ALK - ";

        retVal = retVal.substring(0, retVal.length() - 3);
        return retVal;
    }
}
