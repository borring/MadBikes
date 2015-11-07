package com.cs442.team6.madbikes;




import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLHelper extends SQLiteOpenHelper {

    static class Users {
        public static final String TABLE_NAME = "USERS";
        public static final String UID = "_id";
        public static final String NAME = "NAME";
        public static final String USERNAME = "USERNAME";
        public static final String PASSWORD = "PASSWORD";
    }

    static class Bikes {
        public static final String TABLE_NAME = "BIKES";
        public static final String BID = "_id";
        public static final String UID = "UID";
        public static final String LOCATION_LAT = "LATITUDE";
        public static final String LOCATION_LONG = "LONGITUDE";
        public static final String ISAVAILABLE = "ISAVAILABLE";
        public static final String LIKES = "LIKES";
    }

    static class Votes {
        public static final String TABLE_NAME = "VOTES";
        public static final String UID = "UID";
        public static final String BID = "BID";
    }

    static Users USERS = new Users();
    static Bikes BIKES = new Bikes();
    static Votes VOTES = new Votes();

    private static final String DATABASE_NAME = "MAD.DB";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE_USERS = "create table "
        + USERS.TABLE_NAME + " ("
        + USERS.UID + " integer primary key autoincrement, "
        + USERS.USERNAME + " text not null, "
        + USERS.NAME + " text not null, "
        + USERS.PASSWORD + " text not null" +")";

    private static final String DATABASE_CREATE_BIKES = "create table "
        + BIKES.TABLE_NAME + " ("
        + BIKES.BID + " integer primary key autoincrement, "
        + BIKES.UID + " INTEGER, "
        + BIKES.LOCATION_LAT + " REAL, "
        + BIKES.LOCATION_LONG + " REAL, "
        + BIKES.ISAVAILABLE + " INTEGER, "
        + BIKES.LIKES + " INTEGER, "
        + "FOREIGN KEY (" + BIKES.UID + ") REFERENCES " + USERS.TABLE_NAME + "(" + USERS.UID + ")" +")";

    private static final String DATABASE_CREATE_VOTES = "create table "
        + VOTES.TABLE_NAME + " (" 
        + VOTES.BID + " INTEGER, "
        + VOTES.UID + " INTEGER, "
        + "FOREIGN KEY (" + VOTES.BID + ") REFERENCES " + BIKES.TABLE_NAME + "(" + BIKES.BID + "), "
        + "FOREIGN KEY (" + VOTES.UID + ") REFERENCES " + USERS.TABLE_NAME + "(" + USERS.UID + ")"
        + " )";

    public SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // Check if sql commands are concatenated correctly
        Log.d("Create Users", DATABASE_CREATE_USERS);
        Log.d("Create Bikes", DATABASE_CREATE_BIKES);
        Log.d("Create Votes", DATABASE_CREATE_VOTES);

        database.execSQL(DATABASE_CREATE_USERS);
        database.execSQL(DATABASE_CREATE_BIKES);
        database.execSQL(DATABASE_CREATE_VOTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + USERS.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BIKES.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + VOTES.TABLE_NAME);
        onCreate(db);
    }

}
