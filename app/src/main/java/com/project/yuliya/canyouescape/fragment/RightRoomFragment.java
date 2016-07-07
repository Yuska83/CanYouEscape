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

public class RightRoomFragment extends MainFragment implements View.OnClickListener {

    ImageView door, picture, picture2, safe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentName = "RightRoomFragment";
        Log.e(dbKeys.TAG,"onCreateRightRoomFragment:");
        try {
            view = inflater.inflate(R.layout.right_room_fragment, container, false);
            context = view.getContext();
            DBHelper = new DBHelper(context);
            tool = (ToolFragment) getFragmentManager().findFragmentById(R.id.tool_fragment);
            userIdLocal = tool.idRowUser;

            myLayout = (RelativeLayout) view.findViewById(R.id.room3);
            messageBox = (TextView) view.findViewById(R.id.message);
            door = (ImageView) view.findViewById(R.id.door2);
            picture = (ImageView) view.findViewById(R.id.picture);
            picture2 = (ImageView) view.findViewById(R.id.picture2);
            safe = (ImageView) view.findViewById(R.id.safeDoor);

            myLayout.setOnClickListener(this);
            door.setOnClickListener(this);
            picture.setOnClickListener(this);
            picture2.setOnClickListener(this);
            safe.setOnClickListener(this);

            if (DBHelper.getValueIntFromDB(userIdLocal, dbKeys.KEY_ON_LIGHT) == 1) {
                myLayout.setBackground(this.getResources().getDrawable(R.drawable.room_right));

                if (DBHelper.getValueIntFromDB(userIdLocal, dbKeys.KEY_IS_PICTURE) == 0) {
                    picture.setVisibility(View.GONE);
                    picture2.setVisibility(View.VISIBLE);
                }
                if (DBHelper.getValueIntFromDB(userIdLocal, dbKeys.KEY_SAFE_OPEN) == 1
                        && DBHelper.getValueIntFromDB(userIdLocal, dbKeys.KEY_MAIN_KEY) == 1)
                            safe.setEnabled(false);


            } else {
                myLayout.setBackground(this.getResources().getDrawable(R.drawable.roomblack));
                myLayout.setEnabled(false);
                picture.setVisibility(View.INVISIBLE);
                safe.setVisibility(View.INVISIBLE);
                picture2.setEnabled(false);
                messageBox.setText(R.string.msg_dark);
            }

        } catch (Exception e) {
            Log.e(dbKeys.TAG, "onCreateView:", e);
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.room3:
                    messageBox.setText("");
                    break;
                case R.id.door2:
                    onClickDoor();
                    break;
                case R.id.safeDoor:
                    onClickSafe();
                    break;
                case R.id.picture:
                    onClickPicture();
                    break;
            }

        } catch (Exception e) {
            Log.e(dbKeys.TAG, "onClick:", e);
        }
    }


    public void onClickDoor() {
        try {
            MediaPlayer.create(context, R.raw.doorclose).start();
            getActivity().getSupportFragmentManager().popBackStack();
        } catch (Exception e) {
            Log.e(dbKeys.TAG, "onClickDoor:", e);
        }
    }

    public void onClickPicture() {
        try {
            MediaPlayer.create(context, R.raw.plate).start();
            picture.setVisibility(View.GONE);
            picture2.setVisibility(View.VISIBLE);
            DBHelper.saveValueInDB(userIdLocal, dbKeys.KEY_IS_PICTURE, 0);

        } catch (Exception e) {
            Log.e(dbKeys.TAG, "onClickPicture:", e);
        }

    }

    public void onClickSafe() {
        try {

            if (((RadioButton) tool.RBG.getChildAt(4)).isChecked()) {
                BusProvider.getInstance().post(new ToolChangeEvent(ToolName.KeyForSafe, 4, Action.Used));

                DBHelper.saveValueInDB(userIdLocal, dbKeys.KEY_SAFE_KEY, 0);
                DBHelper.saveValueInDB(userIdLocal, dbKeys.KEY_SAFE_OPEN, 1);

                MediaPlayer.create(context, R.raw.trashremove).start();

                replaceFragment(new SafetyBoxFragment());

                if (DBHelper.getValueIntFromDB(userIdLocal, dbKeys.KEY_MAIN_KEY) == 1) safe.setEnabled(false);

            } else messageBox.setText(R.string.msg_safe);

        } catch (Exception e) {
            Log.e(dbKeys.TAG, "error", e);
        }

    }


}
