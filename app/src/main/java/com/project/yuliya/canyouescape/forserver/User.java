package com.project.yuliya.canyouescape.forserver;


public class User {

    private long id;
    private String login;
    private long time;
    private int idUser;

    public User()
    {

    }
    public User(String login)
    {
        this.login = login;
        this.time = 0;
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


    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
