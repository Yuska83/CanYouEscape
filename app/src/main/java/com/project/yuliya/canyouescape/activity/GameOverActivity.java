package com.project.yuliya.canyouescape.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.project.yuliya.canyouescape.R;
import com.project.yuliya.canyouescape.adapter.usersArrayAdapter;
import com.project.yuliya.canyouescape.constans.dbKeys;
import com.project.yuliya.canyouescape.classes.User;
import com.project.yuliya.canyouescape.asyncTask.getTopRateUsersTask;
import com.project.yuliya.canyouescape.asyncTask.saveUserTimeTask;
import com.project.yuliya.canyouescape.helper.DBHelper;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.google.*;
public class GameOverActivity extends AppCompatActivity {


    DBHelper DBHelper;
    int userIdLocal;
    String userName;
    long userIdGlobal, userTime;
    ListView listViewUsers;
    TextView numCurrentUser, nameCurrentUser, timeCurrentUser;
    Integer userRate;
    ShareButton shareButton;
    ShareLinkContent content;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);


        setContentView(R.layout.activity_game_over);

        try {


            adView = (AdView)this.findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
            DBHelper = new DBHelper(this);

            listViewUsers = (ListView) findViewById(R.id.listViewUsers);
            numCurrentUser = (TextView) findViewById(R.id.numCurrentUser);
            nameCurrentUser = (TextView) findViewById(R.id.nameCurrentUser);
            timeCurrentUser = (TextView) findViewById(R.id.timeCurrentUser);

            shareButton = (ShareButton) findViewById(R.id.shareButton);



            content = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("https://developers.facebook.com")).build();
            shareButton.setShareContent(content);



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

    public void onClickShare(View v)
    {
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        if (ShareDialog.canShow(ShareLinkContent.class)) {

            content = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("https://developers.facebook.com"))
                    .setContentTitle("Room Escape")
                    .setContentDescription("Мой результат : "+usersArrayAdapter.printTime(userTime)
                            +" - это "+String.valueOf(userRate)+" место!!!" )
                    .setImageUrl(Uri.parse("http://www.2fons.ru/pic/201407/1366x768/2fons.ru-49964.jpg"))
                    .build();

            shareDialog.show(content);
        }


    }


    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
            userRate = (Integer) new saveUserTimeTask().execute(new User(userIdGlobal, userName, userTime)).get();

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
