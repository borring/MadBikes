package com.cs442.team6.madbikes;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;

/**
 * Created by moymoy on 12/10/15.
 */
public class MainMenu {
    public static boolean onOptionsItemSelected(Context c, MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.mp) {
            Utilities util = new Utilities(c);
            int uid = util.getUID(util.getLoggedInUser());
            util.close();
            Intent signin_intent = new Intent(c, ManageProfile.class);
            signin_intent.putExtra(ManageProfile.UID_KEY,uid);
            c.startActivity(signin_intent);
            //startActivity(new Intent(c, ManageProfile.class));
            return true;
        } else if (id == R.id.list) {
            c.startActivity(new Intent(c, ListOfBike.class));
            return true;
        } else {
            Log.d("MainMenu", String.format("Menu item %d is not hooked up to anything", id));
            return false;
        }
    }
}
