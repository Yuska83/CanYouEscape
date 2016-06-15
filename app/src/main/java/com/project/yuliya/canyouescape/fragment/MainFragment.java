package com.project.yuliya.canyouescape.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.yuliya.canyouescape.EventBus.BusProvider;
import com.project.yuliya.canyouescape.R;
import com.project.yuliya.canyouescape.helper.DBHelper;


public class MainFragment extends Fragment {

    public static final String TAG = "MyLog";
    public String fragmentName;
    DBHelper dbHelper;
    Context context;
    ToolFragment tool;
    Animation anim;
    View view;
    RelativeLayout myLayout;
    TextView messageBox;


    public void replaceFragment(Fragment newFragment)
    {
        try
        {
            getFragmentManager().beginTransaction()
                    .replace(R.id.main_fragment_container, newFragment)
                    .addToBackStack(null)
                    .commit();
        }
        catch (Exception e)
        {
            Log.e(TAG,"onReplaceFragment:",e);
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
        //dbHelper = new DBHelper(context);
        messageBox.setText("");
        dbHelper.saveFragmentNameInDB(fragmentName);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
        dbHelper.saveFragmentNameInDB(fragmentName);
    }

}
