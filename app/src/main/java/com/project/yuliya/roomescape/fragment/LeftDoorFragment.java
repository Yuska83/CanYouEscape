package com.project.yuliya.roomescape.fragment;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.yuliya.roomescape.R;
import com.project.yuliya.roomescape.constans.dbKeys;
import com.project.yuliya.roomescape.helper.DBHelper;

public class LeftDoorFragment extends MainFragment implements View.OnClickListener {

    ImageView door,codeLock;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentName = "LeftDoorFragment";
        Log.e(dbKeys.TAG,"onCreateLeftDoor:");
        try
        {
            view = inflater.inflate(R.layout.left_door_fragment, container, false);
            context = view.getContext();
            DBHelper = new DBHelper(context);
            tool=(ToolFragment)getFragmentManager().findFragmentById(R.id.tool_fragment);
            userIdLocal = tool.idRowUser;

            myLayout = (RelativeLayout)view.findViewById(R.id.LDLayout);
            messageBox = (TextView)view.findViewById(R.id.message);
            door =(ImageView) view.findViewById(R.id.doorL);
            codeLock =(ImageView) view.findViewById(R.id.codeLock);

            myLayout.setOnClickListener(this);
            door.setOnClickListener(this);
            codeLock.setOnClickListener(this);

            if(DBHelper.getValueIntFromDB(userIdLocal, dbKeys.KEY_IS_LOCKED_LEFT_DOOR) == 0)
                codeLock.setEnabled(false);

        }
        catch (Exception e)
        {
            Log.e(dbKeys.TAG,"onReplaceFragment:",e);
        }

        return view;
    }


    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.LDLayout:messageBox.setText("");break;
            case R.id.doorL:onClickDoor();break;
            case R.id.codeLock:onClickCodeLock();break;

        }
    }

    private void onClickDoor() {
        try
        {
            if (DBHelper.getValueIntFromDB(userIdLocal, dbKeys.KEY_IS_LOCKED_LEFT_DOOR)==0) {

                MediaPlayer.create(context, R.raw.doorclose).start();
                replaceFragment(new LeftRoomFragment());

            } else {
                messageBox.setText(R.string.msg_close);
                MediaPlayer.create(context, R.raw.dvernayaruchka).start();
            }

        }
        catch (Exception e)
        {
            Log.e(dbKeys.TAG,"onClickLeftDoor:",e);
        }

    }

    private void onClickCodeLock() {
        try
        {
            if(DBHelper.getValueIntFromDB(userIdLocal, dbKeys.KEY_IS_LOCKED_LEFT_DOOR) ==0)
            {
                codeLock.setEnabled(false);
                return;
            }
            replaceFragment(new CombinationLockFragment());

        }
        catch (Exception e)
        {
            Log.e(dbKeys.TAG,"onClickCodeLock:",e);
        }

    }




}

