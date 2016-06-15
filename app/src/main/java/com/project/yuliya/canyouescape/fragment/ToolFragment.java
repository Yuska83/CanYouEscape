package com.project.yuliya.canyouescape.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.project.yuliya.canyouescape.EventBus.BusProvider;
import com.project.yuliya.canyouescape.EventBus.ToolChangeEvent;
import com.project.yuliya.canyouescape.R;
import com.project.yuliya.canyouescape.enums.Action;
import com.project.yuliya.canyouescape.enums.ToolName;
import com.project.yuliya.canyouescape.helper.DBHelper;
import com.squareup.otto.Subscribe;


public class ToolFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "MyLog";
    private static final String KEY_LIST_EVENT = "ListEvent";

    RadioGroup RBG;
    RadioButton RB1,RB2,RB3,RB4,RB5,RB6;
    Context context;
    DBHelper dbHelper;

    //для взаимодействия с MainActivity
    private OnInstrumentalFragmentListener mListener;

    public interface OnInstrumentalFragmentListener {
        void setRadioButtonSelectedId(int RadioButtonSelectedId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tool_fragment, container, false);
        context = view.getContext();
        dbHelper= new DBHelper(context);

        RBG = (RadioGroup) view.findViewById(R.id.RBGroup);

        RB1 = (RadioButton) view.findViewById(R.id.RB1);
        RB2 = (RadioButton) view.findViewById(R.id.RB2);
        RB3 = (RadioButton) view.findViewById(R.id.RB3);
        RB4 = (RadioButton) view.findViewById(R.id.RB4);
        RB5 = (RadioButton) view.findViewById(R.id.RB5);
        RB6 = (RadioButton) view.findViewById(R.id.RB6);


        RB1.setOnClickListener(this);
        RB2.setOnClickListener(this);
        RB3.setOnClickListener(this);
        RB4.setOnClickListener(this);
        RB5.setOnClickListener(this);
        RB6.setOnClickListener(this);

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
    public void onClick(View view) {

        if (mListener != null) {
            switch (view.getId()) {
                case R.id.RB1: mListener.setRadioButtonSelectedId(1);break;
                case R.id.RB2: mListener.setRadioButtonSelectedId(2);break;
                case R.id.RB3: mListener.setRadioButtonSelectedId(3);break;
                case R.id.RB4: mListener.setRadioButtonSelectedId(4);break;
                case R.id.RB5: mListener.setRadioButtonSelectedId(5);break;
                case R.id.RB6: mListener.setRadioButtonSelectedId(6);break;

            }
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInstrumentalFragmentListener) {
            mListener = (OnInstrumentalFragmentListener) context;
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
        if(dbHelper.getValue(DBHelper.KEY_RIGHT_DOOR_KEY) == 1)
        {
            RBG.getChildAt(0).setEnabled(true);
            RBG.getChildAt(0).setBackground(this.getResources().getDrawable(R.drawable.key_right_door));
        }
        if(dbHelper.getValue(DBHelper.KEY_HAMMER) == 1)
        {
            RBG.getChildAt(2).setEnabled(true);
            RBG.getChildAt(2).setBackground(this.getResources().getDrawable(R.drawable.hammer));
        }
        if(dbHelper.getValue(DBHelper.KEY_CABLE) == 1)
        {
            RBG.getChildAt(1).setEnabled(true);
            RBG.getChildAt(1).setBackground(this.getResources().getDrawable(R.drawable.cable_little));
        }
        if(dbHelper.getValue(DBHelper.KEY_HATCH_KEY) == 1)
        {
            RBG.getChildAt(3).setEnabled(true);
            RBG.getChildAt(3).setBackground(this.getResources().getDrawable(R.drawable.key_hatch));
        }
        if(dbHelper.getValue(DBHelper.KEY_SAFE_KEY) == 1)
        {
            RBG.getChildAt(4).setEnabled(true);
            RBG.getChildAt(4).setBackground(this.getResources().getDrawable(R.drawable.key_safe));
        }
        if(dbHelper.getValue(DBHelper.KEY_MAIN_KEY) == 1)
        {
            RBG.getChildAt(5).setEnabled(true);
            RBG.getChildAt(5).setBackground(this.getResources().getDrawable(R.drawable.key_main));
        }
    }
}