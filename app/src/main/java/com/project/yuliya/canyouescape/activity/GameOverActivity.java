package com.project.yuliya.canyouescape.activity;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.project.yuliya.canyouescape.R;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        MediaPlayer.create(this, R.raw.likovanietolpy).start();
    }
}
