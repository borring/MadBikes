package com.cs442.team6.madbikes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by moymoy on 11/14/15.
 */
public class DummyData {

    Utilities util;
    Context c;
    public DummyData(Context c) {
        this.c = c;
        util = new Utilities(c);
    }

    public void fill() {
        if (util.numUsers() > 0) {
            return;
        }
        util.addUser("cshe", "Congwei She", "12255555", "123");
        util.addUser("cmoy", "Chester Moy", "12255556", "234");
        util.addUser("ggadhvi", "Gaurav Gadhvi", "12255557", "567");
        util.addUser("aarshok", "Arjun Arshok", "12255558", "890");

        util.addBike(
                util.getUID("cmoy"),
                "Black Allez Alluminum Body",
                41.838310,
                -87.627371,
                "Brand new",
                (float)5.00
        );
        util.addBike(
                util.getUID("ggadhvi"),
                "Fastest Bike GG no Re",
                41.832620,
                -87.615028,
                "Brand new",
                (float)5.00
        );
        util.addBike(
                util.getUID("aarshok"),
                "N/A",
                41.837289,
                -87.623354,
                "USB doesn't work",
                (float)5.00
        );
    }
}
