package com.project.yuliya.canyouescape.fragment;

import android.content.res.Resources;
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

import com.project.yuliya.canyouescape.EventBus.BusProvider;
import com.project.yuliya.canyouescape.EventBus.ToolChangeEvent;
import com.project.yuliya.canyouescape.R;
import com.project.yuliya.canyouescape.enums.Action;
import com.project.yuliya.canyouescape.enums.ToolName;
import com.project.yuliya.canyouescape.helper.DBHelper;

public class HatchFragment extends MainFragment implements View.OnClickListener{

    ImageView hatchLock, hatchBackground,cables,key;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentName = "HatchFragment";

        try {

            view = inflater.inflate(R.layout.hatch_fragment, container, false);
            context = view.getContext();
            dbHelper = new DBHelper(context);
            tool = (ToolFragment) getFragmentManager().findFragmentById(R.id.tool_fragment);

            myLayout = (RelativeLayout) view.findViewById(R.id.hatchLayout);
            messageBox = (TextView)view.findViewById(R.id.message);
            hatchBackground = (ImageView) view.findViewById(R.id.hatchBackground);
            hatchLock = (ImageView) view.findViewById(R.id.hatchLock);
            cables = (ImageView) view.findViewById(R.id.cables);
            key = (ImageView) view.findViewById(R.id.keySafe);

            myLayout.setOnClickListener(this);
            hatchLock.setOnClickListener(this);
            cables.setOnClickListener(this);
            key.setOnClickListener(this);

            if (dbHelper.getValue(DBHelper.KEY_IS_HATCH_OPEN) == 1)
            {
                hatchBackground.setImageDrawable(getResources().getDrawable(R.drawable.hatch_open));

                if(dbHelper.getValue(DBHelper.KEY_IS_LIGHT) == 1) {
                    cables.setImageDrawable(getResources().getDrawable(R.drawable.cables));
                    cables.setEnabled(false);
                }
                else {
                    cables.setImageDrawable(getResources().getDrawable(R.drawable.cables1));
                    cables.setEnabled(true);

                }

                if (dbHelper.getValue(DBHelper.KEY_IS_HATCH_KEY_TAKE) == 1)
                        key.setVisibility(View.GONE);


            }

            else {
                hatchBackground.setImageDrawable(getResources().getDrawable(R.drawable.hatch_big));
                cables.setImageDrawable(getResources().getDrawable(R.drawable.cables1));
                cables.setEnabled(false);
                key.setEnabled(false);
            }



        }
        catch (Resources.NotFoundException e) {
            Log.e(TAG, "onCreateView", e);
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        try
        {
            switch (view.getId())
            {
                case R.id.hatchLayout:messageBox.setText("");break;
                case R.id.cables:onClickCables();break;
                case R.id.keySafe:onClickKey();break;
                case R.id.hatchLock:onClickHatch();break;

            }

        }
        catch (Exception e)
        {
            Log.e(TAG,"onClick:",e);
        }
    }


    public void onClickHatch()
    {
        try
        {
            if (((RadioButton)tool.RBG.getChildAt(3)).isChecked())
            {
                BusProvider.getInstance().post(new ToolChangeEvent(ToolName.KeyForHatch, 3, Action.Used));

                dbHelper.saveInDB(DBHelper.KEY_HATCH_KEY, 0);
                dbHelper.saveInDB(DBHelper.KEY_IS_HATCH_OPEN,1);

                hatchBackground.setImageDrawable(getResources().getDrawable(R.drawable.hatch_open));
                hatchLock.setEnabled(false);
                cables.setEnabled(true);
                key.setEnabled(true);
                messageBox.setText(R.string.msg_it_open);

            }

            else
                messageBox.setText(R.string.msg_hatch);

        }
        catch (Exception e)
        {
            Log.e(TAG, "onClickHatch:", e);
        }

    }

    public void onClickCables()
    {
        try
        {
            if (((RadioButton)tool.RBG.getChildAt(1)).isChecked())
            {
                BusProvider.getInstance().post(new ToolChangeEvent(ToolName.Cable, 1, Action.Used));

                dbHelper.saveInDB(DBHelper.KEY_CABLE, 0);
                dbHelper.saveInDB(DBHelper.KEY_IS_LIGHT, 1);

                cables.setImageDrawable(getResources().getDrawable(R.drawable.cables));
                cables.setEnabled(false);

                MediaPlayer.create(context, R.raw.buttonenter).start();
                messageBox.setText(R.string.msg_fixed);

            }
            else
                messageBox.setText(R.string.msg_hatch2);

        }
        catch (Exception e)
        {
            Log.e(TAG, "onClickCables:", e);
        }

    }

    public void onClickKey()
    {
        try
        {
            BusProvider.getInstance().post(new ToolChangeEvent(ToolName.KeyForSafe, 4, Action.Found));

            dbHelper.saveInDB(DBHelper.KEY_SAFE_KEY, 1);
            dbHelper.saveInDB(DBHelper.KEY_IS_LIGHT, 1);
            dbHelper.saveInDB(DBHelper.KEY_IS_HATCH_KEY_TAKE, 1);

            key.setVisibility(View.GONE);

            MediaPlayer.create(context, R.raw.miscmetal).start();
            messageBox.setText(R.string.msg_key3);

        }
        catch (Exception e)
        {
            Log.e(TAG, "onClickKey:", e);
        }

    }


}
