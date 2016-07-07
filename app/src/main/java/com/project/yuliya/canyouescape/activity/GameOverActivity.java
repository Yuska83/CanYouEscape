package com.project.yuliya.canyouescape.activity;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.project.yuliya.canyouescape.R;
import com.project.yuliya.canyouescape.adapter.usersArrayAdapter;
import com.project.yuliya.canyouescape.constans.dbKeys;
import com.project.yuliya.canyouescape.classes.User;
import com.project.yuliya.canyouescape.asyncTask.getTopRateUsersTask;
import com.project.yuliya.canyouescape.asyncTask.saveUserTimeTask;
import com.project.yuliya.canyouescape.helper.DBHelper;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class GameOverActivity extends AppCompatActivity {


    DBHelper DBHelper;
    int userIdLocal;
    String userName;
    long userIdGlobal, userTime;
    ListView listViewUsers;
    TextView numCurrentUser, nameCurrentUser, timeCurrentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        try {
            DBHelper = new DBHelper(this);

            listViewUsers = (ListView) findViewById(R.id.listViewUsers);
            numCurrentUser = (TextView) findViewById(R.id.numCurrentUser);
            nameCurrentUser = (TextView) findViewById(R.id.nameCurrentUser);
            timeCurrentUser = (TextView) findViewById(R.id.timeCurrentUser);

            userIdLocal = getIntent().getExtras().getInt("userIdLocal");
            userIdGlobal = Long.valueOf(DBHelper.getValueIntFromDB(userIdLocal, dbKeys.KEY_USER_ID));
            userName = DBHelper.getValueStringFromDB(userIdLocal, dbKeys.KEY_USER_NAME);
            userTime = Long.valueOf(DBHelper.getValueStringFromDB(userIdLocal, dbKeys.KEY_USER_TIME));


            Log.d(dbKeys.TAG, "userIdGlobal : " + userIdGlobal + " useridlocal : " + userIdLocal + " login : " + userName + " time : " + userTime);

            if (savedInstanceState == null)
                MediaPlayer.create(this, R.raw.likovanietolpy).start();

            fillRateCurrentUser();
            fillListUsers();

        } catch (Exception e) {
            Log.d(dbKeys.TAG, "Error : onCreateGameOver " + e);
        }

    }

    public void fillListUsers() {
        try {

            //запрашиваем рейтинг игроков
            ArrayList<User> users = (ArrayList<User>) new getTopRateUsersTask().execute().get();

            if (users != null) {
                ArrayAdapter<User> adapter = new usersArrayAdapter(this, users);
                listViewUsers.setAdapter(adapter);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public void fillRateCurrentUser() {

        try {

            //отправляем на сервер время игрока и получаем его место в рейтинге
            Integer userRate = (Integer) new saveUserTimeTask().execute(new User(userIdGlobal, userName, userTime)).get();

            if (userRate != -1 && userRate != 0) {
                numCurrentUser.setText(String.valueOf(userRate));
                nameCurrentUser.setText(userName);
                timeCurrentUser.setText(usersArrayAdapter.printTime(userTime));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
