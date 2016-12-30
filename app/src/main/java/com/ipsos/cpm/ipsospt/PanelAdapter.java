package com.ipsos.cpm.ipsospt;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.CursorSwipeAdapter;

/**
 * Created by zencifil on 26/12/2016.
 */

public class PanelAdapter extends CursorSwipeAdapter {

    private SwipeLayout _swipeLayout;

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.list_item_panel_swipe;
    }

    @Override
    public void closeAllItems() {

    }

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
    public void bindView(View view, final Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        if (viewHolder == null || viewHolder.panelStatusView == null || viewHolder.famCodeView == null)
            return;

        _swipeLayout = (SwipeLayout) view.findViewById(getSwipeLayoutResourceId(cursor.getPosition()));
        _swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        _swipeLayout.addDrag(SwipeLayout.DragEdge.Left, _swipeLayout.findViewById(R.id.bottom_wrapper_child1));
        _swipeLayout.addDrag(SwipeLayout.DragEdge.Right, _swipeLayout.findViewById(R.id.bottom_wrapper_child2));

        ImageView check1 = (ImageView) view.findViewById(R.id.pt2house_image_view);
        check1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context).setTitle("Form birakildi onayi")
                        .setMessage("Form birakildi olarak isaretlenecek, emin misiniz?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(context, "Form birakildi", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });
        ImageView check2 = (ImageView) view.findViewById(R.id.house2pt_image_view);
        check2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context).setTitle("Form alindi onayi")
                        .setMessage("Form alindi olarak isaretlenecek, emin misiniz?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(context, "Form alindi", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

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
