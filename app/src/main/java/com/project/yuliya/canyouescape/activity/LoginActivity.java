package com.project.yuliya.canyouescape.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.project.yuliya.canyouescape.R;
import com.project.yuliya.canyouescape.forserver.User;
import com.project.yuliya.canyouescape.forserver.SaveNewUserTask;
import com.project.yuliya.canyouescape.helper.DBHelper;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "MyLog";
    DBHelper dbHelper;
    Button play, changeUser;
    EditText loginEditText;
    TextView loginCurrentUser;

    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //deleteDatabase(DBHelper.DATABASE_NAME);
        dbHelper = new DBHelper(this);

        loginEditText = (EditText) findViewById(R.id.loginNewUser);
        loginCurrentUser = (TextView)findViewById(R.id.loginCurrentUser);
        play = (Button) findViewById(R.id.play);
        changeUser = (Button) findViewById(R.id.changeUser);


        currentUser = dbHelper.getCurrentUser();
        if(currentUser != null)
        {
            loginEditText.setVisibility(View.GONE);
            loginCurrentUser.setVisibility(View.VISIBLE);
            changeUser.setVisibility(View.VISIBLE);

            loginCurrentUser.setText(currentUser.getLogin());

        }


        if(savedInstanceState==null)
            MediaPlayer.create(this, R.raw.wincheme).start();


    }

    public void onClickChangePlayer(View view)
    {
        loginEditText.setVisibility(View.VISIBLE);
    }


    public void onClickPlay(View view)
    {
        long idUser = -1;
        if(loginEditText.getVisibility()==View.VISIBLE)
        {
            if(loginEditText.getText().equals("")) return;


            SaveNewUserTask task = new SaveNewUserTask();
            task.execute(loginEditText.getText().toString());

            try {
                currentUser =(User) task.get();
                idUser = dbHelper.fillDB((int)currentUser.getId(),currentUser.getLogin());

                if(idUser != -1)
                    MediaPlayer.create(view.getContext(), R.raw.buttonenter).start();


            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


        }
        else
            idUser = currentUser.getIdUser();

        if(idUser!=-1)
        {
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            intent.putExtra("idUser", (int) idUser);
            startActivity(intent);
            finish();
        }
        else
            Log.d(TAG,"Error : idUser = -1");


    }
}
