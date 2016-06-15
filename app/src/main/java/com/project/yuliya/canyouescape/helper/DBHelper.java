package com.project.yuliya.canyouescape.helper;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper {

    SQLiteDatabase database;
    ContentValues contentValues;
    Cursor cursor;

    public static final String TAG = "MyLog";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MyDB";
    public static final String TABLE = "game";

    public static final String KEY_ID = "_id";
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

        Log.d(TAG, "Enter onCreate");


        db.execSQL("create table " + TABLE + "(" + KEY_ID + " integer primary key,"
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


    public void fillDB() {

        try {
            database = getWritableDatabase();

            if (database != null) {

                contentValues = new ContentValues();
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

                long idRoow = database.insert(TABLE, null, contentValues);

                Log.d(TAG, String.valueOf(idRoow));
                database.close();
            } else

                Log.d(TAG, "ошибка fillDB");


        } catch (Exception e) {
            Log.d(TAG, " ошибка ", e);

        }

        showDB();

    }

    public void saveInDB(String key, int value) {
        try {
            database = this.getWritableDatabase();

            if (database != null) {

                ContentValues newValues = new ContentValues();

                newValues.put(key, value);

                String WHERE = KEY_ID + "=" + String.valueOf(1);

                database.update(TABLE, newValues, WHERE, null);

                database.close();

            } else
                Log.d(TAG, "ошибка saveInDB");
        } catch (Exception e) {
            Log.d(TAG, "ошибка ", e);
        } finally {
            if (database != null) database.close();
        }

    }


    public int getValue(String key) {
        int value = -1;

        try {
            database = this.getWritableDatabase();

            if (database != null) {

                cursor = database.query(TABLE, new String[]{key},
                        KEY_ID + " = ?", new String[]{String.valueOf(1)},
                        null, null, null);

                if (cursor.moveToFirst()) {

                    value = cursor.getInt(cursor.getColumnIndex(key));

                    Log.d(TAG, "ID = " + 1 + ", key = " + key + ", value = " + String.valueOf(value));
                } else
                    Log.d(TAG, "0 roows in getValue");

                cursor.close();
                database.close();
            } else
                Log.d(TAG, "ошибка getValue");


            return value;

        } catch (Exception e) {
            Log.d(TAG, "ошибка ", e);
            return value;
        } finally {
            if (database != null) database.close();
            if (cursor != null) cursor.close();
            return value;
        }


    }

    public void saveFragmentNameInDB(String value) {
        try {
            database = this.getWritableDatabase();

            if (database != null) {
                ContentValues newValues = new ContentValues();
                newValues.put(KEY_FRAGMENT_NAME, value);
                String WHERE = KEY_ID + "=" + String.valueOf(1);
                database.update(TABLE, newValues, WHERE, null);
                database.close();

                Log.d(TAG, "SaveName : "  + ", key = " + KEY_FRAGMENT_NAME + ", value = " + String.valueOf(value));

            } else
                Log.d(TAG, "ошибка saveNameInDB");
        } catch (Exception e) {
            Log.d(TAG, "ошибка ", e);
        } finally {
            if (database != null) database.close();
        }

    }

    public String getFragmentNameFromDB() {

        String value = "";

        try {
            database = this.getWritableDatabase();

            if (database != null) {

                cursor = database.query(TABLE, new String[]{KEY_FRAGMENT_NAME},
                        KEY_ID + " = ?", new String[]{String.valueOf(1)},
                        null, null, null);

                if (cursor.moveToFirst()) {

                    value = cursor.getString(cursor.getColumnIndex(KEY_FRAGMENT_NAME));

                    Log.d(TAG, "GetName :  "  + ", key = " + KEY_FRAGMENT_NAME + ", value = " + value);
                } else
                    Log.d(TAG, "0 roows in getValue");

                cursor.close();
                database.close();

            } else
                Log.d(TAG, "ошибка getValue");

            return value;

        } catch (Exception e) {
            Log.d(TAG, "ошибка ", e);
            return value;
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
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int NameFragment = cursor.getColumnIndex(KEY_FRAGMENT_NAME);
                    int IsLockedLeftDoor = cursor.getColumnIndex(KEY_IS_LOCKED_LEFT_DOOR);
                    int IsLockedRightDoor = cursor.getColumnIndex(KEY_IS_LOCKED_RIGHT_DOOR);
                    int IsLockedMainDoor = cursor.getColumnIndex(KEY_IS_LOCKED_MAIN_DOOR);
                    int IsTableMove = cursor.getColumnIndex(KEY_IS_TABLE_MOVE);
                    int IsLockedHatch = cursor.getColumnIndex(KEY_IS_LOCKED_HATCH);
                    int IsLight = cursor.getColumnIndex(KEY_IS_LIGHT);
                    int IsOnLight = cursor.getColumnIndex(KEY_ON_LIGHT);
                    int IsPicture = cursor.getColumnIndex(KEY_IS_PICTURE);
                    int LittleDoorOpen = cursor.getColumnIndex(KEY_SAFE_OPEN);
                    int IsMirror = cursor.getColumnIndex(KEY_IS_MIRROR);
                    int IsKeyLuchok = cursor.getColumnIndex(KEY_IS_KEY_HATCH);
                    int IsCabel = cursor.getColumnIndex(KEY_IS_CABLE);
                    int IsMolotok = cursor.getColumnIndex(KEY_IS_HAMMER);
                    int IsHatchOpen = cursor.getColumnIndex(KEY_IS_HATCH_OPEN);
                    int IsHatchKeyTake = cursor.getColumnIndex(KEY_IS_HATCH_KEY_TAKE);
                    int CountTable = cursor.getColumnIndex(KEY_COUNT_TOUCH_TABLE);
                    int CountTree = cursor.getColumnIndex(KEY_COUNT_TOUCH_TREE);
                    int Molotok = cursor.getColumnIndex(KEY_HAMMER);
                    int Cabel = cursor.getColumnIndex(KEY_CABLE);
                    int KeyLuchok = cursor.getColumnIndex(KEY_HATCH_KEY);
                    int KeyRightDoor = cursor.getColumnIndex(KEY_SAFE_KEY);
                    int LittleKey = cursor.getColumnIndex(KEY_RIGHT_DOOR_KEY);
                    int MainKey = cursor.getColumnIndex(KEY_MAIN_KEY);


                    do {
                        Log.d(TAG, "ID = " + cursor.getInt(idIndex) +
                                ", NameFragment = " + cursor.getString(NameFragment)+
                                ", IsLockedLeftDoor = " + cursor.getInt(IsLockedLeftDoor) +
                                ", IsLockedRightDoor = " + cursor.getInt(IsLockedRightDoor) +
                                ", IsLockedMainDoor = " + cursor.getInt(IsLockedMainDoor) +
                                ", IsTableMove = " + cursor.getInt(IsTableMove) +
                                ", IsLockedHatch = " + cursor.getInt(IsLockedHatch) +
                                ", IsLight = " + cursor.getInt(IsLight) +
                                ", IsOnLight = " + cursor.getInt(IsOnLight) +
                                ", IsPicture = " + cursor.getInt(IsPicture) +
                                ", LittleDoorOpen = " + cursor.getInt(LittleDoorOpen) +
                                ", IsMirror = " + cursor.getInt(IsMirror) +
                                ", IsKeyLuchok = " + cursor.getInt(IsKeyLuchok) +
                                ", IsCabel = " + cursor.getInt(IsCabel) +
                                ", IsMolotok = " + cursor.getInt(IsMolotok) +
                                ", IsHatchOpen = " + cursor.getInt(IsHatchOpen) +
                                ", IsHatchKeyTake = " + cursor.getInt(IsHatchKeyTake) +
                                ", CountTable = " + cursor.getInt(CountTable) +
                                ", CountTree = " + cursor.getInt(CountTree) +
                                ", Molotok = " + cursor.getInt(Molotok) +
                                ", Cabel = " + cursor.getInt(Cabel) +
                                ", KeyLuchok = " + cursor.getInt(KeyLuchok) +
                                ", KeyRightDoor = " + cursor.getInt(KeyRightDoor) +
                                ", MainKey = " + cursor.getInt(MainKey) +
                                ", LittleKey = " + cursor.getInt(LittleKey));
                    } while (cursor.moveToNext());
                } else
                    Log.d(TAG, "0 rows");

                cursor.close();
                database.close();
            } else
                Log.d(TAG, "ошибка showDB ");


        } catch (Exception e) {
            Log.d(TAG, "ошибка", e);

        } finally {
            if (database != null) database.close();
            if (cursor != null) cursor.close();
        }
    }
}