package com.cs442.team6.madbikes;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListOfBike extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_bike);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Utilities util = new Utilities(this);
        Cursor cur = util.getBikeCur();
        if (cur.getCount() <= 0) {
            Log.d("ListofBikes", "Cursor is empty");
            Log.d("ListofBikes", "Cursor.getCount(): " + cur.getCount());
        }
        BikeCursorAdapter customAdapter = new BikeCursorAdapter(
                this,
                cur,
                true
        );
        ListView lv = (ListView) findViewById(R.id.blistview);
        if (lv == null) {
            Log.d("ListofBikes", "listview is null");
            return;
        }
        lv.setAdapter(customAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView bidview = (TextView) view.findViewById(R.id.list_BID);
                Intent bikedetails = new Intent(getBaseContext(), BikesDetail.class);
                bikedetails.putExtra(BikesDetail.BID_KEY, Integer.parseInt(bidview.getText().toString()));
                startActivity(bikedetails);
            }
        });
    }
}
