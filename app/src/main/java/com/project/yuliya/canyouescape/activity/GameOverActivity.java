package com.project.yuliya.canyouescape.activity;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.project.yuliya.canyouescape.R;
import com.project.yuliya.canyouescape.helper.DBHelper;

import java.util.Date;

public class GameOverActivity extends AppCompatActivity {

    public static final String TAG = "MyLog";
    DBHelper dbHelper;
    int idUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        dbHelper = new DBHelper(this);
        idUser =getIntent().getExtras().getInt("idUser");

        Long time = Long.valueOf(dbHelper.getValueStringFromDB(idUser,DBHelper.KEY_USER_TIME));
        Date userTime = new Date(time);

        Log.d(TAG,"EndTime = " + String.valueOf(time) + "FormTime = " + String.valueOf(userTime));
        MediaPlayer.create(this, R.raw.likovanietolpy).start();
    }
}
