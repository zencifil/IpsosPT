package com.ipsos.cpm.ipsospt;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

class FamListAdapter extends CursorAdapter {

    private static class ViewHolder {
        final TextView famCodeView;
        final TextView famNameView;
        final TextView addressView;

        ViewHolder (View view) {
            famCodeView = (TextView) view.findViewById(R.id.fam_code_list_item);
            famNameView = (TextView) view.findViewById(R.id.fam_name_list_item);
            addressView = (TextView) view.findViewById(R.id.fam_address_list_item);
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
    }

    private String joinAddressColumns(Cursor cursor) {
        return cursor.getString(FamListFragment.COL_NEIGHBORHOOD) + " " +
                cursor.getString(FamListFragment.COL_ADDRESS) + " " +
                cursor.getString(FamListFragment.COL_DISTRICT);
    }
}
