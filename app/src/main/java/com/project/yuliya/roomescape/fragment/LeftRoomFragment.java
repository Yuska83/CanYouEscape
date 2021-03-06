package com.project.yuliya.roomescape.fragment;


import android.animation.ObjectAnimator;
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

import com.project.yuliya.roomescape.eventBus.BusProvider;
import com.project.yuliya.roomescape.eventBus.ToolChangeEvent;
import com.project.yuliya.roomescape.R;
import com.project.yuliya.roomescape.constans.Action;
import com.project.yuliya.roomescape.constans.ToolName;
import com.project.yuliya.roomescape.constans.dbKeys;
import com.project.yuliya.roomescape.helper.DBHelper;


public class LeftRoomFragment extends MainFragment implements View.OnClickListener {

    ObjectAnimator anim;
    ImageView door, tree, cable, hammer, mirror, key;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentName = "LeftRoomFragment";

        Log.e(dbKeys.TAG,"onCreateLeftRoom:");

        try
        {
            view = inflater.inflate(R.layout.left_room_fragment, container, false);
            context = view.getContext();
            DBHelper = new DBHelper(context);
            tool = (ToolFragment) getFragmentManager().findFragmentById(R.id.tool_fragment);
            userIdLocal = tool.idRowUser;

            myLayout = (RelativeLayout)view.findViewById(R.id.LRLayout);
            messageBox = (TextView)view.findViewById(R.id.message);
            door =(ImageView) view.findViewById(R.id.door1);
            tree =(ImageView) view.findViewById(R.id.tree);
            cable =(ImageView) view.findViewById(R.id.cable);
            hammer =(ImageView) view.findViewById(R.id.hammer);
            mirror=(ImageView) view.findViewById(R.id.mirror);
            key = (ImageView) view.findViewById(R.id.key);

            myLayout.setOnClickListener(this);
            door.setOnClickListener(this);
            tree.setOnClickListener(this);
            cable.setOnClickListener(this);
            hammer.setOnClickListener(this);
            mirror.setOnClickListener(this);
            key.setOnClickListener(this);

            if(DBHelper.getValueIntFromDB(userIdLocal, dbKeys.KEY_IS_MIRROR)==0)
            {
                mirror.setImageDrawable(getResources().getDrawable(R.drawable.mirror2));
                mirror.setEnabled(false);
                if(DBHelper.getValueIntFromDB(userIdLocal, dbKeys.KEY_IS_KEY_HATCH)==1)
                    key.setVisibility(View.GONE);
                else
                    key.setVisibility(View.VISIBLE);
            }
            else
                mirror.setImageDrawable(getResources().getDrawable(R.drawable.mirror));

        }
        catch (Exception e)
        {
            Log.e(dbKeys.TAG,"onClick:",e);
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        try
        {
            switch (view.getId())
            {
                case R.id.LRLayout:messageBox.setText("");break;
                case R.id.door1:onClickDoor();break;
                case R.id.tree:onClickTree();break;
                case R.id.cable:onClickCable();break;
                case R.id.hammer:onClickHammer();break;
                case R.id.key:onClickKey();break;
                case R.id.mirror:onClickMirror();break;
            }

        }
        catch (Exception e)
        {
            Log.e(dbKeys.TAG,"onClick:",e);
        }
    }


    private void onClickDoor() {
        try
        {
            MediaPlayer.create(context, R.raw.doorclose).start();
            getActivity().getSupportFragmentManager().popBackStack();
        }
        catch (Exception e)
        {
            Log.e(dbKeys.TAG,"onClickDoor:",e);
        }
    }

    private void onClickTree() {
        try
        {
            MediaPlayer.create(context, R.raw.shurshanie).start();

            int countTouch = DBHelper.getValueIntFromDB(userIdLocal, dbKeys.KEY_COUNT_TOUCH_TREE);
            countTouch++;
            DBHelper.saveValueInDB(userIdLocal, dbKeys.KEY_COUNT_TOUCH_TREE, countTouch);

            if(countTouch>3) return;

            if(countTouch == 1)
            {
                cable.setVisibility(View.VISIBLE);
                cable.setEnabled(true);

                anim = ObjectAnimator.ofFloat(cable, "y", cable.getTop(), cable.getTop()+380);
                anim.setDuration(1000);
                anim.start();

            }
            else

            if(countTouch ==3)
            {
                hammer.setVisibility(View.VISIBLE);
                hammer.setEnabled(true);

                anim = ObjectAnimator.ofFloat(hammer, "y", hammer.getTop(),hammer.getTop()+370);
                anim.setDuration(1000);
                anim.start();

            }

        }
        catch (Exception e)
        {
            Log.e(dbKeys.TAG,"onClickTree:",e);
        }
    }

    private void onClickCable() {

        try
        {
            cable.setVisibility(View.GONE);
            BusProvider.getInstance().post(new ToolChangeEvent(ToolName.Cable, 1, Action.Found));

            DBHelper.saveValueInDB(userIdLocal, dbKeys.KEY_CABLE, 1);

            MediaPlayer.create(context, R.raw.miscmetal).start();
            messageBox.setText(R.string.msg_cable);

        }
        catch (Exception e)
        {
            Log.e(dbKeys.TAG,"onClickCable:",e);
        }
    }

    private void onClickHammer() {

        try
        {
            hammer.setVisibility(View.GONE);
            BusProvider.getInstance().post(new ToolChangeEvent(ToolName.Hammer, 2, Action.Found));

            DBHelper.saveValueInDB(userIdLocal, dbKeys.KEY_HAMMER, 1);

            MediaPlayer.create(context, R.raw.miscmetal).start();
            messageBox.setText(R.string.msg_hammer);

        }
        catch (Exception e)
        {
            Log.e(dbKeys.TAG,"onClickHammer:",e);
        }
    }

    private void onClickKey() {
        try
        {
            BusProvider.getInstance().post(new ToolChangeEvent(ToolName.KeyForHatch, 3, Action.Found));

            DBHelper.saveValueInDB(userIdLocal, dbKeys.KEY_HATCH_KEY, 1);
            DBHelper.saveValueInDB(userIdLocal, dbKeys.KEY_IS_KEY_HATCH, 1);

            key.setVisibility(View.INVISIBLE);
            mirror.setEnabled(false);

            MediaPlayer.create(context, R.raw.miscmetal).start();
            messageBox.setText(R.string.msg_key2);
        }
        catch (Exception e)
        {
            Log.e(dbKeys.TAG,"onClickKey:",e);
        }
    }

    private void onClickMirror() {
        try
        {

            if (((RadioButton)tool.RBG.getChildAt(2)).isChecked())
            {
                BusProvider.getInstance().post(new ToolChangeEvent(ToolName.Hammer, 2 , Action.Used));
                DBHelper.saveValueInDB(userIdLocal, dbKeys.KEY_HAMMER, 0);
                DBHelper.saveValueInDB(userIdLocal, dbKeys.KEY_IS_MIRROR, 0);

                mirror.setImageDrawable(getResources().getDrawable(R.drawable.mirror2));
                mirror.setEnabled(false);
                messageBox.setText(R.string.msg_tink);
                MediaPlayer.create(context, R.raw.zerkalo).start();

                key.setVisibility(View.VISIBLE);
                key.setEnabled(true);

            }
            else
                messageBox.setText(R.string.msg_mirror);

        }
        catch (Exception e)
        {
            Log.e(dbKeys.TAG,"onClickMirror:",e);
        }
    }


}