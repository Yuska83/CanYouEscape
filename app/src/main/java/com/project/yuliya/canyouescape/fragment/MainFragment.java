package com.project.yuliya.canyouescape.fragment;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.yuliya.canyouescape.eventBus.BusProvider;
import com.project.yuliya.canyouescape.R;
import com.project.yuliya.canyouescape.constans.dbKeys;
import com.project.yuliya.canyouescape.helper.DBHelper;


public class MainFragment extends Fragment {

    int userIdLocal;
    public String fragmentName;
    DBHelper DBHelper;
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
            Log.e(dbKeys.TAG,"onReplaceFragment:",e);
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
        messageBox.setText("");
        DBHelper.saveValueInDB(userIdLocal, dbKeys.KEY_FRAGMENT_NAME, fragmentName);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
        DBHelper.saveValueInDB(userIdLocal, dbKeys.KEY_FRAGMENT_NAME, fragmentName);
    }

}
