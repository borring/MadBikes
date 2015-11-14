package com.cs442.team6.madbikes;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

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

	Utilities(Context c) {
		this.c = c;
		this.dbhelper = new SQLHelper(c);
        db = dbhelper.getWritableDatabase();
	}

   // public int insertDb()

	public int getPopularity(int BID) {
        int ret;

        String[] columns = {
                dbhelper.BIKES.LIKES
        };
        Cursor cur = db.query(
                dbhelper.BIKES.TABLE_NAME,
                columns,
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
        String selection = dbhelper.BIKES.BID + " = ?";
        String[] selectionArgs = {Integer.toString(BID)};
        Cursor vcur = db.query(
                dbhelper.VOTES.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
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
        db.update(dbhelper.BIKES.TABLE_NAME, cvals, selection, selectionArgs);
        vcur.close();
    }

    public boolean hasVoted(int UID, int BID) {
        boolean ret;
        String[] columns = {
                dbhelper.VOTES.UID
        };
        String selection = dbhelper.VOTES.UID + " = ? "
                + "AND " + dbhelper.VOTES.BID + " = ?";
        String[] selectionArgs = {
                Integer.toString(UID),
                Integer.toString(BID)
        };
        Cursor cur = db.query(
                dbhelper.VOTES.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
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

    public LatLng getLatLng(int BID) {
        LatLng ret;
        String[] columns = {
                dbhelper.BIKES.LAT,
                dbhelper.BIKES.LONG
        };
        String selection = dbhelper.BIKES.BID + " = " + BID;
        Cursor cur = db.query(
                dbhelper.BIKES.TABLE_NAME,
                columns,
                selection,
                null,
                null,
                null,
                null
        );
        if (cur == null) {
            return null;
        }
        if (cur.getCount() <= 0) {
            return null;
        }
        ret = new LatLng(
                cur.getDouble(cur.getColumnIndex(dbhelper.BIKES.LAT)),
                cur.getDouble(cur.getColumnIndex(dbhelper.BIKES.LAT))
        );
        cur.close();
        return ret;
    }

    public boolean authenticate(String username, String passwd) {
        byte[] hash = hashPasswd(passwd);
        String[] columns = {
                dbhelper.USERS.USERNAME,
                dbhelper.USERS.PASSWORD
        };
        String selection = dbhelper.USERS.USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cur = db.query(
                dbhelper.USERS.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (cur.getCount() <= 0) {
            cur.close();
            Log.d("Auth", "No such username");
            return false;
        }
        cur.moveToFirst();
        if (!Arrays.equals(cur.getBlob(cur.getColumnIndex(dbhelper.USERS.PASSWORD)), hash)) {
            cur.close();
            Log.d("Auth", "Password hashes do not match");
            return false;
        }
        cur.close();
        SharedPreferences pref = c.getSharedPreferences(AUTH_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(AUTH_FLAG, false);
        editor.commit();
        return true;
    }

    public byte[] hashPasswd(String passwd) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passwd.getBytes());
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
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
        long rowid = db.insert(dbhelper.USERS.TABLE_NAME, null, cvals);
        Log.d("util/addUser", "Added " + rowid + " users");
    }

    public void addBike(int UID, String bname, double lat, double lng, float rate) {
        ContentValues cvals = new ContentValues();
        cvals.put(dbhelper.BIKES.UID, UID);
        cvals.put(dbhelper.BIKES.NAME, bname);
        cvals.put(dbhelper.BIKES.LAT, lat);
        cvals.put(dbhelper.BIKES.LONG, lng);
        cvals.put(dbhelper.BIKES.RATE, rate);
        db.insert(dbhelper.BIKES.TABLE_NAME, null, cvals);
    }

    public int numUsers() {
        int ret;
        String[] columns = {
                dbhelper.USERS.UID
        };
        Cursor cur = db.query(
                dbhelper.USERS.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
        );
        if (cur == null) {
            cur.close();
            return 0;
        }
        ret = cur.getCount();
        cur.close();
        return ret;
    }
}
