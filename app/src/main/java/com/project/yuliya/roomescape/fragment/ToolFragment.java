package com.project.yuliya.roomescape.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.project.yuliya.roomescape.eventBus.BusProvider;
import com.project.yuliya.roomescape.eventBus.ToolChangeEvent;
import com.project.yuliya.roomescape.R;
import com.project.yuliya.roomescape.constans.Action;
import com.project.yuliya.roomescape.constans.ToolName;
import com.project.yuliya.roomescape.constans.dbKeys;
import com.project.yuliya.roomescape.helper.DBHelper;
import com.squareup.otto.Subscribe;


public class ToolFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "MyLog";

    RadioGroup RBG;
    RadioButton rb1,rb2,rb3,rb4,rb5,rb6;
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

        /*RBG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                ((RadioButton)RBG.getChildAt(RBG.getCheckedRadioButtonId())).startAnimation(anim);//.setBackgroundColor(getResources().getColor(R.color.trans));
            }
        });*/

        rb1 = (RadioButton)view.findViewById(R.id.RB1);
        rb2 = (RadioButton)view.findViewById(R.id.RB2);
        rb3 = (RadioButton)view.findViewById(R.id.RB3);
        rb4 = (RadioButton)view.findViewById(R.id.RB4);
        rb5 = (RadioButton)view.findViewById(R.id.RB5);
        rb6 = (RadioButton)view.findViewById(R.id.RB6);

        rb1.setOnClickListener(this);
        rb2.setOnClickListener(this);
        rb3.setOnClickListener(this);
        rb4.setOnClickListener(this);
        rb5.setOnClickListener(this);
        rb6.setOnClickListener(this);

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
    public void onClick(View v) {

        Animation anim = AnimationUtils.loadAnimation(context, R.anim.alfa);
        switch (v.getId())
        {
            case R.id.RB1:
                rb1.startAnimation(anim);break;
            case R.id.RB2:
                rb2.startAnimation(anim);break;
            case R.id.RB3:
                rb3.startAnimation(anim);break;
            case R.id.RB4:
                rb4.startAnimation(anim);break;
            case R.id.RB5:
                rb5.startAnimation(anim);break;
            case R.id.RB6:
                rb6.startAnimation(anim);break;

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