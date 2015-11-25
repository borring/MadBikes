package com.cs442.team6.madbikes;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

public class BikesDetail extends AppCompatActivity implements View.OnClickListener{

    static final String BID_KEY = "BID";
    int BID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bikes_detail);
        /*traverse to another app on clicking button*/
        Button b1=(Button)findViewById(R.id.button);
        b1.setOnClickListener(this);
        Button b2=(Button)findViewById(R.id.button2);
        b2.setOnClickListener(this);
        /*end of segment*/
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

    @Override
    public void onClick(View v) {
        /*
        start
         */
        Utilities util = new Utilities(this);
        int uid = util.getBikeUID(BID);
        String phonenumber = util.getPhone(uid);
        String mailid = util.getUsername(uid);

        /*end*/
        switch (v.getId()) {
            case R.id.button:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",mailid, null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "bike rental");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(emailIntent, "I would like to rent your bike."));
                break;
            case R.id.button2:
                Uri uri = Uri.parse("smsto:" + phonenumber);
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                it.putExtra("sms_body", "I would like to rent your bike.");
                startActivity(it);
        }
        }
}
