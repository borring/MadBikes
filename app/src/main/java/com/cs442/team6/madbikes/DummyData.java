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

    public void close() {
        util.close();
    }

    public void fill() {
        if (util.numUsers() > 0) {
            return;
        }
        util.addUser("cshe@gmail.com", "Congwei She", "8722021136", "123");
        util.addUser("cmoy@gmail.com", "Chester Moy", "7738757625", "234");
        util.addUser("ggadhvi@gmail.com", "Gaurav Gadhvi", "3129185225", "567");
        util.addUser("aashokk@gmail.com", "Arjun Ashok", "3129275249", "891");

    //    (int UID, String bname, String address, double lat, double lng, String state, float rate)
        util.addBike(
                util.getUID("cmoy@gmail.com"),
                "Black Allez Alluminum Body",
                "3001 s king drive Chicago",
                41.838310,
                -87.627371,
                "New",
                (float)5.00
        );
        util.addBike(
                util.getUID("cmoy@gmail.com"),
                "Grey Allez Alluminum Body",
                "3001 s king drive Chicago",
                41.838927, -87.628662,
                "New",
                (float)5.00
        );
        util.addBike(
                util.getUID("cmoy@gmail.com"),
                "White Allez Alluminum Body",
                "3001 s king drive Chicago",
                41.839839, -87.625540,
                "New",
                (float)5.00
        );
        util.addBike(
                util.getUID("ggadhvi@gmail.com"),
                "Red Mangoose",
                "2801 s king drive Chicago",
                41.838344, -87.634274,
                "New",
                (float)5.00
        );
        util.addBike(
                util.getUID("ggadhvi@gmail.com"),
                "Blue Mangoose",
                "2801 s king drive Chicago",
                41.838368, -87.633136,
                "New",
                (float)5.00
        );
        util.addBike(
                util.getUID("aashokk@gmail.com"),
                "Black Schwinn Wayfarer",
                "2951 s king drive Chicago",
                41.841592, -87.616292,
                "Good",
                (float)5.00
        );
        util.addBike(
                util.getUID("aashokk@gmail.com"),
                "Grey Schwinn Wayfarer",
                "2951 s king drive Chicago",
                41.840402, -87.616400,
                "Good",
                (float)5.00
        );
        util.addBike(
                util.getUID("aashokk@gmail.com"),
                "Blue Schwinn Wayfarer",
                "2951 s king drive Chicago",
                41.837287,
                -87.623352,
                "Good",
                (float)5.00
        );
        util.addBike(
                util.getUID("cshe@gmail.com"),
                "Pink Kulana Womens",
                "2951 s federal street Chicago",
                41.839366, -87.616206,
                "Like New",
                (float)15.00
        );
        util.addBike(
                util.getUID("cshe@gmail.com"),
                "White Kulana Womens",
                "2951 s federal street Chicago",
                41.840593, -87.618068,
                "Like New",
                (float)15.00
        );
    }
}
