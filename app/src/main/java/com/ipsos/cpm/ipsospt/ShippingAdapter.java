package com.ipsos.cpm.ipsospt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.ipsos.cpm.ipsospt.data.PTContract;
import com.ipsos.cpm.ipsospt.helper.Constants;

import java.util.ArrayList;

class ShippingAdapter extends CursorAdapter {

    private static class ShippingViewHolder {
        final CheckedTextView listItem;

        ShippingViewHolder(View view) {
            listItem = (CheckedTextView) view.findViewById(R.id.list_item_checked_text);
        }
    }

    ShippingAdapter(Context context, Cursor c, int flags) {
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

        String listItemText;
        String famCode = cursor.getString(ShippingActivity.COL_FAM_CODE);
        int indCode = cursor.getInt(ShippingActivity.COL_IND_CODE);
        String indName = cursor.getString(ShippingActivity.COL_IND_NAME);
        if (indCode == 0)
            listItemText = famCode;
        else
            listItemText = Integer.toString(indCode) + " - " + indName;
        listItemText += " - " + cursor.getString(ShippingActivity.COL_PANEL_TYPE);
        listItemText += " - " + cursor.getString(ShippingActivity.COL_WEEK_DESC);

        viewHolder.listItem.setText(listItemText);
    }

    void onItemSelect(View view) {
        ShippingViewHolder viewHolder = (ShippingViewHolder) view.getTag();
        viewHolder.listItem.toggle();
    }

    void sendItems(Context context, ArrayList items) {
        Uri uri = PTContract.Panel.CONTENT_URI;
        ContentValues values = new ContentValues();
        String where;
        String[] args;
        for (Object item : items) {
            values.put(PTContract.Panel.COLUMN_WEEK_CHECK, Constants.SHIPPED_FLAG);
            where = PTContract.Panel._ID + " = ? ";
            args = new String[]{ item.toString() }; //_panelType, Integer.toString(_weekCode), _famCode, Integer.toString(indCode) };
            context.getContentResolver().update(uri, values, where, args);
        }
    }
}
