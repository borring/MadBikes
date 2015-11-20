package com.cs442.team6.madbikes;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

public class BikesDetail extends AppCompatActivity {

    static final String BID_KEY = "BID";
    int BID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bikes_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        BID = intent.getIntExtra(BID_KEY, -1);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.mp) {
            startActivity(new Intent(this, ManageProfile.class));
            return true;
        } else {
            NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Utilities util = new Utilities(this);
        String bname = util.getBikeName(BID);
        String bcond = util.getBikeCondition(BID);
        float brate = util.getBikeRate(BID);
        LatLng latlng = util.getBikeLatLng(BID);
        boolean avail = util.getBikeAvail(BID);

        ((TextView) findViewById(R.id.bname)).setText(bname);
        ((TextView) findViewById(R.id.bavailable)).setText(avail ? "yes":"no");
        ((TextView) findViewById(R.id.bavailable)).setTextColor(avail ? Color.GREEN:Color.RED);
        ((TextView) findViewById(R.id.brate)).setText(
                String.format("$%.2f/hr", brate)
        );
        ((TextView) findViewById(R.id.bcondition)).setText(bcond);
        ((TextView) findViewById(R.id.blocation)).setText(
                "Lat " + latlng.latitude + "\n"
                        + "Lng " + latlng.longitude
        );
    }

}
