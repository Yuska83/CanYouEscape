package com.project.yuliya.canyouescape.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

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
import com.project.yuliya.canyouescape.constans.URL;
import com.project.yuliya.canyouescape.constans.dbKeys;
import com.project.yuliya.canyouescape.helper.DBHelper;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LoginActivity extends AppCompatActivity {


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

        try {

            FacebookSdk.sdkInitialize(getApplicationContext());
            AppEventsLogger.activateApp(this);
            callbackManager = CallbackManager.Factory.create();
            DBHelper = new DBHelper(this);
            setContentView(R.layout.activity_login);


            loginButton = (LoginButton)findViewById(R.id.login_button) ;
            loginEditText = (EditText) findViewById(R.id.loginNewUser);
            loginCurrentUsers = (ExpandableListView)findViewById(R.id.loginCurrentUsers);
            play = (Button) findViewById(R.id.play);

            //facebook login
            loginButton.setReadPermissions(Arrays.asList("public_profile"));
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Profile user = Profile.getCurrentProfile();
                    if(user!=null) {
                        loginEditText.setText(user.getFirstName());
                        AccessToken a = AccessToken.getCurrentAccessToken();
                        Log.d(dbKeys.TAG, "userFacebookId = " + a.getUserId());
                    }
                    else
                        loginEditText.setText("");
                }

                @Override
                public void onCancel() {
                    loginEditText.setText("");
                }

                @Override
                public void onError(FacebookException exception) {
                    Toast.makeText(getApplicationContext(),
                            R.string.facebook_error, Toast.LENGTH_SHORT).show();
                    Log.e(dbKeys.TAG,exception.toString());
                }
            });


            //Проверяем, есть ли текущий пользователь
            currentUser = DBHelper.getCurrentUser();

            if(currentUser!= null)
                //если есть хотя бы один пользователь заполняем список пользователей

                fillListUser();


            if(savedInstanceState==null)
                MediaPlayer.create(this, R.raw.wincheme).start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onClickPlay(View view)
    {
        int localId = -1;

        if(loginEditText.getVisibility()==View.VISIBLE) {
            if (loginEditText.getText().equals(""))
                Toast.makeText(getApplicationContext(), R.string.enter_name, Toast.LENGTH_LONG).show();

            else {

                //RestTemplate template = new RestTemplate();
                //template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                //User user = new User(loginEditText.getText().toString());
                currentUser = new User(loginEditText.getText().toString());
                localId = DBHelper.saveNewUser(0, loginEditText.getText().toString());

            }
        }
        else
            localId = currentUser.getLocalId();

        if(localId!=-1)
        {
            MediaPlayer.create(view.getContext(), R.raw.buttonenter).start();
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            intent.putExtra("userIdLocal", (int) localId);
            startActivity(intent);
            finish();
        }
        else
            Log.d(dbKeys.TAG,"Error : userIdLocal = -1");

    }

    public void fillListUser()
    {
        nameCurrentUser = currentUser.getName();
        loginEditText.setVisibility(View.INVISIBLE);

        final HashMap<String, List<User>> expDetails = new HashMap<>();
        final ArrayList<User> localUsersNames = DBHelper.getLocalUsers();
        expDetails.put(currentUser.getName(), localUsersNames);

        final ArrayList<String> localUser = new ArrayList<>(expDetails.keySet());
        adapter = new usersLocalExpandListAdapter(this, localUser, expDetails);
        loginCurrentUsers.setAdapter(adapter);

        loginCurrentUsers.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                loginEditText.setVisibility(View.VISIBLE);
                loginEditText.setHint(R.string.enter_you_name);
                return false;
            }
        });


        loginCurrentUsers.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                currentUser = expDetails.get(localUser.get(groupPosition)).get(childPosition);
                loginEditText.setVisibility(View.INVISIBLE);
                nameCurrentUser = currentUser.getName();
                DBHelper.changeCurrentUser(currentUser.getLocalId());
                fillListUser();
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


//************************************************************************************************************
    /*private void showHash() {

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

    }*/
}