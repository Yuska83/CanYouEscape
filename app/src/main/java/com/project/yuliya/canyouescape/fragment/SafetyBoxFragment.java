package com.project.yuliya.canyouescape.fragment;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.project.yuliya.canyouescape.EventBus.BusProvider;
import com.project.yuliya.canyouescape.EventBus.ToolChangeEvent;
import com.project.yuliya.canyouescape.R;
import com.project.yuliya.canyouescape.enums.Action;
import com.project.yuliya.canyouescape.enums.ToolName;
import com.project.yuliya.canyouescape.helper.DBHelper;


public class SafetyBoxFragment extends MainFragment {

    FrameLayout picture;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentName = "SafetyBoxFragment";

        try
        {
            view = inflater.inflate(R.layout.safety_box_fragment, container, false);
            context = view.getContext();
            dbHelper = new DBHelper(context);
            tool = (ToolFragment) getFragmentManager().findFragmentById(R.id.tool_fragment);
            idUser = tool.idRowUser;

            messageBox = (TextView) view.findViewById(R.id.message);
            picture =(FrameLayout) view.findViewById(R.id.pic);

            picture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    BusProvider.getInstance().post(new ToolChangeEvent(ToolName.KeyMain, 5, Action.Found));
                    dbHelper.saveValueInDB(idUser, DBHelper.KEY_MAIN_KEY, 1);

                    MediaPlayer.create(context, R.raw.miscmetal).start();
                    messageBox.setText(R.string.msg_key4);

                    getActivity().getSupportFragmentManager().popBackStack();
                }
            });

        }
        catch (Exception e)
        {
            Log.e(TAG,"onCreateView:",e);
        }

        return view;
    }


}
