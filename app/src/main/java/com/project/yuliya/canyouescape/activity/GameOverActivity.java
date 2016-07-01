package com.project.yuliya.canyouescape.activity;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.project.yuliya.canyouescape.R;
import com.project.yuliya.canyouescape.adapter.usersArrayAdapter;
import com.project.yuliya.canyouescape.forserver.Constants;
import com.project.yuliya.canyouescape.forserver.User;
import com.project.yuliya.canyouescape.forserver.getTopRateUsersTask;
import com.project.yuliya.canyouescape.forserver.saveUserTimeTask;
import com.project.yuliya.canyouescape.helper.DBHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class GameOverActivity extends AppCompatActivity {

    public static final String TAG = "MyLog";
    DBHelper dbHelper;
    int userId;
    String userName;
    long userTime;
    ListView listViewUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        listViewUsers = (ListView)findViewById(R.id.listViewUsers) ;
        dbHelper = new DBHelper(this);

        userId =getIntent().getExtras().getInt("idUser");
        userName = dbHelper.getValueStringFromDB(userId,DBHelper.KEY_USER_NAME);
        userTime = Long.valueOf(dbHelper.getValueStringFromDB(userId,DBHelper.KEY_USER_TIME));

        long id  =Long.valueOf( dbHelper.getValueIntFromDB(userId,DBHelper.KEY_USER_ID));

        Log.d(Constants.TAG,"&&&id : "+ id+"userid : "+ userId+" login : "+userName +" time : "+userTime);

        Date time = new Date(userTime);
        Log.d(TAG,"EndTime = " + String.valueOf(time) + "FormTime = " + String.valueOf(userTime));


        //отправляем на серер время игрока
        new saveUserTimeTask().execute(new User(id,userName,userTime));

        //запрашиваем рейтинг игроков
        try {
            ArrayList<User> users =(ArrayList<User>) new getTopRateUsersTask().execute().get();

            if(users!= null) {
                ArrayAdapter<User> adapter = new usersArrayAdapter(this, users);
                listViewUsers.setAdapter(adapter);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        MediaPlayer.create(this, R.raw.likovanietolpy).start();
    }
}
