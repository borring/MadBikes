package com.cs442.team6.madbikes;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by moymoy on 11/24/15.
 */
public class ManageBikeCursorAdapter extends CursorAdapter {

    ManageBikeCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final View view = LayoutInflater.from(context).inflate(R.layout.manage_profile_bikelist_row_layout, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String bname = cursor.getString(cursor.getColumnIndex(SQLHelper.BIKES.NAME));
        float brate = cursor.getFloat(cursor.getColumnIndex(SQLHelper.BIKES.RATE));
        String bcondition = cursor.getString(cursor.getColumnIndex(SQLHelper.BIKES.CONDITION));
        boolean bavail = cursor.getInt(cursor.getColumnIndex(SQLHelper.BIKES.ISAVAILABLE)) == 0 ? false:true;
        int bid = cursor.getInt(cursor.getColumnIndex(SQLHelper.BIKES.BID));

        TextView v_bname = (TextView) view.findViewById(R.id.list_bname);
        TextView v_brate = (TextView) view.findViewById(R.id.list_brate);
        TextView v_bcondition = (TextView) view.findViewById(R.id.list_bcondition);
        TextView v_bavail = (TextView) view.findViewById(R.id.list_bavail);
        TextView v_bid = (TextView) view.findViewById(R.id.list_BID);

        // check to see if each individual textview is null.
        // if not, assign some text!
        if (v_bname != null){
            v_bname.setText(bname);
        }
        if (v_brate != null){
            v_brate.setText(Float.toString(brate));
        }
        if (v_bcondition != null){
            v_bcondition.setText(bcondition);
        }
        if (v_bavail != null){
            v_bavail.setText(bavail ? "Available" : "Not Available");
            v_bavail.setTextColor(bavail ? Color.GREEN : Color.RED);
        }
        if (v_bid != null){
            v_bid.setText(Integer.toString(bid));
        }
    }
}