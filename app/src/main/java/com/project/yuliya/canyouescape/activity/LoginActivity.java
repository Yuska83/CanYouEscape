package com.project.yuliya.canyouescape.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.project.yuliya.canyouescape.R;
import com.project.yuliya.canyouescape.adapter.usersLocalExpandListAdapter;
import com.project.yuliya.canyouescape.classes.User;
import com.project.yuliya.canyouescape.asyncTask.SaveNewUserTask;
import com.project.yuliya.canyouescape.helper.DBHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "MyLog";
    DBHelper DBHelper;
    Button play;
    EditText loginEditText;
    ExpandableListView loginCurrentUsers;
    usersLocalExpandListAdapter adapter;
    LoginButton loginButton;
    CallbackManager callbackManager;

    String nameCurrentUser;
    User currentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //deleteDatabase(dbKeys.DATABASE_NAME);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        //MultiDex.install(this);
        setContentView(R.layout.activity_login);


        loginButton = (LoginButton)findViewById(R.id.login_button) ;
        loginEditText = (EditText) findViewById(R.id.loginNewUser);
        loginCurrentUsers = (ExpandableListView)findViewById(R.id.loginCurrentUsers);
        play = (Button) findViewById(R.id.play);

        loginButton.setReadPermissions(Arrays.asList("public_profile"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Profile user = Profile.getCurrentProfile();
                loginEditText.setText(user.getFirstName());
                AccessToken a = AccessToken.getCurrentAccessToken();
                Log.d(TAG,"UserID = "+a.getUserId());
            }

            @Override
            public void onCancel() {
                loginEditText.setText("");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.e(TAG,exception.toString());
            }
        });


        DBHelper = new DBHelper(this);



        currentUser = DBHelper.getCurrentUser();
        if(currentUser!= null) {

            nameCurrentUser = currentUser.getLogin();
            loginEditText.setText(currentUser.getLogin());


            final HashMap<String, List<User>> expDetails = new HashMap<>();
            final ArrayList<User> localUsersNames = DBHelper.getLocalUsers();
            expDetails.put(currentUser.getLogin(), localUsersNames);


            final ArrayList<String> localUser = new ArrayList<>(expDetails.keySet());
            adapter = new usersLocalExpandListAdapter(this, localUser, expDetails);
            loginCurrentUsers.setAdapter(adapter);

            loginCurrentUsers.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {

                    currentUser = expDetails.get(localUser.get(groupPosition)).get(childPosition);
                    loginEditText.setText(currentUser.getLogin());
                    nameCurrentUser = currentUser.getLogin();

                    return false;
                }
            });

        }
        else

        loginEditText.setVisibility(View.VISIBLE);

        if(savedInstanceState==null)
            MediaPlayer.create(this, R.raw.wincheme).start();

       // showHash();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void showHash() {

        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("com.project.yuliya.canyouescape", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e(TAG, something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }

    }

    public void onClickCurrentUserName(View view)
    {
        loginEditText.setVisibility(View.VISIBLE);
    }


    public void onClickPlay(View view)
    {
        long userIdLocal = -1;
        String newName = loginEditText.getText().toString();

        if(!newName.equals(nameCurrentUser))
        {
            if(loginEditText.getText().equals("")) return;


            SaveNewUserTask task = new SaveNewUserTask();
            task.execute(newName);

            try {
                currentUser =(User) task.get();
                userIdLocal = DBHelper.fillDB((int)currentUser.getId(),currentUser.getLogin());

                if(userIdLocal != -1)
                    MediaPlayer.create(view.getContext(), R.raw.buttonenter).start();


            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


        }
        else
            userIdLocal = currentUser.getIdUser();

        if(userIdLocal!=-1)
        {
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            intent.putExtra("userIdLocal", (int) userIdLocal);
            startActivity(intent);
            finish();
        }
        else
            Log.d(TAG,"Error : userIdLocal = -1");


    }
}
