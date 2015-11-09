package com.cs442.team6.madbikes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;

/**
 * Created by moymoy on 11/7/15.
 */
public class Utilities {

	Context c;
	SQLiteDatabase db;
    SQLHelper dbhelper;

	Utilities(Context c, SQLHelper dbhelper) {
		this.c = c;
		this.dbhelper = dbhelper;
        db = dbhelper.getWritableDatabase();
	}

   // public int insertDb()

	public int getPopularity(int BID) {
        int ret;

        String[] selection = {
                dbhelper.BIKES.LIKES
        };
        Cursor cur = db.query(
                dbhelper.BIKES.TABLE_NAME,
                selection,
                null,
                null,
                null,
                null,
                null
        );
        if (cur.getCount() <= 0) {
            cur.close();
            return -1;
        }

        cur.moveToFirst();
        ret = cur.getInt(cur.getColumnIndex(dbhelper.BIKES.LIKES));
        cur.close();

        return ret;
	}

    public void updatePopularity(int BID) {

    }

    public boolean hasVoted(int UID, int BID) {
        String[] columns = {
                dbhelper.VOTES.UID
        };
        String selection = dbhelper.VOTES.UID + " = " + UID
                + "AND " + dbhelper.VOTES.BID + " = " + BID;
        Cursor cur = db.query(
                dbhelper.BIKES.TABLE_NAME,
                columns,
                selection,
                null,
                null,
                null,
                null
        );
        if (cur.getCount() <= 0) {
            cur.close();
            return false;
        }
        cur.close();
        return true;
    }

    public Location getLocation(int BID) {
        return null;
    }

    public boolean authenticate(String username, String passwd) {
        return false;
    }

    public boolean isAuthenticated() {
        return false;
    }
}
