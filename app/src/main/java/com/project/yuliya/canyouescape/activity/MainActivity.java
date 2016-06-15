package com.project.yuliya.canyouescape.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.project.yuliya.canyouescape.EventBus.BusProvider;
import com.project.yuliya.canyouescape.EventBus.ToolChangeEvent;
import com.project.yuliya.canyouescape.R;
import com.project.yuliya.canyouescape.fragment.CombinationLockFragment;
import com.project.yuliya.canyouescape.fragment.HatchFragment;
import com.project.yuliya.canyouescape.fragment.LeftDoorFragment;
import com.project.yuliya.canyouescape.fragment.LeftRoomFragment;
import com.project.yuliya.canyouescape.fragment.MainFragment;
import com.project.yuliya.canyouescape.fragment.MainRoomFragment;
import com.project.yuliya.canyouescape.fragment.RightDoorFragment;
import com.project.yuliya.canyouescape.fragment.RightRoomFragment;
import com.project.yuliya.canyouescape.fragment.SafetyBoxFragment;
import com.project.yuliya.canyouescape.fragment.ToolFragment;
import com.project.yuliya.canyouescape.helper.DBHelper;
import com.squareup.otto.Bus;

public class MainActivity extends AppCompatActivity implements ToolFragment.OnInstrumentalFragmentListener {

    public static final String TAG = "MyLog";
    DBHelper dbHelper;
    FragmentTransaction fragmentTransaction;

    String nameCurrentFragment;
    int RadioButtonSelectedId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);

        try {
            nameCurrentFragment = dbHelper.getFragmentNameFromDB();

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
                                .replace(R.id.main_fragment_container, new RightRoomFragment()).addToBackStack(null)
                                .commit();
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

    //методы интерфейсов********************************************************************
    @Override
    public void setRadioButtonSelectedId(int RadioButtonSelectedId) {this.RadioButtonSelectedId = RadioButtonSelectedId;}

}
