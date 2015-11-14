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
        util.addUser("cshe", "Congwei She", "2255555", "123");
    }
}
