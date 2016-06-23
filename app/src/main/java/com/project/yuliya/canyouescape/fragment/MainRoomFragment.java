package com.project.yuliya.canyouescape.fragment;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.yuliya.canyouescape.EventBus.BusProvider;
import com.project.yuliya.canyouescape.EventBus.ToolChangeEvent;
import com.project.yuliya.canyouescape.R;
import com.project.yuliya.canyouescape.activity.GameOverActivity;
import com.project.yuliya.canyouescape.enums.Action;
import com.project.yuliya.canyouescape.enums.ToolName;
import com.project.yuliya.canyouescape.helper.DBHelper;


public class MainRoomFragment extends MainFragment implements View.OnClickListener {

    ImageView mainDoor, leftDoor, rightDoor, MonaLiza, table, table2, hatch;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentName = "MainRoomFragment";
        Log.e(TAG,"onCreateMainRoom:");
        try
        {
            view = inflater.inflate(R.layout.main_room_fragment, container, false);
            context = view.getContext();
            dbHelper = new DBHelper(context);
            tool = (ToolFragment) getFragmentManager().findFragmentById(R.id.tool_fragment);
            idUser = tool.idRowUser;

            mainDoor = (ImageView) view.findViewById(R.id.mainDoor);
            leftDoor = (ImageView) view.findViewById(R.id.leftDoor);
            rightDoor = (ImageView) view.findViewById(R.id.rightDoor);
            MonaLiza = (ImageView) view.findViewById(R.id.MonaLiza);
            table = (ImageView) view.findViewById(R.id.table);
            table2 = (ImageView) view.findViewById(R.id.table2);
            hatch = (ImageView) view.findViewById(R.id.hatchSmall);
            messageBox = (TextView) view.findViewById(R.id.message);
            myLayout = (RelativeLayout)view.findViewById(R.id.myLayout);

            mainDoor.setOnClickListener(this);
            leftDoor.setOnClickListener(this);
            rightDoor.setOnClickListener(this);
            MonaLiza.setOnClickListener(this);
            table.setOnClickListener(this);
            hatch.setOnClickListener(this);
            messageBox.setOnClickListener(this);
            myLayout.setOnClickListener(this);

            if(dbHelper.getValueIntFromDB(idUser, DBHelper.KEY_IS_TABLE_MOVE)==1)
            {
                table.setVisibility(View.GONE);
                table2.setVisibility(View.VISIBLE);
                hatch.setEnabled(true);
            }


            return view;
        }
        catch (Exception e)
        {
            Log.e(TAG,"onCreateView:",e);
            return  null;
        }


    }

    //Обработчики нажатий********************************************************************

    @Override
    public void onClick(View view) {

        try
        {
            switch (view.getId())
            {
                case R.id.mainDoor:onClickMainDoor();break;
                case R.id.rightDoor:onClickRightDoor();break;
                case R.id.leftDoor:onClickLeftDoor();break;
                case R.id.table:onClickTable();break;
                case R.id.MonaLiza:onClickMonaLiza();break;
                case R.id.hatchSmall:onClickHatch();break;
                case R.id.myLayout:messageBox.setText("");break;
            }

        }
        catch (Exception e)
        {
            Log.e(TAG,"onClick:",e);
        }

    }

    private void onClickMainDoor() {
        try
        {
            if (((RadioButton)tool.RBG.getChildAt(5)).isChecked())
            {
                MediaPlayer.create(context, R.raw.doorclose).start();

                Intent intent = new Intent(context, GameOverActivity.class);
                intent.putExtra("idUser", idUser);
                startActivity(intent);

            } else {
                messageBox.setText(R.string.msg_close);
                MediaPlayer.create(context, R.raw.dvernayaruchka).start();
            }

        }
        catch (Exception e)
        {
            Log.e(TAG,"onClickMainDoor:",e);
        }

    }

    private void onClickRightDoor() {
        replaceFragment(new RightDoorFragment());
    }

    private void onClickLeftDoor(){
        replaceFragment(new LeftDoorFragment());
    }

    private void onClickTable() {
        try
        {
            Integer countTouch = dbHelper.getValueIntFromDB(idUser,DBHelper.KEY_COUNT_TOUCH_TABLE);
            Log.d(TAG, "countTouch = " + countTouch);

            if (countTouch == 0) {

                BusProvider.getInstance().post(new ToolChangeEvent(ToolName.KeyForRightDoor, 0, Action.Found));

                MediaPlayer.create(context, R.raw.yaschiktumbochki).start();
                MediaPlayer.create(context, R.raw.miscmetal).start();
                messageBox.setText(R.string.msg_key1);
                dbHelper.saveValueInDB(idUser,dbHelper.KEY_RIGHT_DOOR_KEY, 1);

                countTouch++;
                dbHelper.saveValueInDB(idUser,dbHelper.KEY_COUNT_TOUCH_TABLE, countTouch);

                return;
            }


            if (countTouch >= 3) {


                TranslateAnimation anim = new TranslateAnimation(0, -90, 0, 0);
                anim.setDuration(1000);
                anim.setFillAfter(true);
                table.startAnimation(anim);

                MediaPlayer.create(context, R.raw.drawer).start();
                messageBox.setText(R.string.msg_it_can_be_moved);

                table.setEnabled(false);
                hatch.setEnabled(true);

                countTouch++;
                dbHelper.saveValueInDB(idUser,dbHelper.KEY_COUNT_TOUCH_TABLE, countTouch);
                dbHelper.saveValueInDB(idUser,dbHelper.KEY_IS_TABLE_MOVE, 1);
                return;
            }

            countTouch++;
            dbHelper.saveValueInDB(idUser,dbHelper.KEY_COUNT_TOUCH_TABLE, countTouch);

        }
        catch (Exception e)
        {
            Log.e(TAG,"onClickTable:",e);
        }

    }

    private void onClickMonaLiza() {

        try
        {
            anim = AnimationUtils.loadAnimation(context, R.anim.myrotate);
            MonaLiza.startAnimation(anim);
            MediaPlayer.create(context, R.raw.squeak_wood).start();
            messageBox.setText(R.string.msg_inscription);

        }
        catch (Exception e)
        {
            Log.e(TAG,"onClickMonaLiza:",e);
        }

    }

    private void onClickHatch() {

        if(!table.isEnabled())
            replaceFragment(new HatchFragment());

    }



}

