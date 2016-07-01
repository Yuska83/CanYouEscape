package com.project.yuliya.canyouescape.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.project.yuliya.canyouescape.R;
import com.project.yuliya.canyouescape.forserver.User;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class usersArrayAdapter extends ArrayAdapter<User>{

    Context context;
    ArrayList<User> users;

    public usersArrayAdapter(Context context, ArrayList<User> users) {
        super(context, R.layout.list_item_user,users);
        this.context = context;
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listItem = inflater.inflate(R.layout.list_item_user, parent, false);
        TextView textViewNum = (TextView) listItem.findViewById(R.id.numUser);
        TextView textViewName = (TextView) listItem.findViewById(R.id.nameUser);
        TextView textViewTime = (TextView) listItem.findViewById(R.id.timeUser);

        textViewNum.setText(String.valueOf(position+1));
        textViewName.setText(users.get(position).getLogin());
        textViewTime.setText(printTime(users.get(position).getTime()));

        return listItem;
    }

    public static String printTime(long time)
    {
        String strTime = "";
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(time);
        Date newTime =calendar.getTime ();

        SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
        strTime = dateFormat.format( newTime );
        return strTime;


    }
}



