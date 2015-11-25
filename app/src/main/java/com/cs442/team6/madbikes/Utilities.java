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
    boolean closed = false;

    // Sharedpref AUTH flag
    String AUTH_FILE = "auth";
    String AUTH_FLAG = "isAuth";
    String AUTH_NAME = "username";

	Utilities(Context c) {
		this.c = c;
		this.dbhelper = new SQLHelper(c);
        db = dbhelper.getWritableDatabase();
	}

   // public int insertDb()

	public int getPopularity(int BID) {
        if (closed) {
            return -1;
        }
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
        if (closed) {
            return;
        }
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
        if (closed) {
            return true;
        }
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

    public boolean authenticate(String username, String passwd) {
        if (closed) {
            return false;
        }
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
        editor.putString(AUTH_NAME, username);
        editor.putBoolean(AUTH_FLAG, true);
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

    //check if the username hase existed
    public boolean isExisted(String username){
        if(getUID(username)==-1){
            return false;
        }else
            return true;
    }

    public long addUser(String uname, String name, String phon, String passwd) {
        if (closed) {
            Log.d("util/addUser", "db closed. Cannot add user");
            return -1;
        }
        ContentValues cvals = new ContentValues();
        cvals.put(dbhelper.USERS.USERNAME, uname);
        cvals.put(dbhelper.USERS.NAME, name);
        cvals.put(dbhelper.USERS.PHONE, phon);
        cvals.put(dbhelper.USERS.PASSWORD, hashPasswd(passwd));
        long rowid = db.insert(dbhelper.USERS.TABLE_NAME, null, cvals);
        Log.d("util/addUser", "Added " + rowid + " users");
        return rowid;
    }

    public long updateUser(int UID, String name, String phon, String passwd) {
        if (closed) {
            Log.d("util/addUser", "db closed. Cannot add user");
            return -1;
        }
        String where = dbhelper.USERS.UID + " = ?";
        String[] whereArgs = {Integer.toString(UID)};

        ContentValues cvals = new ContentValues();
        cvals.put(dbhelper.USERS.NAME, name);
        cvals.put(dbhelper.USERS.PHONE, phon);
        cvals.put(dbhelper.USERS.PASSWORD, hashPasswd(passwd));
        long rowid = db.update(dbhelper.USERS.TABLE_NAME, cvals, where, whereArgs);
        Log.d("util/updateUser", "Updated user " + UID);
        return rowid;
    }

    public void addBike(int UID, String bname, String address, double lat, double lng, String state, float rate) {
        if (closed) {
            Log.d("util/addBike", "db closed. Cannot add bike");
        }
        ContentValues cvals = new ContentValues();
        cvals.put(dbhelper.BIKES.UID, UID);
        cvals.put(dbhelper.BIKES.NAME, bname);
        cvals.put(dbhelper.BIKES.LAT, lat);
        cvals.put(dbhelper.BIKES.LONG, lng);
        cvals.put(dbhelper.BIKES.RATE, rate);
        cvals.put(dbhelper.BIKES.ISAVAILABLE, 1);
        cvals.put(dbhelper.BIKES.ADDRESS,address);
        cvals.put(dbhelper.BIKES.CONDITION, state);
        db.insert(dbhelper.BIKES.TABLE_NAME, null, cvals);
    }

    public void updateBike(int BID, String bname, double lat, double lng, String state, float rate) {
        if (closed) {
            Log.d("util/addBike", "db closed. Cannot add bike");
        }
        String where = dbhelper.BIKES.BID + " = ?";
        String[] whereArgs = {Integer.toString(BID)};

        ContentValues cvals = new ContentValues();
        cvals.put(dbhelper.BIKES.NAME, bname);
        cvals.put(dbhelper.BIKES.LAT, lat);
        cvals.put(dbhelper.BIKES.LONG, lng);
        cvals.put(dbhelper.BIKES.RATE, rate);
        cvals.put(dbhelper.BIKES.ISAVAILABLE, 1);
        cvals.put(dbhelper.BIKES.CONDITION, state);
        db.update(dbhelper.BIKES.TABLE_NAME, cvals, where, whereArgs);
    }

    public int numUsers() {
        if (closed) {
            Log.d("util/numUsers", "db closed. Cannot get numUsers");
            return -1;
        }
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
            return -1;
        }
        ret = cur.getCount();
        cur.close();
        return ret;
    }

    public int getUID(String uname) {
        if (closed) {
            Log.d("util/getUID", "db closed. Cannot get UID");
            return -1;
        }
        int ret;
        String[] columns = {
                dbhelper.USERS.UID,
                dbhelper.USERS.USERNAME
        };
        String where = dbhelper.USERS.USERNAME + " = ?";
        String[] whereArgs = {uname};
        Cursor cur = db.query(
                dbhelper.USERS.TABLE_NAME,
                columns,
                where,
                whereArgs,
                null,
                null,
                null
        );
        if (cur == null) {
            cur.close();
            return -1;
        }
        cur.moveToFirst();
        ret = cur.getInt(cur.getColumnIndex(dbhelper.USERS.UID));
        cur.close();
        return ret;
    }

    public String getUsername(int UID) {
        if (closed) {
            Log.d("util/getUsername", "db closed. Cannot get Username");
            return null;
        }
        String ret;
        String[] columns = {
                dbhelper.USERS.UID,
                dbhelper.USERS.USERNAME
        };
        String where = dbhelper.USERS.UID + " = ?";
        String[] whereArgs = {Integer.toString(UID)};
        Cursor cur = db.query(
                dbhelper.USERS.TABLE_NAME,
                columns,
                where,
                whereArgs,
                null,
                null,
                null
        );
        if (cur == null) {
            cur.close();
            return null;
        }
        cur.moveToFirst();
        ret = cur.getString(cur.getColumnIndex(dbhelper.USERS.USERNAME));
        cur.close();
        return ret;
    }

    public String getName(int UID) {
        if (closed) {
            Log.d("util/getName", "db closed. Cannot get Name");
            return null;
        }
        String ret;
        String[] columns = {
                dbhelper.USERS.UID,
                dbhelper.USERS.NAME
        };
        String where = dbhelper.USERS.UID + " = ?";
        String[] whereArgs = {Integer.toString(UID)};
        Cursor cur = db.query(
                dbhelper.USERS.TABLE_NAME,
                columns,
                where,
                whereArgs,
                null,
                null,
                null
        );
        if (cur == null) {
            cur.close();
            return null;
        }
        cur.moveToFirst();
        ret = cur.getString(cur.getColumnIndex(dbhelper.USERS.NAME));
        cur.close();
        return ret;
    }

    public String getPhone(int UID) {
        if (closed) {
            Log.d("util/getPhone", "db closed. Cannot get Phone Number");
            return null;
        }
        String ret;
        String[] columns = {
                dbhelper.USERS.UID,
                dbhelper.USERS.PHONE
        };
        String where = dbhelper.USERS.UID + " = ?";
        String[] whereArgs = {Integer.toString(UID)};
        Cursor cur = db.query(
                dbhelper.USERS.TABLE_NAME,
                columns,
                where,
                whereArgs,
                null,
                null,
                null
        );
        if (cur == null) {
            cur.close();
            return null;
        }
        cur.moveToFirst();
        ret = cur.getString(cur.getColumnIndex(dbhelper.USERS.PHONE));
        cur.close();
        return ret;
    }

    public Cursor getBikeCur() {
        if (closed) {
            return null;
        }
        String[] columns = {
                dbhelper.BIKES.BID,
                dbhelper.BIKES.NAME,
                dbhelper.BIKES.ISAVAILABLE,
                dbhelper.BIKES.RATE,
                dbhelper.BIKES.CONDITION,
                dbhelper.BIKES.LIKES,
                dbhelper.BIKES.IMAGE_KEY,
                dbhelper.BIKES.LAT,
                dbhelper.BIKES.LONG
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
        return cur;
    }

    public int[] getBikeList() {
        if (closed) {
            return null;
        }
        String[] columns = {
                dbhelper.BIKES.BID
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
        if (cur == null) {
            return null;
        }
        Log.d("util/getBikeList", String.format("There are %d bikes in db", cur.getCount()));
        if (cur.getCount() <= 0) {
            cur.close();
            return null;
        }
        int[] ret = new int[cur.getCount()];
        cur.moveToFirst();
        for (int i = 0; i < cur.getCount(); i++) {
            ret[i] = cur.getInt(cur.getColumnIndex(dbhelper.BIKES.BID));
            Log.d("util/getBikeList", "adding bike to int[]");
            cur.moveToNext();
        }
        cur.close();
        return ret;
    }

    public int getBikeUID(int BID) {
        if (closed) {
            return -1;
        }
        String[] columns = {
                dbhelper.BIKES.BID,
                dbhelper.BIKES.UID
        };
        String where = dbhelper.BIKES.BID + " = ?";
        String[] whereArgs = {Integer.toString(BID)};
        Cursor cur = db.query(
                dbhelper.BIKES.TABLE_NAME,
                columns,
                where,
                whereArgs,
                null,
                null,
                null
        );
        if (cur == null) {
            return -1;
        }
        if (cur.getCount() <= 0) {
            return -1;
        }
        cur.moveToFirst();
        int ret = cur.getInt(cur.getColumnIndex(dbhelper.BIKES.UID));
        cur.close();
        return ret;
    }

    public String getBikeName(int BID) {
        if (closed) {
            return null;
        }
        String[] columns = {
                dbhelper.BIKES.BID,
                dbhelper.BIKES.NAME
        };
        String where = dbhelper.BIKES.BID + " = ?";
        String[] whereArgs = {Integer.toString(BID)};
        Cursor cur = db.query(
                dbhelper.BIKES.TABLE_NAME,
                columns,
                where,
                whereArgs,
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
        cur.moveToFirst();
        String ret = cur.getString(cur.getColumnIndex(dbhelper.BIKES.NAME));
        cur.close();
        return ret;
    }

    public LatLng getBikeLatLng(int BID) {
        if (closed) {
            return null;
        }
        LatLng ret;
        String[] columns = {
                dbhelper.BIKES.LAT,
                dbhelper.BIKES.LONG
        };
        String where = dbhelper.BIKES.BID + " = ?";
        String[] whereArgs = {Integer.toString(BID)};
        Cursor cur = db.query(
                dbhelper.BIKES.TABLE_NAME,
                columns,
                where,
                whereArgs,
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
        cur.moveToFirst();
        ret = new LatLng(
                cur.getDouble(cur.getColumnIndex(dbhelper.BIKES.LAT)),
                cur.getDouble(cur.getColumnIndex(dbhelper.BIKES.LONG))
        );
        cur.close();
        return ret;
    }

    public String getBikeCondition(int BID) {
        if (closed) {
            return null;
        }
        String[] columns = {
                dbhelper.BIKES.BID,
                dbhelper.BIKES.CONDITION
        };
        String where = dbhelper.BIKES.BID + " = ?";
        String[] whereArgs = {Integer.toString(BID)};
        Cursor cur = db.query(
                dbhelper.BIKES.TABLE_NAME,
                columns,
                where,
                whereArgs,
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
        cur.moveToFirst();
        String ret = cur.getString(cur.getColumnIndex(dbhelper.BIKES.CONDITION));
        cur.close();
        return ret;
    }

    public float getBikeRate(int BID) {
        if (closed) {
            return -1;
        }
        String[] columns = {
                dbhelper.BIKES.BID,
                dbhelper.BIKES.RATE
        };
        String where = dbhelper.BIKES.BID + " = ?";
        String[] whereArgs = {Integer.toString(BID)};
        Cursor cur = db.query(
                dbhelper.BIKES.TABLE_NAME,
                columns,
                where,
                whereArgs,
                null,
                null,
                null
        );
        if (cur == null) {
            return -1;
        }
        if (cur.getCount() <= 0) {
            return -1;
        }
        cur.moveToFirst();
        float ret = cur.getFloat(cur.getColumnIndex(dbhelper.BIKES.RATE));
        cur.close();
        return ret;
    }

    public boolean getBikeAvail(int BID) {
        if (closed) {
            return false;
        }
        String[] columns = {
                dbhelper.BIKES.BID,
                dbhelper.BIKES.ISAVAILABLE
        };
        String where = dbhelper.BIKES.BID + " = ?";
        String[] whereArgs = {Integer.toString(BID)};
        Cursor cur = db.query(
                dbhelper.BIKES.TABLE_NAME,
                columns,
                where,
                whereArgs,
                null,
                null,
                null
        );
        if (cur == null) {
            return false;
        }
        if (cur.getCount() <= 0) {
            return false;
        }
        cur.moveToFirst();
        int ret = cur.getInt(cur.getColumnIndex(dbhelper.BIKES.ISAVAILABLE));
        cur.close();
        return ret == 1;
    }

    public void close() {
        db.close();
        closed = true;
    }
}
