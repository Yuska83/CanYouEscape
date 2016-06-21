package com.project.yuliya.canyouescape.fragment;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.yuliya.canyouescape.EventBus.BusProvider;
import com.project.yuliya.canyouescape.EventBus.ToolChangeEvent;
import com.project.yuliya.canyouescape.R;
import com.project.yuliya.canyouescape.enums.Action;
import com.project.yuliya.canyouescape.enums.ToolName;
import com.project.yuliya.canyouescape.helper.DBHelper;

public class RightDoorFragment extends MainFragment {

    ImageView door, tumbler;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentName = "RightDoorFragment";

        try
        {
            view = inflater.inflate(R.layout.right_door_fragment, container, false);
            context = view.getContext();
            dbHelper = new DBHelper(context);
            tool = (ToolFragment) getFragmentManager().findFragmentById(R.id.tool_fragment);

            myLayout = (RelativeLayout) view.findViewById(R.id.myLayout);
            messageBox = (TextView) view.findViewById(R.id.message);
            door = (ImageView) view.findViewById(R.id.doorR);
            tumbler = (ImageView) view.findViewById(R.id.tumbler);

            if(dbHelper.getValue(DBHelper.KEY_ON_LIGHT)== 1)
                myLayout.setBackground(getResources().getDrawable(R.drawable.door_right2));
            else
                myLayout.setBackground(getResources().getDrawable(R.drawable.door_right));


            myLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    messageBox.setText("");
                }
            });

            door.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (dbHelper.getValue(DBHelper.KEY_IS_LOCKED_RIGHT_DOOR) == 0) {
                            MediaPlayer.create(context, R.raw.doorclose).start();
                            replaceFragment(new RightRoomFragment());

                        } else if (((RadioButton) tool.RBG.getChildAt(0)).isChecked()) {
                            BusProvider.getInstance().post(new ToolChangeEvent(ToolName.KeyForRightDoor, 0, Action.Used));

                            dbHelper.saveInDB(DBHelper.KEY_RIGHT_DOOR_KEY, 0);
                            dbHelper.saveInDB(DBHelper.KEY_IS_LOCKED_RIGHT_DOOR, 0);

                            replaceFragment(new RightRoomFragment());
                        } else {

                            messageBox.setText(R.string.msg_close);
                            MediaPlayer.create(context, R.raw.dvernayaruchka).start();

                        }

                    } catch (Exception e) {
                        Log.e(TAG, "onClickRightDoor:", e);
                    }
                }
            });

            tumbler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {

                        MediaPlayer.create(context, R.raw.buttonenter).start();

                        if (dbHelper.getValue(DBHelper.KEY_ON_LIGHT) == 1) {
                            dbHelper.saveInDB(DBHelper.KEY_ON_LIGHT, 0);
                            myLayout.setBackground(getResources().getDrawable(R.drawable.door_right));
                            messageBox.setText(R.string.msg_tumbler2);

                        } else {
                            if (dbHelper.getValue(DBHelper.KEY_IS_LIGHT) == 1) {
                                dbHelper.saveInDB(DBHelper.KEY_ON_LIGHT, 1);
                                myLayout.setBackground(getResources().getDrawable(R.drawable.door_right2));
                                messageBox.setText(R.string.msg_tumbler1);
                            } else {
                                messageBox.setText(R.string.msg_tumbler);
                            }
                        }

                    } catch (Exception e) {
                        Log.e(TAG, "onClickTumbler:", e);
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "onCreateView:", e);
        }

        return view;
    }


}
