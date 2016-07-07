package com.project.yuliya.canyouescape.fragment;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.project.yuliya.canyouescape.R;
import com.project.yuliya.canyouescape.constans.dbKeys;
import com.project.yuliya.canyouescape.helper.DBHelper;


public class CombinationLockFragment extends MainFragment implements View.OnClickListener{

    Animation anim = null;

    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,enter;
    TextView textView;
    String str;
    GridLayout codelock;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentName = "CombinationLockFragment";
        Log.e(dbKeys.TAG,"onCreateCombinationLockFragment");
        try
        {
            view = inflater.inflate(R.layout.combination_lock_fragment, container, false);
            context = view.getContext();
            DBHelper = new DBHelper(context);
            tool = (ToolFragment) getFragmentManager().findFragmentById(R.id.tool_fragment);
            userIdLocal = tool.idRowUser;

            str = new String();

            textView = (TextView)view.findViewById(R.id.textView);
            codelock = (GridLayout)view.findViewById(R.id.codelock);
            messageBox = (TextView)view.findViewById(R.id.message);

            b1 = (Button) view.findViewById(R.id.button1);
            b2 = (Button) view.findViewById(R.id.button2);
            b3 = (Button) view.findViewById(R.id.button3);
            b4 = (Button) view.findViewById(R.id.button4);
            b5 = (Button) view.findViewById(R.id.button5);
            b6 = (Button) view.findViewById(R.id.button6);
            b7 = (Button) view.findViewById(R.id.button7);
            b8 = (Button) view.findViewById(R.id.button8);
            b9 = (Button) view.findViewById(R.id.button9);
            enter = (Button) view.findViewById(R.id.btnEnter);

            b1.setOnClickListener(this);
            b2.setOnClickListener(this);
            b3.setOnClickListener(this);
            b4.setOnClickListener(this);
            b5.setOnClickListener(this);
            b6.setOnClickListener(this);
            b7.setOnClickListener(this);
            b8.setOnClickListener(this);
            b9.setOnClickListener(this);
            enter.setOnClickListener(this);

        }
        catch (Exception e)
        {
            Log.e(dbKeys.TAG,"onCreateView:",e);
        }

        return view;

    }

    @Override
    public void onClick(View view) {

        MediaPlayer.create(context, R.raw.buttonpress).start();

        if (textView.getText().equals( "ERROR!!!")  )
            str = "";

        switch(view.getId())
        {
            case R.id.button1:
                str+="1";
                textView.setText(str);
                break;
            case R.id.button2:
                str+="2";
                textView.setText(str);
                break;
            case R.id.button3:
                str+="3";
                textView.setText(str);
                break;
            case R.id.button4:
                str+="4";
                textView.setText(str);
                break;
            case R.id.button5:
                str+="5";
                textView.setText(str);
                break;
            case R.id.button6:
                str+="6";
                textView.setText(str);
                break;
            case R.id.button7:
                str+="7";
                textView.setText(str);
                break;
            case R.id.button8:
                str+="8";
                textView.setText(str);
                break;
            case R.id.button9:
                str+="9";
                textView.setText(str);
                break;

            case R.id.btnEnter:
                Result();
                break;

        }

    }

    private void Result() {
        if (TextUtils.isEmpty(textView.getText().toString()))
            return;

        MediaPlayer.create(context, R.raw.buttonenter).start();

        if (textView.getText().equals("274"))
        {
            textView.setText("YES!!!");
            codelock.setEnabled(false);
            DBHelper.saveValueInDB(userIdLocal, dbKeys.KEY_IS_LOCKED_LEFT_DOOR,0);

            getActivity().getSupportFragmentManager().popBackStack();

        }
        else
            textView.setText("ERROR!!!");

    }

}
