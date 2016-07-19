package com.project.yuliya.roomescape.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.project.yuliya.roomescape.R;
import com.project.yuliya.roomescape.adapter.usersArrayAdapter;
import com.project.yuliya.roomescape.asyncTask.SaveNewUserTask;
import com.project.yuliya.roomescape.asyncTask.getTopRateUsersTask;
import com.project.yuliya.roomescape.asyncTask.saveUserTimeTask;
import com.project.yuliya.roomescape.classes.User;
import com.project.yuliya.roomescape.constans.dbKeys;
import com.project.yuliya.roomescape.helper.DBHelper;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class GameOverActivity extends AppCompatActivity {


    DBHelper DBHelper;
    int userIdLocal;
    ListView listViewUsers;
    TextView numCurrentUser, nameCurrentUser, timeCurrentUser;
    Integer userRate;
    ShareButton shareButton;
    ShareLinkContent content;
    CallbackManager callbackManager;
    AdView adView;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

            FacebookSdk.sdkInitialize(getApplicationContext());
            AppEventsLogger.activateApp(this);
            callbackManager = CallbackManager.Factory.create();
            DBHelper = new DBHelper(this);
            currentUser = new User();

            setContentView(R.layout.activity_game_over);

            listViewUsers = (ListView) findViewById(R.id.listViewUsers);
            numCurrentUser = (TextView) findViewById(R.id.numCurrentUser);
            nameCurrentUser = (TextView) findViewById(R.id.nameCurrentUser);
            timeCurrentUser = (TextView) findViewById(R.id.timeCurrentUser);
            shareButton = (ShareButton) findViewById(R.id.shareButton);

            //запускаем рекламный банер
            adView = (AdView)this.findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);

            userIdLocal=getIntent().getExtras().getInt("userIdLocal");
            currentUser.setLocalId(userIdLocal);
            currentUser.setGlobalId(Long.valueOf(DBHelper.getValueStringFromDB(userIdLocal, dbKeys.KEY_USER_GLOBAL_ID)));
            currentUser.setName(DBHelper.getValueStringFromDB(userIdLocal, dbKeys.KEY_USER_NAME));
            currentUser.setTime(Long.valueOf(DBHelper.getValueStringFromDB(userIdLocal, dbKeys.KEY_USER_TIME)));

            if (savedInstanceState == null)
                MediaPlayer.create(this, R.raw.likovanietolpy).start();

            //заполняем рейтинги
            fillRateCurrentUser();

            //вешаем модель на кнопку "поделиться"
            content = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=com.project.yuliya.roomescape"))
                    .setContentTitle("Room Escape")
                    .setContentDescription("Мой результат : "+usersArrayAdapter.printTime(currentUser.getTime())
                            +" - это "+String.valueOf(userRate)+" место!!!" )
                    .setImageUrl(Uri.parse("http://www.2fons.ru/pic/201407/1366x768/2fons.ru-49964.jpg"))
                    .build();

            shareButton.setShareContent(content);

            Log.d(dbKeys.TAG, "userIdGlobal : " + currentUser.getGlobalId() + " useridlocal : " + userIdLocal + " login : " + currentUser.getName()
                    + " time : " + currentUser.getTime());



        } catch (Exception e) {
            Log.d(dbKeys.TAG, "Error : onCreateGameOver " + e);
        }

    }

    public void fillRateCurrentUser() {

        try {

            //если пользователь еще не авторизован на серере сохраняем его
            if(currentUser.getGlobalId()==0) {
                Long globalId = (Long) new SaveNewUserTask().execute(currentUser).get();
                //если получилось, заносим его globalId в локальную базу
                if(globalId!=null) {
                    currentUser.setGlobalId(globalId);
                    DBHelper.saveValueInDB(userIdLocal, dbKeys.KEY_USER_GLOBAL_ID, String.valueOf(globalId));
                }

            }


            if(currentUser.getGlobalId()!=0)
            {
                //отправляем на сервер время игрока и получаем его место в рейтинге
                userRate =(Integer) new saveUserTimeTask().execute(currentUser).get();

                if(userRate!=null)
                    fillGlobalRate();
                else {
                    userRate = DBHelper.getRateUser(currentUser);
                    fillLocalRate();
                }
            }
            //если не получилось связаться с сервером, выводим локальный рейтинг
            else
            {
                userRate = DBHelper.getRateUser(currentUser);
                fillLocalRate();

            }

            numCurrentUser.setText(String.valueOf(userRate));
            nameCurrentUser.setText(currentUser.getName());
            timeCurrentUser.setText(usersArrayAdapter.printTime(currentUser.getTime()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void fillLocalRate(){

        try
        {
            //запрашиваем рейтинг локаьных игроков
            ArrayList<User> users = DBHelper.getTopRate();

            if (users != null) {
                ArrayAdapter<User> adapter = new usersArrayAdapter(this, users);
                listViewUsers.setAdapter(adapter);
            }
        }
        catch (Exception e)
        {
            Log.d(dbKeys.TAG, "Error : fillLocalRate " + e);
        }

    }

    public void fillGlobalRate() {
        try {

            //запрашиваем рейтинг глобальных игроков
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


    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}
