package com.cs442.team6.madbikes;




import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLHelper extends SQLiteOpenHelper {

    static class Users {
        public static final String TABLE_NAME = "Users";
        public static final String UID = "_id";
        public static final String NAME = "Name";
        public static final String USERNAME = "Username";//The username will be unique identifier of the user and will store the email id as username
        public static final String PASSWORD = "Password";
        public static final String PHONE = "PhoneNo";
    }

    static class Bikes {
        public static final String TABLE_NAME = "Bikes";
        public static final String BID = "_id";
        public static final String UID = "UID";
        public static final String NAME = "Make";
        public static final String ADDRESS = "Address";
        public static final String LAT = "Latitude";
        public static final String LONG = "Longitude";
        public static final String ISAVAILABLE = "isAvailable";
        public static final String CONDITION = "Condition"; // Select Condition from dropdown {upto one year old, upto two years old, upto three years old}
        public static final String RATE = "Rate";
        public static final String IMAGE_KEY = "Image";
        public static final String LIKES = "Likes";
    }

    static class Votes {
        public static final String TABLE_NAME = "Votes";
        public static final String UID = "UID";
        public static final String BID = "BID";
    }

    static final Users USERS = new Users();
    static final Bikes BIKES = new Bikes();
    static final Votes VOTES = new Votes();

    private static final String DATABASE_NAME = "MAD.DB";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE_USERS = "CREATE TABLE "
        + USERS.TABLE_NAME + " ("
        + USERS.UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + USERS.USERNAME + " TEXT NOT NULL, "
        + USERS.NAME + " TEXT NOT NULL, "
        + USERS.PASSWORD + " BLOB NOT NULL, "
        + USERS.PHONE + " INTEGER NOT NULL" + ")";

    private static final String DATABASE_CREATE_BIKES = "CREATE TABLE "
        + BIKES.TABLE_NAME + " ("
        + BIKES.BID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + BIKES.UID + " INTEGER, "
        + BIKES.NAME + " TEXT, "
        + BIKES.ADDRESS + " TEXT, "
        + BIKES.LAT + " REAL, "
        + BIKES.LONG + " REAL, "
        + BIKES.ISAVAILABLE + " INTEGER, "
        + BIKES.RATE + " REAL,"
        + BIKES.IMAGE_KEY + " TEXT, "
        + BIKES.CONDITION + " TEXT, "
        + BIKES.LIKES + " INTEGER, "
        + "FOREIGN KEY (" + BIKES.UID + ") REFERENCES " + USERS.TABLE_NAME + "(" + USERS.UID + ")" +")";

    private static final String DATABASE_CREATE_VOTES = "CREATE TABLE "
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
