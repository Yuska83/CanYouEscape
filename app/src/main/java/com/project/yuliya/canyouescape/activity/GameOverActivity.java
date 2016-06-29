package com.project.yuliya.canyouescape.activity;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.project.yuliya.canyouescape.R;
import com.project.yuliya.canyouescape.adapter.usersArrayAdapter;
import com.project.yuliya.canyouescape.forserver.SaveNewUserTask;
import com.project.yuliya.canyouescape.forserver.User;
import com.project.yuliya.canyouescape.forserver.getTopRateUsersTask;
import com.project.yuliya.canyouescape.helper.DBHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class GameOverActivity extends AppCompatActivity {

    public static final String TAG = "MyLog";
    DBHelper dbHelper;
    int idUser;
    ListView listViewUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        dbHelper = new DBHelper(this);
        idUser =getIntent().getExtras().getInt("idUser");

        listViewUsers = (ListView)findViewById(R.id.listViewUsers) ;

        getTopRateUsersTask task = new getTopRateUsersTask();
        task.execute();

        try {
            ArrayList<User> users =(ArrayList<User>) task.get();

            if(users!= null) {
                ArrayAdapter<User> adapter = new usersArrayAdapter(this, users);
                listViewUsers.setAdapter(adapter);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Long time = Long.valueOf(dbHelper.getValueStringFromDB(idUser,DBHelper.KEY_USER_TIME));
        Date userTime = new Date(time);

        Log.d(TAG,"EndTime = " + String.valueOf(time) + "FormTime = " + String.valueOf(userTime));
        MediaPlayer.create(this, R.raw.likovanietolpy).start();
    }
}
