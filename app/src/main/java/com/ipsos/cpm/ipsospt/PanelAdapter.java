package com.ipsos.cpm.ipsospt;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.CursorSwipeAdapter;
import com.ipsos.cpm.ipsospt.data.PTContract;

class PanelAdapter extends CursorSwipeAdapter {

    private int _weekStatus;
    private int _weekCode;
    private String _panelType;
    private String _famCode;

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.list_item_panel_swipe;
    }

    @Override
    public void closeAllItems() {

    }

    private static class ViewHolder {
        final TextView famCodeView;
        final TextView panelStatusView;

        ViewHolder (View view) {
            famCodeView = (TextView) view.findViewById(R.id.fam_code_list_item_panel);
            panelStatusView = (TextView) view.findViewById(R.id.panel_status_list_item_panel);
        }
    }

    PanelAdapter(Context context, Cursor c, int flags) {
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

        String status = context.getString(R.string.form_panel_list_item_none);
        _weekStatus = cursor.getInt(PanelFragment.COL_WEEK_CHECK);
        final int indCode = cursor.getInt(PanelFragment.COL_IND_CODE);
        final String indName = cursor.getString(PanelFragment.COL_IND_NAME);
        _famCode = cursor.getString(PanelFragment.COL_FAM_CODE);
        _weekCode = cursor.getInt(PanelFragment.COL_WEEK_CODE);
        _panelType = cursor.getString(PanelFragment.COL_PANEL_TYPE);

        SwipeLayout swipeLayout = (SwipeLayout) view.findViewById(getSwipeLayoutResourceId(cursor.getPosition()));
        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, swipeLayout.findViewById(R.id.bottom_wrapper_child1));
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, swipeLayout.findViewById(R.id.bottom_wrapper_child2));
        swipeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (_weekStatus == 0)
                    Toast.makeText(context, R.string.form_panel_list_not_yet_left_error, Toast.LENGTH_LONG).show();
                else if (_weekStatus == 1)
                    Toast.makeText(context, R.string.form_panel_list_not_yet_taken_error, Toast.LENGTH_LONG).show();
                else if (_weekStatus == 2)
                    new AlertDialog.Builder(context).setTitle(R.string.form_panel_list_sent_confirmation_title)
                            .setMessage(R.string.form_panel_list_sent_confirmation)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    _weekStatus = 3;
                                    updateFormStatus(context, indCode);
                                    Toast.makeText(context, R.string.form_panel_list_sent, Toast.LENGTH_LONG).show();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                else if (_weekStatus == 3)
                    new AlertDialog.Builder(context).setTitle(R.string.form_panel_list_sent_confirmation_title)
                            .setMessage(R.string.form_panel_list_sent_remove_confirmation)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    _weekStatus = 2;
                                    updateFormStatus(context, indCode);
                                    Toast.makeText(context, R.string.form_panel_list_sent_remove, Toast.LENGTH_LONG).show();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                else
                    Toast.makeText(context, R.string.invalid_operation, Toast.LENGTH_LONG).show();

                return true;
            }
        });

        if (indCode == 0 || indName == null)
            viewHolder.famCodeView.setText(_famCode);
        else
            viewHolder.famCodeView.setText(_famCode + " - " + Integer.toString(indCode) + " - " + indName);

        ImageView check1 = (ImageView) view.findViewById(R.id.pt2house_image_view);
        check1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_weekStatus == 0)
                    new AlertDialog.Builder(context).setTitle(R.string.form_panel_list_left_confirmation_title)
                            .setMessage(R.string.form_panel_list_left_confirmation)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    _weekStatus = 1;
                                    updateFormStatus(context, indCode);
                                    Toast.makeText(context, R.string.form_panel_list_left, Toast.LENGTH_LONG).show();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                else if (_weekStatus == 1)
                    new AlertDialog.Builder(context).setTitle(R.string.form_panel_list_left_confirmation_title)
                            .setMessage(R.string.form_panel_list_left_remove_confirmation)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    _weekStatus = 0;
                                    updateFormStatus(context, indCode);
                                    Toast.makeText(context, R.string.form_panel_list_left_remove, Toast.LENGTH_LONG).show();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                else if (_weekStatus == 2)
                    Toast.makeText(context, R.string.form_panel_list_already_taken_error, Toast.LENGTH_LONG).show();
                else if (_weekStatus == 3)
                    Toast.makeText(context, R.string.form_panel_list_already_sent_error, Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(context, R.string.invalid_operation, Toast.LENGTH_LONG).show();
            }
        });
        ImageView check2 = (ImageView) view.findViewById(R.id.house2pt_image_view);
        check2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_weekStatus == 0)
                    Toast.makeText(context, R.string.form_panel_list_not_yet_left_error, Toast.LENGTH_LONG).show();
                else if (_weekStatus == 1)
                    new AlertDialog.Builder(context).setTitle(R.string.form_panel_list_taken_confirmation_title)
                            .setMessage(R.string.form_panel_list_taken_confirmation)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    _weekStatus = 2;
                                    updateFormStatus(context, indCode);
                                    Toast.makeText(context, R.string.form_panel_list_taken, Toast.LENGTH_LONG).show();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                else if (_weekStatus == 2)
                    new AlertDialog.Builder(context).setTitle(R.string.form_panel_list_taken_confirmation_title)
                            .setMessage(R.string.form_panel_list_taken_remove_confirmation)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    _weekStatus = 1;
                                    updateFormStatus(context, indCode);
                                    Toast.makeText(context, R.string.form_panel_list_taken_remove, Toast.LENGTH_LONG).show();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                else if (_weekStatus == 3)
                    Toast.makeText(context, R.string.form_panel_list_already_sent_error, Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(context, R.string.invalid_operation, Toast.LENGTH_LONG).show();
            }
        });


        ImageView pt2houseOk = (ImageView) view.findViewById(R.id.pt2house_ok_image_view);
        ImageView house2ptOk = (ImageView) view.findViewById(R.id.house2pt_ok_image_view);
        switch (_weekStatus) {
            case 0:
                status = context.getString(R.string.form_panel_list_item_0);
                viewHolder.panelStatusView.setTextColor(Color.parseColor("#FF5533"));
                pt2houseOk.setVisibility(View.INVISIBLE);
                house2ptOk.setVisibility(View.INVISIBLE);
                break;
            case 1:
                status = context.getString(R.string.form_panel_list_item_1);
                viewHolder.panelStatusView.setTextColor(Color.parseColor("#000000"));
                pt2houseOk.setVisibility(View.VISIBLE);
                house2ptOk.setVisibility(View.INVISIBLE);
                break;
            case 2:
                status = context.getString(R.string.form_panel_list_item_2);
                viewHolder.panelStatusView.setTextColor(Color.parseColor("#000000"));
                pt2houseOk.setVisibility(View.VISIBLE);
                house2ptOk.setVisibility(View.VISIBLE);
                break;
            case 3:
                status = context.getString(R.string.form_panel_list_item_3);
                viewHolder.panelStatusView.setTextColor(Color.parseColor("#4CD964"));
                pt2houseOk.setVisibility(View.VISIBLE);
                house2ptOk.setVisibility(View.VISIBLE);
                break;
        }
        viewHolder.panelStatusView.setText(status);
    }

    private void updateFormStatus(Context context, int indCode) {
        Uri uri = PTContract.Panel.CONTENT_URI;
        ContentValues values = new ContentValues();
        values.put(PTContract.Panel.COLUMN_WEEK_CHECK, _weekStatus);
        values.put(PTContract.Panel.COLUMN_SYNC, 1);
        String where = PTContract.Panel.COLUMN_PANEL_TYPE + " = ? AND " +
                PTContract.Panel.COLUMN_WEEK_CODE + " = ? AND " +
                PTContract.Panel.COLUMN_FAM_CODE + " = ? AND " +
                PTContract.Panel.COLUMN_IND_CODE + " = ? ";
        String[] args = new String[] { _panelType, Integer.toString(_weekCode), _famCode, Integer.toString(indCode) };
        context.getContentResolver().update(uri, values, where, args);
    }
}
