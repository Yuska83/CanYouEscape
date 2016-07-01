package com.project.yuliya.canyouescape.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.project.yuliya.canyouescape.R;
import com.project.yuliya.canyouescape.fragment.CombinationLockFragment;
import com.project.yuliya.canyouescape.fragment.HatchFragment;
import com.project.yuliya.canyouescape.fragment.LeftDoorFragment;
import com.project.yuliya.canyouescape.fragment.LeftRoomFragment;
import com.project.yuliya.canyouescape.fragment.MainRoomFragment;
import com.project.yuliya.canyouescape.fragment.RightDoorFragment;
import com.project.yuliya.canyouescape.fragment.RightRoomFragment;
import com.project.yuliya.canyouescape.fragment.SafetyBoxFragment;
import com.project.yuliya.canyouescape.fragment.ToolFragment;
import com.project.yuliya.canyouescape.helper.DBHelper;

import java.util.Date;


public class MainActivity extends AppCompatActivity implements ToolFragment.OnInstrumentalFragmentListener {

    public static final String TAG = "MyLog";
    DBHelper dbHelper;
    FragmentTransaction fragmentTransaction;

    String nameCurrentFragment;
    public int idUser;
    public long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        idUser =getIntent().getExtras().getInt("idUser");

        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);
        time = new Date().getTime();// запоминаем текущее время

        try {
            nameCurrentFragment = dbHelper.getValueStringFromDB(idUser,DBHelper.KEY_FRAGMENT_NAME);

            if (savedInstanceState == null) {

                fragmentTransaction = getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_fragment_container, new MainRoomFragment());

                switch (nameCurrentFragment) {

                    case "LeftRoomFragment": {
                        fragmentTransaction
                                .replace(R.id.main_fragment_container, new LeftDoorFragment()).addToBackStack(null)
                                .replace(R.id.main_fragment_container, new LeftRoomFragment()).addToBackStack(null);
                        break;
                    }
                    case "RightRoomFragment": {
                        fragmentTransaction
                                .replace(R.id.main_fragment_container, new RightDoorFragment()).addToBackStack(null)
                                .replace(R.id.main_fragment_container, new RightRoomFragment()).addToBackStack(null);
                        break;
                    }
                    case "LeftDoorFragment": {
                        fragmentTransaction
                                .replace(R.id.main_fragment_container, new LeftDoorFragment()).addToBackStack(null);
                        break;
                    }
                    case "RightDoorFragment": {
                        fragmentTransaction
                                .replace(R.id.main_fragment_container, new RightDoorFragment()).addToBackStack(null);
                        break;
                    }
                    case "CombinationLockFragment": {
                        fragmentTransaction
                                .replace(R.id.main_fragment_container, new LeftDoorFragment()).addToBackStack(null)
                                .replace(R.id.main_fragment_container, new CombinationLockFragment()).addToBackStack(null);
                        break;
                    }
                    case "HatchFragment": {
                        fragmentTransaction
                                .replace(R.id.main_fragment_container, new HatchFragment()).addToBackStack(null);
                        break;
                    }
                    case "SafetyBoxFragment": {
                        fragmentTransaction
                                .replace(R.id.main_fragment_container, new SafetyBoxFragment()).addToBackStack(null);
                        break;
                    }
                }

                fragmentTransaction.commit();
            }

        } catch (Exception e) {
            Log.e(TAG,"onClick:",e);
        }

    }

    //метод интерфейса, передает значение ID пользователя********************************************************************
    @Override
    public int getIdRowUserInDb() {
        return this.idUser;
    }


    //***************************************************************************************
    @Override
    protected void onPause() {
        super.onPause();

        //считаем прошедшее время и записываем его в базу
        time = new Date().getTime() - time;
        Log.d(TAG,"time = " +String.valueOf(time));

        long oldTime = Long.valueOf(dbHelper.getValueStringFromDB(idUser,DBHelper.KEY_USER_TIME));

        time += oldTime;
        dbHelper.saveValueInDB(idUser,DBHelper.KEY_USER_TIME,String.valueOf(time));
    }

    @Override
    protected void onResume() {
        super.onResume();
        //запускаем счет времени
        time = new Date().getTime();
    }
}
