package com.ipsos.cpm.ipsospt;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

class ShippingAdapter extends CursorAdapter {

    static class ShippingViewHolder {
        final TextView famCodeView;
        final TextView panelTypeView;
        final TextView weekDescView;

        ShippingViewHolder (View view) {
            famCodeView = (TextView) view.findViewById(R.id.fam_code_list_item_shipping);
            panelTypeView = (TextView) view.findViewById(R.id.panel_type_list_item_shipping);
            weekDescView = (TextView) view.findViewById(R.id.week_code_list_item_shipping);
        }
    }

    public ShippingAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_shipping, parent, false);

        ShippingViewHolder viewHolder = new ShippingViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ShippingViewHolder viewHolder = (ShippingViewHolder) view.getTag();

        String famCode = cursor.getString(ShippingFragment.COL_FAM_CODE);
        int indCode = cursor.getInt(ShippingFragment.COL_IND_CODE);
        String indName = cursor.getString(ShippingFragment.COL_IND_NAME);
        viewHolder.famCodeView.setText(famCode);
        String famName = cursor.getString(ShippingFragment.COL_PANEL_TYPE);
        viewHolder.panelTypeView.setText(famName);
        String address = cursor.getString(ShippingFragment.COL_WEEK_DESC);
        viewHolder.weekDescView.setText(address);
    }

}
