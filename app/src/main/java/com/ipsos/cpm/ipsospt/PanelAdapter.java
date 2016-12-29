package com.ipsos.cpm.ipsospt;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by zencifil on 26/12/2016.
 */

public class PanelAdapter extends CursorAdapter {

    public static class ViewHolder {
        public final TextView famCodeView;
        public final TextView panelStatusView;

        public ViewHolder (View view) {
            famCodeView = (TextView) view.findViewById(R.id.fam_code_list_item_panel);
            panelStatusView = (TextView) view.findViewById(R.id.panel_status_list_item_panel);
        }
    }

    public PanelAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_panel, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        if (viewHolder == null || viewHolder.panelStatusView == null || viewHolder.famCodeView == null)
            return;

        String famCode = cursor.getString(PanelFragment.COL_FAM_CODE);
        int indCode = cursor.getInt(PanelFragment.COL_IND_CODE);
        String indName = cursor.getString(PanelFragment.COL_IND_NAME);
        if (indCode == 0 || indName == null)
            viewHolder.famCodeView.setText(famCode);
        else
            viewHolder.famCodeView.setText(Integer.toString(indCode) + " - " + indName);

        String status = context.getString(R.string.form_panel_list_item_none);
        int weekStatus = cursor.getInt(PanelFragment.COL_WEEK_CHECK);
        switch (weekStatus) {
            case 0:
                status = context.getString(R.string.form_panel_list_item_0);
                //TODO backcolor may change
                break;
            case 1:
                status = context.getString(R.string.form_panel_list_item_1);
                break;
            case 2:
                status = context.getString(R.string.form_panel_list_item_2);
                break;
        }
        viewHolder.panelStatusView.setText(status);
    }
}
