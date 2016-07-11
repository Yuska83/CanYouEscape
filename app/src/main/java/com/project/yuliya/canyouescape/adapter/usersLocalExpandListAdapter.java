package com.project.yuliya.canyouescape.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.project.yuliya.canyouescape.R;
import com.project.yuliya.canyouescape.classes.User;

import java.util.HashMap;
import java.util.List;

public class usersLocalExpandListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expListTitle;
    private HashMap<String, List<User>> expListDetail;

    public usersLocalExpandListAdapter(Context context, List<String> expListTitle,
                                       HashMap<String, List<User>> expListDetail) {
        this.context = context;
        this.expListTitle = expListTitle;
        this.expListDetail = expListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expListPosition) {
        return expListDetail.get(
                expListTitle.get(listPosition)
        ).get(expListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        // получаем дочерний элемент
        User expListText = (User) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.local_users, null);
        }
        TextView expListTextView = (TextView) convertView.findViewById(R.id.expandedListItem);
        expListTextView.setText(expListText.getLogin());
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return expListDetail.get(
                expListTitle.get(listPosition)
        ).size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return expListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return expListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        // получаем родительский элемент
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.current_user, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
