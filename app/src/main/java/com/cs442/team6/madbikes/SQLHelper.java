package com.cs442.team6.madbikes;




import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLHelper extends SQLiteOpenHelper {

    public static final String TABLE_USERS = "USERS";
    public static final String UID = "UID";
    public static final String NAME = "NAME";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";


    public static final String TABLE_BIKES = "BIKES";
    public static final String BID = "BID";
    public static final String LOCATION_LAT = "LATITUDE";
    public static final String LOCATION_LONG = "LONGITUDE";
    public static final String ISAVAILABLE = "ISAVAILABLE";
    public static final String LIKES = "LIKES";


    public static final String TABLE_VOTES = "VOTES";


    private static final String DATABASE_NAME = "MAD.DB";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE_USERS = "create table "
            + TABLE_USERS + " ("
            + UID + " integer primary key autoincrement, "
            + USERNAME + " text not null, "
            + NAME + " text not null, "
            + PASSWORD + " text not null" +")";

    private static final String DATABASE_CREATE_BIKES = "create table "
        + TABLE_BIKES + " ("
        + BID + " integer primary key autoincrement, "
        + UID + " INTEGER, "
        + LOCATION_LAT + " REAL, "
        + LOCATION_LONG + " REAL, "
        + ISAVAILABLE + " INTEGER, "
        + LIKES + " INTEGER, "
        + "FOREIGN KEY (" + UID + ") REFERENCES " + TABLE_USERS + "(" + UID + ")" +")";

    private static final String DATABASE_CREATE_VOTES = "create table "
        + TABLE_VOTES + " (" 
        + BID + " INTEGER, "
        + UID + " INTEGER, "
        + "FOREIGN KEY (" + BID + ") REFERENCES " + TABLE_BIKES + "(" + BID + "), "
        + "FOREIGN KEY (" + UID + ") REFERENCES " + TABLE_USERS + "(" + UID + ")"
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BIKES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOTES);
        onCreate(db);
    }

}
