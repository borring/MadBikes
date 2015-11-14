package com.cs442.team6.madbikes;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by moymoy on 11/7/15.
 */
public class Utilities {

	Context c;
	SQLiteDatabase db;
    SQLHelper dbhelper;

    // Sharedpref AUTH flag
    String AUTH_FILE = "auth";
    String AUTH_FLAG = "isAuth";

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
        String[] columns = {
                dbhelper.VOTES.UID,
                dbhelper.VOTES.BID
        };
        String selection = dbhelper.BIKES.BID + " = " + BID;
        Cursor vcur = db.query(
                dbhelper.BIKES.TABLE_NAME,
                columns,
                selection,
                null,
                null,
                null,
                null
        );
        if (vcur == null) {
            vcur.close();
            return;
        }

        columns = new String[]{
                dbhelper.BIKES.BID,
                dbhelper.BIKES.LIKES
        };
        ContentValues cvals = new ContentValues();
        cvals.put(dbhelper.BIKES.LIKES, vcur.getCount());
        db.update(dbhelper.BIKES.TABLE_NAME, cvals, selection, null);
        vcur.close();
    }

    public boolean hasVoted(int UID, int BID) {
        boolean ret;
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
        ret = cur.getCount() <= 0 ? false:true;
        cur.close();
        return ret;
    }

    public Location getLocation(int BID) {
        return null;
    }

    public boolean authenticate(String username, String passwd) {
        String hash = hashPasswd(passwd);
        String[] columns = {
                dbhelper.USERS.USERNAME,
                dbhelper.USERS.PASSWORD
        };
        String selection = dbhelper.USERS.USERNAME + " = " + username
                + "AND " + dbhelper.USERS.PASSWORD + " = " + hash;
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
        SharedPreferences pref = c.getSharedPreferences(AUTH_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(AUTH_FLAG, false);
        editor.commit();
        return true;
    }

    public String hashPasswd(String passwd) {
        String ret = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passwd.getBytes());
            ret = new String(md.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public boolean isAuthenticated() {
        SharedPreferences pref = c.getSharedPreferences(AUTH_FILE, Context.MODE_PRIVATE);
        return pref.getBoolean(AUTH_FLAG, false);
    }

    public void addUser(String uname, String name, String phon, String passwd) {
        ContentValues cvals = new ContentValues();
        cvals.put(dbhelper.USERS.USERNAME, uname);
        cvals.put(dbhelper.USERS.NAME, name);
        cvals.put(dbhelper.USERS.PHONE, phon);
        cvals.put(dbhelper.USERS.PASSWORD, hashPasswd(passwd));
        db.insert(dbhelper.USERS.TABLE_NAME, null, cvals);
    }

    public void addBike(int UID, String bname, float lat, float lng, float rate) {
        ContentValues cvals = new ContentValues();
        cvals.put(dbhelper.BIKES.UID, UID);
        cvals.put(dbhelper.BIKES.NAME, bname);
        cvals.put(dbhelper.BIKES.LAT, lat);
        cvals.put(dbhelper.BIKES.LONG, lng);
        cvals.put(dbhelper.BIKES.RATE, rate);
        db.insert(dbhelper.BIKES.TABLE_NAME, null, cvals);
    }
}
