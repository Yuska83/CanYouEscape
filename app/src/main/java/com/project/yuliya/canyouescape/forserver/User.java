package com.project.yuliya.canyouescape.forserver;

import java.sql.Time;


public class User {

    private long id;
    private String login;
    private Time time;

    public User()
    {

    }
    public User(String login)
    {
        this.login = login;
        this.time = null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
