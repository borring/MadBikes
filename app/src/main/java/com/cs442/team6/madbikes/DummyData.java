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
        util.addUser("aashokk@gmail.com", "Arjun Ashok", "3129275249", "890");

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
                41.838312,
                -87.627373,
                "New",
                (float)5.00
        );
        util.addBike(
                util.getUID("cmoy@gmail.com"),
                "White Allez Alluminum Body",
                "3001 s king drive Chicago",
                41.838308,
                -87.627369,
                "New",
                (float)5.00
        );
        util.addBike(
                util.getUID("ggadhvi@gmail.com"),
                "Red Mangoose",
                "2801 s king drive Chicago",
                41.832620,
                -87.615028,
                "New",
                (float)5.00
        );
        util.addBike(
                util.getUID("ggadhvi@gmail.com"),
                "Blue Mangoose",
                "2801 s king drive Chicago",
                41.832622,
                -87.615030,
                "New",
                (float)5.00
        );
        util.addBike(
                util.getUID("aashokk@gmail.com"),
                "Black Schwinn Wayfarer",
                "2951 s king drive Chicago",
                41.837289,
                -87.623354,
                "Good",
                (float)5.00
        );
        util.addBike(
                util.getUID("aashokk@gmail.com"),
                "Grey Schwinn Wayfarer",
                "2951 s king drive Chicago",
                41.837291,
                -87.623356,
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
                42.837296,
                -88.623360,
                "Like New",
                (float)15.00
        );
        util.addBike(
                util.getUID("cshe@gmail.com"),
                "White Kulana Womens",
                "2951 s federal street Chicago",
                42.837298,
                -88.623362,
                "Like New",
                (float)15.00
        );
    }
}
