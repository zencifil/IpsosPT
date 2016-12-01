package com.ipsos.savascilve.ipsospt;

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
    }
}
