package com.project.yuliya.canyouescape.adapter;


import com.project.yuliya.canyouescape.forserver.User;

import java.util.ArrayList;

public class topRateUsers {

    private ArrayList <User> users = new ArrayList<User>();

    public topRateUsers (ArrayList<User> users)
    {
        this.users = users;
    }
    public ArrayList<User> getTopRateUsers() {
        return users;
    }

    public void setTopRateUsers(ArrayList<User> topRateUsers) {
        this.users = topRateUsers;
    }
}
