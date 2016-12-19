package com.ipsos.cpm.ipsospt;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by zencifil on 18/12/2016.
 */

public class IndListAdapter extends CursorAdapter {

    public static class IndViewHolder {
        public final TextView indCodeView;
        public final TextView indNameView;

        public IndViewHolder (View view) {
            indCodeView = (TextView) view.findViewById(R.id.ind_code_list_item);
            indNameView = (TextView) view.findViewById(R.id.ind_name_list_item);
        }
    }

    public IndListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_ind, parent, false);

        IndViewHolder viewHolder = new IndViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        IndViewHolder viewHolder = (IndViewHolder) view.getTag();

        String indCode = cursor.getString(IndListFragment.COL_IND_CODE);
        viewHolder.indCodeView.setText(indCode);
        String indName = cursor.getString(IndListFragment.COL_IND_NAME);
        viewHolder.indNameView.setText(indName);
    }
}
