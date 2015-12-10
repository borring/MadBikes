package com.cs442.team6.madbikes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class ManageProfile extends AppCompatActivity implements View.OnClickListener {

    final Context context = this;
    static final String UID_KEY = "UID";
    int UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_profile);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        Button add= (Button)findViewById(R.id.add_new);
        Button edit= (Button)findViewById(R.id.edit_bike1);
        Button delete= (Button)findViewById(R.id.delete);
        add.setOnClickListener(this);
        delete.setOnClickListener(this);
        edit.setOnClickListener(this);
        Intent intent = getIntent();
        UID = intent.getIntExtra(UID_KEY, -1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Utilities util = new Utilities(this);
        String uname = util.getName(UID);
        String umail = util.getUsername(UID);
        String uphone = util.getPhone(UID);
        //LatLng latlng = util.getBikeLatLng(BID);
        //boolean avail = util.getBikeAvail(BID);

        ((TextView) findViewById(R.id.user)).setText(uname);
        ((TextView) findViewById(R.id.ea)).setText(umail);
//        ((TextView) findViewById(R.id.bavailable)).setTextColor(avail ? Color.GREEN : Color.RED);
//        ((TextView) findViewById(R.id.brate)).setText(
//                String.format("$%.2f/hr", brate)
//        );
        ((TextView) findViewById(R.id.pn)).setText(uphone);
//        ((TextView) findViewById(R.id.blocation)).setText(
//                "Lat " + latlng.latitude + "\n"
//                        + "Lng " + latlng.longitude
//        );

        Cursor cur = util.getBikeCur(util.getUID(util.getLoggedInUser()));
        ListView bikelist = (ListView) findViewById(R.id.manage_bikelist);
        ManageBikeCursorAdapter customAdapter = new ManageBikeCursorAdapter(
                this,
                cur,
                true
        );
        bikelist.setAdapter(customAdapter);
        util.close();
        bikelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView bidview = (TextView) view.findViewById(R.id.list_BID);
                Intent bikedetails = new Intent(getBaseContext(), BikesDetail.class);
                bikedetails.putExtra(BikesDetail.BID_KEY, Integer.parseInt(bidview.getText().toString()));
                startActivity(bikedetails);
            }
        });
    }
    public void deleteBike(View view) {
        TextView bid_textview = (TextView) ((View) view.getParent()).findViewById(R.id.list_BID);
        if (bid_textview == null) {
            return;
        }
        int bid = Integer.parseInt(bid_textview.getText().toString());
        Utilities util = new Utilities(this);
        util.deleteBike(bid);
        util.close();
        finish();
        startActivity(getIntent());
        Log.d("ManageProfile/bikelist", "Delete bike BID: " + bid_textview.getText().toString());
    }
    public void editBike(View view) {
        TextView bid_textview = (TextView) ((View) view.getParent()).findViewById(R.id.list_BID);
        if (bid_textview == null) {
            return;
        }
        int bid = Integer.parseInt(bid_textview.getText().toString());
        Intent editbike = new Intent(getBaseContext(), AddNew.class);
        editbike.putExtra(AddNew.BID_KEY, bid);
        startActivity(editbike);
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_new:
                startActivity(new Intent(this, AddNew.class));
                break;
            case R.id.edit_bike1:
                startActivity(new Intent(this, EditBike.class));
                break;
            case R.id.delete:
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.delete_bike, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        return;
                                        }
                                       })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
                break;
        }

    }

}
