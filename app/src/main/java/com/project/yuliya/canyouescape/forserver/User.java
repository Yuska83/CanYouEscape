package com.project.yuliya.canyouescape.forserver;

import java.sql.Time;

/**
 * Created by Yuri on 15.06.2016.
 */
public class User {

    private long id;
    private String login;
    private String password;
    private Time time;

    public User()
    {

    }
    public User(String login, String password)
    {
        this.login = login;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
