package com.project.yuliya.canyouescape.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.project.yuliya.canyouescape.constans.dbKeys;
import com.project.yuliya.canyouescape.classes.User;

import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper {

    SQLiteDatabase database;
    ContentValues contentValues;
    Cursor cursor;

    public DBHelper(Context context) {
        super(context, dbKeys.DATABASE_NAME, null, dbKeys.DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d(dbKeys.TAG, "Enter onCreateDB");


        db.execSQL("create table " + dbKeys.TABLE + "("
                + dbKeys.KEY_ID + " integer primary key,"
                + dbKeys.KEY_USER_GLOBAL_ID + " String,"
                + dbKeys.KEY_USER_NAME + " String,"
                + dbKeys.KEY_USER_TIME + " String,"
                + dbKeys.KEY_USER_IS_CURRENT + " int,"
                + dbKeys.KEY_USER_IS_FINISH + " int,"
                + dbKeys.KEY_FRAGMENT_NAME + " String,"
                + dbKeys.KEY_CABLE + " int,"
                + dbKeys.KEY_IS_CABLE + " int,"
                + dbKeys.KEY_COUNT_TOUCH_TABLE + " int,"
                + dbKeys.KEY_COUNT_TOUCH_TREE + " int,"
                + dbKeys.KEY_IS_HATCH_KEY_TAKE + " int,"
                + dbKeys.KEY_IS_HATCH_OPEN + " int,"
                + dbKeys.KEY_IS_KEY_HATCH + " int,"
                + dbKeys.KEY_IS_LIGHT + " int,"
                + dbKeys.KEY_IS_LOCKED_HATCH + " int,"
                + dbKeys.KEY_IS_LOCKED_LEFT_DOOR + " int,"
                + dbKeys.KEY_IS_LOCKED_RIGHT_DOOR + " int,"
                + dbKeys.KEY_IS_LOCKED_MAIN_DOOR + " int,"
                + dbKeys.KEY_IS_MIRROR + " int,"
                + dbKeys.KEY_IS_HAMMER + " int,"
                + dbKeys.KEY_IS_PICTURE + " int,"
                + dbKeys.KEY_IS_TABLE_MOVE + " int,"
                + dbKeys.KEY_SAFE_OPEN + " int,"
                + dbKeys.KEY_SAFE_KEY + " int,"
                + dbKeys.KEY_HATCH_KEY + " int,"
                + dbKeys.KEY_HAMMER + " int,"
                + dbKeys.KEY_ON_LIGHT + " int,"
                + dbKeys.KEY_RIGHT_DOOR_KEY + " int,"
                + dbKeys.KEY_MAIN_KEY + " int"

                + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + dbKeys.TABLE);

        onCreate(db);
    }


    public int saveNewUser(int globalId, String UserName) {

        int localId=-1;
        try {
            database = this.getWritableDatabase();

            if (database != null) {

                ContentValues newValues = new ContentValues();
                newValues.put(dbKeys.KEY_USER_IS_CURRENT, 0);
                database.update(dbKeys.TABLE, newValues, null, null);
            }

            if (database != null) {

                contentValues = new ContentValues();
                contentValues.put(dbKeys.KEY_USER_GLOBAL_ID,String.valueOf(globalId));
                contentValues.put(dbKeys.KEY_USER_NAME,UserName);
                contentValues.put(dbKeys.KEY_USER_TIME,"0");
                contentValues.put(dbKeys.KEY_USER_IS_CURRENT,1);
                contentValues.put(dbKeys.KEY_USER_IS_FINISH,0);
                contentValues.put(dbKeys.KEY_FRAGMENT_NAME, "MainRoomFragment");
                contentValues.put(dbKeys.KEY_IS_LOCKED_LEFT_DOOR, 1);
                contentValues.put(dbKeys.KEY_IS_LOCKED_RIGHT_DOOR, 1);
                contentValues.put(dbKeys.KEY_IS_LOCKED_MAIN_DOOR, 1);
                contentValues.put(dbKeys.KEY_IS_TABLE_MOVE, 0);
                contentValues.put(dbKeys.KEY_IS_LOCKED_HATCH, 1);
                contentValues.put(dbKeys.KEY_IS_LIGHT, 0);
                contentValues.put(dbKeys.KEY_ON_LIGHT, 0);
                contentValues.put(dbKeys.KEY_IS_PICTURE, 1);
                contentValues.put(dbKeys.KEY_SAFE_OPEN, 0);
                contentValues.put(dbKeys.KEY_IS_MIRROR, 1);
                contentValues.put(dbKeys.KEY_IS_KEY_HATCH, 0);
                contentValues.put(dbKeys.KEY_IS_CABLE, 0);
                contentValues.put(dbKeys.KEY_IS_HAMMER, 0);
                contentValues.put(dbKeys.KEY_IS_HATCH_OPEN, 0);
                contentValues.put(dbKeys.KEY_IS_HATCH_KEY_TAKE, 0);
                contentValues.put(dbKeys.KEY_COUNT_TOUCH_TABLE, 0);
                contentValues.put(dbKeys.KEY_COUNT_TOUCH_TREE, 0);
                contentValues.put(dbKeys.KEY_HAMMER, 0);
                contentValues.put(dbKeys.KEY_CABLE, 0);
                contentValues.put(dbKeys.KEY_HATCH_KEY, 0);
                contentValues.put(dbKeys.KEY_SAFE_KEY, 0);
                contentValues.put(dbKeys.KEY_RIGHT_DOOR_KEY, 0);
                contentValues.put(dbKeys.KEY_MAIN_KEY, 0);

                localId =(int)database.insert(dbKeys.TABLE, null, contentValues);

                Log.d(dbKeys.TAG, String.valueOf("localId = " + localId));
                database.close();

                showDB();
            }
            else
                Log.d(dbKeys.TAG, "Error saveNewUser");

        } catch (Exception e) {
            Log.d(dbKeys.TAG, " Error  ", e);
        } finally {
            if (database != null) database.close();
            return localId;
        }


    }

    public void changeCurrentUser(int idNewCurrentUser)
    {
        try {
            database = this.getWritableDatabase();

            if (database != null) {

                ContentValues newValues = new ContentValues();
                newValues.put(dbKeys.KEY_USER_IS_CURRENT, 0);
                database.update(dbKeys.TABLE, newValues, null, null);

                saveValueInDB(idNewCurrentUser,dbKeys.KEY_USER_IS_CURRENT,1);
            }

        } catch (Exception e) {
            Log.d(dbKeys.TAG, " Error  ", e);
        } finally {
            if (database != null) database.close();
        }

    }

    public User getCurrentUser() {

        User currentUser =null;
        try {
            database = this.getWritableDatabase();

            if (database != null) {

                cursor = database.query(dbKeys.TABLE, null,
                        dbKeys.KEY_USER_IS_CURRENT + " = ?", new String[]{String.valueOf(1)},
                        null, null, null);

                if (cursor.moveToFirst()) {

                    currentUser = new User();
                    currentUser.setName(cursor.getString(cursor.getColumnIndex(dbKeys.KEY_USER_NAME)));
                    currentUser.setLocalId(cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_ID)));

                    Log.d(dbKeys.TAG, "getCurrrentUser : CurrentUser : Login = " + currentUser.getName()
                            +", LocalId = " + currentUser.getLocalId());
                } else
                    Log.d(dbKeys.TAG, "0 rows in getValue");

                cursor.close();
                database.close();


            } else
                Log.d(dbKeys.TAG, "Error getValue");

        } catch (Exception e) {
            Log.d(dbKeys.TAG, "Error ", e);
        } finally {
            if (database != null) database.close();
            if (cursor != null) cursor.close();
            return currentUser;
        }


    }

    public Integer getRateUser(User user) {

        ArrayList<User> topRateUsers=SortLocalUsers();

        Integer rate = -1 ;
        for (int i = 0; i < topRateUsers.size() ; i++) {

            if(topRateUsers.get(i).getLocalId() == user.getLocalId())
            {
                rate = Integer.valueOf(i+1);break;
            }
        }
        return rate;
    }



    public ArrayList<User> getTopRate() {
        ArrayList<User> Users=SortLocalUsers();
        ArrayList<User> topRateUsers = new ArrayList<>();
        int j =0;
        for (int i = 0; i <Users.size() ; i++) {
            if(j==4) break;
            topRateUsers.add(Users.get(i));
            j++;

        }

        return topRateUsers;
    }

    public ArrayList<User> SortLocalUsers() {

        ArrayList<User> users =null;
        try {
            database = this.getWritableDatabase();

            if (database != null) {

                cursor = database.query(dbKeys.TABLE, null,
                        null, null,
                        null, null, dbKeys.KEY_USER_TIME+" ASC");

                if (cursor.moveToFirst()) {
                    users = new ArrayList<User>();
                    do {
                        if(cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_USER_IS_FINISH))==0) continue;

                        User user = new User();
                        user.setName(cursor.getString(cursor.getColumnIndex(dbKeys.KEY_USER_NAME)));
                        user.setLocalId(cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_ID)));
                        user.setGlobalId(cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_USER_GLOBAL_ID)));
                        user.setTime(cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_USER_TIME)));
                        users.add(user);
                    } while (cursor.moveToNext());


                } else
                    Log.d(dbKeys.TAG, "0 rows in getValue");

                cursor.close();
                database.close();


            } else
                Log.d(dbKeys.TAG, "Error getValue");

        } catch (Exception e) {
            Log.d(dbKeys.TAG, "Error ", e);
        } finally {
            if (database != null) database.close();
            if (cursor != null) cursor.close();
            return users;
        }


    }

    public ArrayList<User> getLocalUsers() {

        ArrayList<User> users =null;
        try {
            database = this.getWritableDatabase();

            if (database != null) {

                cursor = database.query(dbKeys.TABLE, null,
                        null, null,
                        null, null, null);

                if (cursor.moveToFirst()) {
                    users = new ArrayList<User>();
                    do {
                        int isCurrent=cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_USER_IS_CURRENT));
                        if( isCurrent == 1 ) continue;

                        User user = new User();
                        user.setName(cursor.getString(cursor.getColumnIndex(dbKeys.KEY_USER_NAME)));
                        user.setLocalId(cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_ID)));
                        user.setGlobalId(cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_USER_GLOBAL_ID)));
                        user.setTime(cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_USER_TIME)));
                        users.add(user);
                    } while (cursor.moveToNext());


                } else
                    Log.d(dbKeys.TAG, "0 rows in getValue");

                cursor.close();
                database.close();


            } else
                Log.d(dbKeys.TAG, "Error getValue");

        } catch (Exception e) {
            Log.d(dbKeys.TAG, "Error ", e);
        } finally {
            if (database != null) database.close();
            if (cursor != null) cursor.close();
            return users;
        }


    }


    public void saveValueInDB(int localId, String key, int value) {
        try {
            database = this.getWritableDatabase();

            if (database != null) {

                ContentValues newValues = new ContentValues();
                newValues.put(key, value);
                String WHERE = dbKeys.KEY_ID + "=" + String.valueOf(localId);
                database.update(dbKeys.TABLE, newValues, WHERE, null);
                database.close();

            } else
                Log.d(dbKeys.TAG, "Error saveInDB");
        } catch (Exception e) {
            Log.d(dbKeys.TAG, "Error ", e);
        } finally {
            if (database != null) database.close();
        }

    }

    public void saveValueInDB(int localId, String key, String value) {
        try {
            database = this.getWritableDatabase();

            if (database != null) {
                ContentValues newValues = new ContentValues();
                newValues.put(key, value);
                String WHERE = dbKeys.KEY_ID + "=" + String.valueOf(localId);
                database.update(dbKeys.TABLE, newValues, WHERE, null);
                database.close();

                Log.d(dbKeys.TAG, "SaveInDb : "  + ", key = " + key + ", value = " + value);

            } else
                Log.d(dbKeys.TAG, "Error SaveInDB");
        } catch (Exception e) {
            Log.d(dbKeys.TAG, "Error ", e);
        } finally {
            if (database != null) database.close();
        }

    }

    public int getValueIntFromDB(int localId, String key) {
        int value = -1;

        try {
            database = this.getWritableDatabase();

            if (database != null) {

                cursor = database.query(dbKeys.TABLE, new String[]{key},
                        dbKeys.KEY_ID + " = ?", new String[]{String.valueOf(localId)},
                        null, null, null);

                if (cursor.moveToFirst()) {

                    value = cursor.getInt(cursor.getColumnIndex(key));

                    Log.d(dbKeys.TAG, "localId = " + localId + ", key = " + key + ", value = " + String.valueOf(value));
                } else
                    Log.d(dbKeys.TAG, "0 rows in getValue");

                cursor.close();
                database.close();

            } else
                Log.d(dbKeys.TAG, "Error getValue");

        } catch (Exception e) {
            Log.d(dbKeys.TAG, "Error ", e);
        } finally {
            if (database != null) database.close();
            if (cursor != null) cursor.close();
            return value;
        }


    }


    public String getValueStringFromDB(int localId, String key) {

        String value = "";

        try {
            database = this.getWritableDatabase();

            if (database != null) {

                cursor = database.query(dbKeys.TABLE, new String[]{key},
                        dbKeys.KEY_ID + " = ?", new String[]{String.valueOf(localId)},
                        null, null, null);

                if (cursor.moveToFirst()) {

                    value = cursor.getString(cursor.getColumnIndex(key));

                    Log.d(dbKeys.TAG, "getValueStringFromDB :  "  + ", key = " + key + ", value = " + value);
                } else
                    Log.d(dbKeys.TAG, "0 rows in getValueStringFromDB");

                cursor.close();
                database.close();

            } else
                Log.d(dbKeys.TAG, "Error getValueStringFromDB");


        } catch (Exception e) {
            Log.d(dbKeys.TAG, "Error ", e);
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

                cursor = database.query(dbKeys.TABLE, null, null, null, null, null, null);

                if (cursor.moveToFirst()) {

                    do {
                        Log.d(dbKeys.TAG, "localId = " + cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_ID)) +
                                ", globalId = " + cursor.getString(cursor.getColumnIndex(dbKeys.KEY_USER_GLOBAL_ID))+
                                ", UserName = " + cursor.getString(cursor.getColumnIndex(dbKeys.KEY_USER_NAME))+
                                ", UserIsCurrent = " + cursor.getString(cursor.getColumnIndex(dbKeys.KEY_USER_IS_CURRENT))+
                                ", NameFragment = " + cursor.getString(cursor.getColumnIndex(dbKeys.KEY_FRAGMENT_NAME))+
                                ", IsLockedLeftDoor = " + cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_IS_LOCKED_LEFT_DOOR)) +
                                ", IsLockedRightDoor = " + cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_IS_LOCKED_RIGHT_DOOR)) +
                                ", IsLockedMainDoor = " + cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_IS_LOCKED_MAIN_DOOR)) +
                                ", IsTableMove = " + cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_IS_TABLE_MOVE)) +
                                ", IsLockedHatch = " + cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_IS_LOCKED_HATCH)) +
                                ", IsLight = " + cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_IS_LIGHT)) +
                                ", OnLight = " + cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_ON_LIGHT)) +
                                ", IsPicture = " + cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_IS_PICTURE)) +
                                ", SafeOpen = " + cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_SAFE_OPEN)) +
                                ", IsMirror = " + cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_IS_MIRROR)) +
                                ", IsKeyHatch = " + cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_IS_KEY_HATCH)) +
                                ", IsCable = " + cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_IS_CABLE)) +
                                ", IsHammer = " + cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_IS_HAMMER)) +
                                ", IsHatchOpen = " + cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_IS_HATCH_OPEN)) +
                                ", IsHatchKeyTake = " + cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_IS_HATCH_KEY_TAKE)) +
                                ", CountTouchTable = " + cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_COUNT_TOUCH_TABLE)) +
                                ", CountTouchTree = " + cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_COUNT_TOUCH_TREE)) +
                                ", Hammer = " + cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_HAMMER)) +
                                ", Cable = " + cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_CABLE)) +
                                ", KeyHatch = " + cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_HATCH_KEY)) +
                                ", KeyRightDoor = " + cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_RIGHT_DOOR_KEY)) +
                                ", MainKey = " + cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_MAIN_KEY)) +
                                ", SafeKey = " + cursor.getInt(cursor.getColumnIndex(dbKeys.KEY_SAFE_KEY)));
                    } while (cursor.moveToNext());
                } else
                    Log.d(dbKeys.TAG, "0 rows");

                cursor.close();
                database.close();
            } else
                Log.d(dbKeys.TAG, "Error showDB ");


        } catch (Exception e) {
            Log.d(dbKeys.TAG, "Error", e);

        } finally {
            if (database != null) database.close();
            if (cursor != null) cursor.close();
        }
    }
}