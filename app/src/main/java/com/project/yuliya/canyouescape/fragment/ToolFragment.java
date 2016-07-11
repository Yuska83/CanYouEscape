package com.project.yuliya.canyouescape.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.project.yuliya.canyouescape.eventBus.BusProvider;
import com.project.yuliya.canyouescape.eventBus.ToolChangeEvent;
import com.project.yuliya.canyouescape.R;
import com.project.yuliya.canyouescape.constans.Action;
import com.project.yuliya.canyouescape.constans.ToolName;
import com.project.yuliya.canyouescape.constans.dbKeys;
import com.project.yuliya.canyouescape.helper.DBHelper;
import com.squareup.otto.Subscribe;


public class ToolFragment extends Fragment {

    public static final String TAG = "MyLog";

    RadioGroup RBG;
    Context context;
    DBHelper DBHelper;
    int idRowUser;

    //для взаимодействия с MainActivity
    private OnInstrumentalFragmentListener mListener;

    public interface OnInstrumentalFragmentListener {
        int getUserIdLocal();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tool_fragment, container, false);
        context = view.getContext();
        DBHelper = new DBHelper(context);

        RBG = (RadioGroup) view.findViewById(R.id.RBGroup);

        fillTools();

        return view;
    }



    @Subscribe
    public void onToolChange(ToolChangeEvent event)
    {
        ToolName nameTool = event.getNameTool();
        int numTool = event.getNumTool();
        Action action = event.getAction();

        if(action == Action.Used)
        {
            RBG.getChildAt(numTool).setBackgroundColor(Color.rgb(54,192,235));
            RBG.getChildAt(numTool).setEnabled(false);
        }
        else
        {
            RBG.getChildAt(numTool).setEnabled(true);

            switch (nameTool)
            {
                case KeyForRightDoor: RBG.getChildAt(0).setBackground(this.getResources().getDrawable(R.drawable.key_right_door));break;
                case Cable: RBG.getChildAt(1).setBackground(this.getResources().getDrawable(R.drawable.cable));break;
                case Hammer: RBG.getChildAt(2).setBackground(this.getResources().getDrawable(R.drawable.hammer));break;
                case KeyForHatch: RBG.getChildAt(3).setBackground(this.getResources().getDrawable(R.drawable.key_hatch));break;
                case KeyForSafe: RBG.getChildAt(4).setBackground(this.getResources().getDrawable(R.drawable.key_safe));break;
                case KeyMain: RBG.getChildAt(5).setBackground(this.getResources().getDrawable(R.drawable.key_main));break;

            }
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInstrumentalFragmentListener) {
            mListener = (OnInstrumentalFragmentListener) context;
            idRowUser = mListener.getUserIdLocal();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnInstrumentalFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);

    }

    private void fillTools() {
        if(DBHelper.getValueIntFromDB(idRowUser, dbKeys.KEY_RIGHT_DOOR_KEY) == 1)
        {
            RBG.getChildAt(0).setEnabled(true);
            RBG.getChildAt(0).setBackground(this.getResources().getDrawable(R.drawable.key_right_door));
        }
        if(DBHelper.getValueIntFromDB(idRowUser, dbKeys.KEY_HAMMER) == 1)
        {
            RBG.getChildAt(2).setEnabled(true);
            RBG.getChildAt(2).setBackground(this.getResources().getDrawable(R.drawable.hammer));
        }
        if(DBHelper.getValueIntFromDB(idRowUser, dbKeys.KEY_CABLE) == 1)
        {
            RBG.getChildAt(1).setEnabled(true);
            RBG.getChildAt(1).setBackground(this.getResources().getDrawable(R.drawable.cable_little));
        }
        if(DBHelper.getValueIntFromDB(idRowUser, dbKeys.KEY_HATCH_KEY) == 1)
        {
            RBG.getChildAt(3).setEnabled(true);
            RBG.getChildAt(3).setBackground(this.getResources().getDrawable(R.drawable.key_hatch));
        }
        if(DBHelper.getValueIntFromDB(idRowUser, dbKeys.KEY_SAFE_KEY) == 1)
        {
            RBG.getChildAt(4).setEnabled(true);
            RBG.getChildAt(4).setBackground(this.getResources().getDrawable(R.drawable.key_safe));
        }
        if(DBHelper.getValueIntFromDB(idRowUser, dbKeys.KEY_MAIN_KEY) == 1)
        {
            RBG.getChildAt(5).setEnabled(true);
            RBG.getChildAt(5).setBackground(this.getResources().getDrawable(R.drawable.key_main));
        }
    }
}