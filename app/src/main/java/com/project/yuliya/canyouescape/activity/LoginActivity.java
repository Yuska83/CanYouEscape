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
import android.widget.ListAdapter;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.project.yuliya.canyouescape.R;
import com.project.yuliya.canyouescape.adapter.usersLocalExpandListAdapter;
import com.project.yuliya.canyouescape.classes.User;
import com.project.yuliya.canyouescape.asyncTask.SaveNewUserTask;
import com.project.yuliya.canyouescape.constans.dbKeys;
import com.project.yuliya.canyouescape.helper.DBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "MyLog";
    DBHelper DBHelper;
    Button play;
    EditText loginEditText;
    ExpandableListView loginCurrentUsers;
    usersLocalExpandListAdapter adapter;

    String nameCurrentUser;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //deleteDatabase(dbKeys.DATABASE_NAME);
        DBHelper = new DBHelper(this);

        loginEditText = (EditText) findViewById(R.id.loginNewUser);
        loginCurrentUsers = (ExpandableListView)findViewById(R.id.loginCurrentUsers);
        play = (Button) findViewById(R.id.play);

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
