package com.project.yuliya.canyouescape.fragment;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.yuliya.canyouescape.eventBus.BusProvider;
import com.project.yuliya.canyouescape.eventBus.ToolChangeEvent;
import com.project.yuliya.canyouescape.R;
import com.project.yuliya.canyouescape.constans.Action;
import com.project.yuliya.canyouescape.constans.ToolName;
import com.project.yuliya.canyouescape.constans.dbKeys;
import com.project.yuliya.canyouescape.helper.DBHelper;
import com.squareup.otto.Bus;

public class RightDoorFragment extends MainFragment {

    ImageView door, tumbler;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentName = "RightDoorFragment";
        Log.e(dbKeys.TAG,"onCreateRightDoorFragment:");
        try
        {
            view = inflater.inflate(R.layout.right_door_fragment, container, false);
            context = view.getContext();
            DBHelper = new DBHelper(context);
            tool = (ToolFragment) getFragmentManager().findFragmentById(R.id.tool_fragment);
            userIdLocal = tool.idRowUser;

            myLayout = (RelativeLayout) view.findViewById(R.id.myLayout);
            messageBox = (TextView) view.findViewById(R.id.message);
            door = (ImageView) view.findViewById(R.id.doorR);
            tumbler = (ImageView) view.findViewById(R.id.tumbler);

            if(DBHelper.getValueIntFromDB(userIdLocal, dbKeys.KEY_ON_LIGHT)== 1)
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
                        if (DBHelper.getValueIntFromDB(userIdLocal, dbKeys.KEY_IS_LOCKED_RIGHT_DOOR) == 0) {
                            MediaPlayer.create(context, R.raw.doorclose).start();
                            replaceFragment(new RightRoomFragment());

                        } else if (((RadioButton) tool.RBG.getChildAt(0)).isChecked()) {

                            Bus bus = BusProvider.getInstance();
                            bus.post(new ToolChangeEvent(ToolName.KeyForRightDoor, 0, Action.Used));

                            DBHelper.saveValueInDB(userIdLocal, dbKeys.KEY_RIGHT_DOOR_KEY, 0);
                            DBHelper.saveValueInDB(userIdLocal, dbKeys.KEY_IS_LOCKED_RIGHT_DOOR, 0);

                            replaceFragment(new RightRoomFragment());
                        } else {

                            messageBox.setText(R.string.msg_close);
                            MediaPlayer.create(context, R.raw.dvernayaruchka).start();

                        }

                    } catch (Exception e) {
                        Log.e(dbKeys.TAG, "onClickRightDoor:", e);
                    }
                }
            });

            tumbler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {

                        MediaPlayer.create(context, R.raw.buttonenter).start();

                        if (DBHelper.getValueIntFromDB(userIdLocal, dbKeys.KEY_ON_LIGHT) == 1) {
                            DBHelper.saveValueInDB(userIdLocal, dbKeys.KEY_ON_LIGHT, 0);
                            myLayout.setBackground(getResources().getDrawable(R.drawable.door_right));
                            messageBox.setText(R.string.msg_tumbler2);

                        } else {
                            if (DBHelper.getValueIntFromDB(userIdLocal, dbKeys.KEY_IS_LIGHT) == 1) {
                                DBHelper.saveValueInDB(userIdLocal, dbKeys.KEY_ON_LIGHT, 1);
                                myLayout.setBackground(getResources().getDrawable(R.drawable.door_right2));
                                messageBox.setText(R.string.msg_tumbler1);
                            } else {
                                messageBox.setText(R.string.msg_tumbler);
                            }
                        }

                    } catch (Exception e) {
                        Log.e(dbKeys.TAG, "onClickTumbler:", e);
                    }
                }
            });
        } catch (Exception e) {
            Log.e(dbKeys.TAG, "onCreateView:", e);
        }

        return view;
    }


}
