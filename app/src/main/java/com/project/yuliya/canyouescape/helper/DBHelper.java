package com.project.yuliya.canyouescape.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.project.yuliya.canyouescape.forserver.User;


public class DBHelper extends SQLiteOpenHelper {

    SQLiteDatabase database;
    ContentValues contentValues;
    Cursor cursor;

    public static final String TAG = "MyLog";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MyDB";
    public static final String TABLE = "game";

    public static final String KEY_ID = "_id";
    public static final String KEY_USER_IS_CURRENT = "UserIsCurrent";
    public static final String KEY_USER_ID = "UserId";
    public static final String KEY_USER_NAME = "UserName";
    public static final String KEY_USER_TIME = "UserTime";
    public static final String KEY_FRAGMENT_NAME = "FragmentName";
    public static final String KEY_IS_LOCKED_LEFT_DOOR = "IsLockedLeftDoor";
    public static final String KEY_IS_LOCKED_RIGHT_DOOR = "IsLockedRightDoor";
    public static final String KEY_IS_LOCKED_MAIN_DOOR = "IsLockedMainDoor";
    public static final String KEY_IS_TABLE_MOVE = "IsTableMove";
    public static final String KEY_IS_LOCKED_HATCH = "IsLockedHatch";
    public static final String KEY_IS_LIGHT = "IsLight";
    public static final String KEY_ON_LIGHT = "IsOnLight";
    public static final String KEY_IS_PICTURE = "IsPicture";
    public static final String KEY_SAFE_OPEN = "SafeOpen";
    public static final String KEY_IS_MIRROR = "IsMirror";
    public static final String KEY_IS_KEY_HATCH = "IsKeyHatch";
    public static final String KEY_IS_CABLE = "IsCable";
    public static final String KEY_IS_HAMMER = "IsHammer";
    public static final String KEY_IS_HATCH_OPEN = "IsHatchOpen";
    public static final String KEY_IS_HATCH_KEY_TAKE = "IsHatchKeyTake";
    public static final String KEY_COUNT_TOUCH_TABLE = "CountTouchTable";
    public static final String KEY_COUNT_TOUCH_TREE = "CountTouchTree";
    public static final String KEY_HAMMER = "Hammer";
    public static final String KEY_CABLE = "Cable";
    public static final String KEY_HATCH_KEY = "KeyHatch";
    public static final String KEY_RIGHT_DOOR_KEY = "KeyRightDoor";
    public static final String KEY_SAFE_KEY = "SafeKey";
    public static final String KEY_MAIN_KEY = "MainKey";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d(TAG, "Enter onCreateDB");


        db.execSQL("create table " + TABLE + "(" + KEY_ID + " integer primary key,"
                + KEY_USER_ID + " int,"
                + KEY_USER_NAME + " String,"
                + KEY_USER_TIME + " String,"
                + KEY_USER_IS_CURRENT + " int,"
                + KEY_FRAGMENT_NAME + " String,"
                + KEY_CABLE + " int,"
                + KEY_IS_CABLE + " int,"
                + KEY_COUNT_TOUCH_TABLE + " int,"
                + KEY_COUNT_TOUCH_TREE + " int,"
                + KEY_IS_HATCH_KEY_TAKE + " int,"
                + KEY_IS_HATCH_OPEN + " int,"
                + KEY_IS_KEY_HATCH + " int,"
                + KEY_IS_LIGHT + " int,"
                + KEY_IS_LOCKED_HATCH + " int,"
                + KEY_IS_LOCKED_LEFT_DOOR + " int,"
                + KEY_IS_LOCKED_RIGHT_DOOR + " int,"
                + KEY_IS_LOCKED_MAIN_DOOR + " int,"
                + KEY_IS_MIRROR + " int,"
                + KEY_IS_HAMMER + " int,"
                + KEY_IS_PICTURE + " int,"
                + KEY_IS_TABLE_MOVE + " int,"
                + KEY_SAFE_OPEN + " int,"
                + KEY_SAFE_KEY + " int,"
                + KEY_HATCH_KEY + " int,"
                + KEY_HAMMER + " int,"
                + KEY_ON_LIGHT + " int,"
                + KEY_RIGHT_DOOR_KEY + " int,"
                + KEY_MAIN_KEY + " int"

                + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE);

