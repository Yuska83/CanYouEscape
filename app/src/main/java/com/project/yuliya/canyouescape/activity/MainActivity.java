package com.project.yuliya.canyouescape.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.project.yuliya.canyouescape.R;
import com.project.yuliya.canyouescape.constans.dbKeys;
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

    DBHelper DBHelper;
    FragmentTransaction fragmentTransaction;

    String nameCurrentFragment;
    public int userIdLocal;
    public long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userIdLocal =getIntent().getExtras().getInt("userIdLocal");

        setContentView(R.layout.activity_main);
        DBHelper = new DBHelper(this);
        time = new Date().getTime();// запоминаем текущее время

        try {
            nameCurrentFragment = DBHelper.getValueStringFromDB(userIdLocal, dbKeys.KEY_FRAGMENT_NAME);

            if (savedInstanceState == null) {

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_fragment_container,new MainRoomFragment())
                        .commit();

                switch (nameCurrentFragment) {

                    case "LeftRoomFragment": {

                        replaceFragment(new LeftDoorFragment());
                        replaceFragment(new LeftRoomFragment());
                        break;
                    }
                    case "RightRoomFragment": {
                        replaceFragment(new RightDoorFragment());
                        replaceFragment(new RightRoomFragment());
                        break;
                    }
                    case "LeftDoorFragment": {
                        replaceFragment(new LeftDoorFragment());
                        break;
                    }
                    case "RightDoorFragment": {
                        replaceFragment(new RightDoorFragment());
                        break;
                    }
                    case "CombinationLockFragment": {
                        replaceFragment(new LeftDoorFragment());
                        replaceFragment(new CombinationLockFragment());
                        break;
                    }
                    case "HatchFragment": {
                        replaceFragment(new HatchFragment());
                        break;
                    }
                    case "SafetyBoxFragment": {
                        replaceFragment(new SafetyBoxFragment());
                        break;
                    }
                }


            }

        } catch (Exception e) {
            Log.e(dbKeys.TAG,"onClick:",e);
        }

    }

    //метод интерфейса, передает значение ID пользователя********************************************************************
    @Override
    public int getUserIdLocal() {
        return this.userIdLocal;
    }


    //***************************************************************************************
    @Override
    protected void onPause() {
        super.onPause();

        //считаем прошедшее время и записываем его в базу
        time = new Date().getTime() - time;
        Log.d(dbKeys.TAG,"time = " +String.valueOf(time));

        long oldTime = Long.valueOf(DBHelper.getValueStringFromDB(userIdLocal, dbKeys.KEY_USER_TIME));

        time += oldTime;
        DBHelper.saveValueInDB(userIdLocal, dbKeys.KEY_USER_TIME,String.valueOf(time));
    }

    @Override
    protected void onResume() {
        super.onResume();
        //запускаем счет времени
        time = new Date().getTime();
    }

    public void replaceFragment(Fragment newFragment)
    {
        try
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_fragment_container, newFragment)
                    .addToBackStack(null)
                    .commit();
        }
        catch (Exception e)
        {
            Log.e(dbKeys.TAG,"onReplaceFragment:",e);
        }
    }
}
