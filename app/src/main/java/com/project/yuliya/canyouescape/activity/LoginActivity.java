package com.project.yuliya.canyouescape.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.project.yuliya.canyouescape.R;
import com.project.yuliya.canyouescape.forserver.UserTask;
import com.project.yuliya.canyouescape.helper.DBHelper;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "MyLog";
    DBHelper dbHelper;
    Button play;
    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        deleteDatabase(DBHelper.DATABASE_NAME);
        dbHelper = new DBHelper(this);
        dbHelper.fillDB();

        name = (EditText) findViewById(R.id.name);
        play = (Button) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(name.getText().equals("")) return;

                UserTask task = new UserTask();
                task.execute(name.getText().toString());

                MediaPlayer.create(v.getContext(), R.raw.buttonenter).start();
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if(savedInstanceState==null)
            MediaPlayer.create(this, R.raw.wincheme).start();


    }

}