        onCreate(db);
    }


    public long fillDB(int UserId, String UserName) {

        long idRow=-1;
        try {
            database = this.getWritableDatabase();

            if (database != null) {

                ContentValues newValues = new ContentValues();
                newValues.put(KEY_USER_IS_CURRENT, 0);
                database.update(TABLE, newValues, null, null);
            }

            if (database != null) {

                contentValues = new ContentValues();
                contentValues.put(KEY_USER_ID,UserId);
                contentValues.put(KEY_USER_NAME,UserName);
                contentValues.put(KEY_USER_TIME,"0");
                contentValues.put(KEY_USER_IS_CURRENT,1);
                contentValues.put(KEY_FRAGMENT_NAME, "MainRoomFragment");
                contentValues.put(KEY_IS_LOCKED_LEFT_DOOR, 1);
                contentValues.put(KEY_IS_LOCKED_RIGHT_DOOR, 1);
                contentValues.put(KEY_IS_LOCKED_MAIN_DOOR, 1);
                contentValues.put(KEY_IS_TABLE_MOVE, 0);
                contentValues.put(KEY_IS_LOCKED_HATCH, 1);
                contentValues.put(KEY_IS_LIGHT, 0);
                contentValues.put(KEY_ON_LIGHT, 0);
                contentValues.put(KEY_IS_PICTURE, 1);
                contentValues.put(KEY_SAFE_OPEN, 0);
                contentValues.put(KEY_IS_MIRROR, 1);
                contentValues.put(KEY_IS_KEY_HATCH, 0);
                contentValues.put(KEY_IS_CABLE, 0);
                contentValues.put(KEY_IS_HAMMER, 0);
                contentValues.put(KEY_IS_HATCH_OPEN, 0);
                contentValues.put(KEY_IS_HATCH_KEY_TAKE, 0);
                contentValues.put(KEY_COUNT_TOUCH_TABLE, 0);
                contentValues.put(KEY_COUNT_TOUCH_TREE, 0);
                contentValues.put(KEY_HAMMER, 0);
                contentValues.put(KEY_CABLE, 0);
                contentValues.put(KEY_HATCH_KEY, 0);
                contentValues.put(KEY_SAFE_KEY, 0);
                contentValues.put(KEY_RIGHT_DOOR_KEY, 0);
                contentValues.put(KEY_MAIN_KEY, 0);

                idRow = database.insert(TABLE, null, contentValues);

                Log.d(TAG, String.valueOf("idRow = " + idRow));
                database.close();

                showDB();
            }
            else
                Log.d(TAG, "Error fillDB");

        } catch (Exception e) {
            Log.d(TAG, " Error  ", e);
        } finally {
            if (database != null) database.close();
            return idRow;
        }


    }

    public User getCurrentUser() {

        User currentUser =null;
        try {
            database = this.getWritableDatabase();

            if (database != null) {

                cursor = database.query(TABLE, null,
                        KEY_USER_IS_CURRENT + " = ?", new String[]{String.valueOf(1)},
                        null, null, null);

                if (cursor.moveToFirst()) {

                    currentUser = new User();
                    currentUser.setLogin(cursor.getString(cursor.getColumnIndex(KEY_USER_NAME)));
                    currentUser.setIdUser(cursor.getInt(cursor.getColumnIndex(KEY_ID)));

                    Log.d(TAG, "CurrentUser : Login = " + currentUser.getLogin() + ", IdUser = " + currentUser.getIdUser());
                } else
                    Log.d(TAG, "0 rows in getValue");

                cursor.close();
                database.close();


            } else
                Log.d(TAG, "Error getValue");

        } catch (Exception e) {
            Log.d(TAG, "Error ", e);
        } finally {
            if (database != null) database.close();
            if (cursor != null) cursor.close();
            return currentUser;
        }


    }


    public void saveValueInDB(int id, String key, int value) {
        try {
            database = this.getWritableDatabase();

            if (database != null) {

                ContentValues newValues = new ContentValues();
                newValues.put(key, value);
                String WHERE = KEY_ID + "=" + String.valueOf(id);
                database.update(TABLE, newValues, WHERE, null);
                database.close();

            } else
                Log.d(TAG, "Error saveInDB");
        } catch (Exception e) {
            Log.d(TAG, "Error ", e);
        } finally {
            if (database != null) database.close();
        }

    }

    public void saveValueInDB(int id, String key, String value) {
        try {
            database = this.getWritableDatabase();

            if (database != null) {
                ContentValues newValues = new ContentValues();
                newValues.put(key, value);
                String WHERE = KEY_ID + "=" + String.valueOf(id);
                database.update(TABLE, newValues, WHERE, null);
                database.close();

                Log.d(TAG, "SaveInDb : "  + ", key = " + key + ", value = " + value);

            } else
                Log.d(TAG, "Error SaveInDB");
        } catch (Exception e) {
            Log.d(TAG, "Error ", e);
        } finally {
            if (database != null) database.close();
        }

    }

    public int getValueIntFromDB(int id, String key) {
        int value = -1;

        try {
            database = this.getWritableDatabase();

            if (database != null) {

                cursor = database.query(TABLE, new String[]{key},
                        KEY_ID + " = ?", new String[]{String.valueOf(id)},
                        null, null, null);

                if (cursor.moveToFirst()) {

                    value = cursor.getInt(cursor.getColumnIndex(key));

                    Log.d(TAG, "ID = " + id + ", key = " + key + ", value = " + String.valueOf(value));
                } else
                    Log.d(TAG, "0 rows in getValue");

                cursor.close();
                database.close();

            } else
                Log.d(TAG, "Error getValue");

        } catch (Exception e) {
            Log.d(TAG, "Error ", e);
        } finally {
            if (database != null) database.close();
            if (cursor != null) cursor.close();
            return value;
        }


    }


    public String getValueStringFromDB(int id, String key) {

        String value = "";

        try {
            database = this.getWritableDatabase();

            if (database != null) {

                cursor = database.query(TABLE, new String[]{key},
                        KEY_ID + " = ?", new String[]{String.valueOf(id)},
                        null, null, null);

                if (cursor.moveToFirst()) {

                    value = cursor.getString(cursor.getColumnIndex(key));

                    Log.d(TAG, "getValueStringFromDB :  "  + ", key = " + key + ", value = " + value);
                } else
                    Log.d(TAG, "0 rows in getValueStringFromDB");

                cursor.close();
                database.close();

            } else
                Log.d(TAG, "Error getValueStringFromDB");


        } catch (Exception e) {
            Log.d(TAG, "Error ", e);
        } finally {
            if (database != null) database.close();
            if (cursor != null) cursor.close();
            return value;
        }


    }


    public void showDB() {
        try {

            database = getWritableDatabase();

            if (database != null) {

                cursor = database.query(DBHelper.TABLE, null, null, null, null, null, null);

                if (cursor.moveToFirst()) {

                    do {
                        Log.d(TAG, "ID = " + cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ID)) +
                                ", UserId = " + cursor.getString(cursor.getColumnIndex(KEY_USER_ID))+
                                ", UserName = " + cursor.getString(cursor.getColumnIndex(KEY_USER_NAME))+
                                ", UserTime = " + cursor.getString(cursor.getColumnIndex(KEY_USER_TIME))+
                                ", UserIsCurrent = " + cursor.getString(cursor.getColumnIndex(KEY_USER_IS_CURRENT))+
                                ", NameFragment = " + cursor.getString(cursor.getColumnIndex(KEY_FRAGMENT_NAME))+
                                ", IsLockedLeftDoor = " + cursor.getInt(cursor.getColumnIndex(KEY_IS_LOCKED_LEFT_DOOR)) +
                                ", IsLockedRightDoor = " + cursor.getInt(cursor.getColumnIndex(KEY_IS_LOCKED_RIGHT_DOOR)) +
                                ", IsLockedMainDoor = " + cursor.getInt(cursor.getColumnIndex(KEY_IS_LOCKED_MAIN_DOOR)) +
                                ", IsTableMove = " + cursor.getInt(cursor.getColumnIndex(KEY_IS_TABLE_MOVE)) +
                                ", IsLockedHatch = " + cursor.getInt(cursor.getColumnIndex(KEY_IS_LOCKED_HATCH)) +
                                ", IsLight = " + cursor.getInt(cursor.getColumnIndex(KEY_IS_LIGHT)) +
                                ", OnLight = " + cursor.getInt(cursor.getColumnIndex(KEY_ON_LIGHT)) +
                                ", IsPicture = " + cursor.getInt(cursor.getColumnIndex(KEY_IS_PICTURE)) +
                                ", SafeOpen = " + cursor.getInt(cursor.getColumnIndex(KEY_SAFE_OPEN)) +
                                ", IsMirror = " + cursor.getInt(cursor.getColumnIndex(KEY_IS_MIRROR)) +
                                ", IsKeyHatch = " + cursor.getInt(cursor.getColumnIndex(KEY_IS_KEY_HATCH)) +
                                ", IsCable = " + cursor.getInt(cursor.getColumnIndex(KEY_IS_CABLE)) +
                                ", IsHammer = " + cursor.getInt(cursor.getColumnIndex(KEY_IS_HAMMER)) +
                                ", IsHatchOpen = " + cursor.getInt(cursor.getColumnIndex(KEY_IS_HATCH_OPEN)) +
                                ", IsHatchKeyTake = " + cursor.getInt(cursor.getColumnIndex(KEY_IS_HATCH_KEY_TAKE)) +
                                ", CountTouchTable = " + cursor.getInt(cursor.getColumnIndex(KEY_COUNT_TOUCH_TABLE)) +
                                ", CountTouchTree = " + cursor.getInt(cursor.getColumnIndex(KEY_COUNT_TOUCH_TREE)) +
                                ", Hammer = " + cursor.getInt(cursor.getColumnIndex(KEY_HAMMER)) +
                                ", Cable = " + cursor.getInt(cursor.getColumnIndex(KEY_CABLE)) +
                                ", KeyHatch = " + cursor.getInt(cursor.getColumnIndex(KEY_HATCH_KEY)) +
                                ", KeyRightDoor = " + cursor.getInt(cursor.getColumnIndex(KEY_RIGHT_DOOR_KEY)) +
                                ", MainKey = " + cursor.getInt(cursor.getColumnIndex(KEY_MAIN_KEY)) +
                                ", SafeKey = " + cursor.getInt(cursor.getColumnIndex(KEY_SAFE_KEY)));
                    } while (cursor.moveToNext());
                } else
                    Log.d(TAG, "0 rows");

                cursor.close();
                database.close();
            } else
                Log.d(TAG, "Error showDB ");


        } catch (Exception e) {
            Log.d(TAG, "Error", e);

        } finally {
            if (database != null) database.close();
            if (cursor != null) cursor.close();
        }
    }
}