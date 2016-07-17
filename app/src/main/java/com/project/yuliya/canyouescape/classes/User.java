package com.project.yuliya.canyouescape.classes;


public class User {


    private long globalId;
    private int localId;
    private String name;
    private long time;


    public User()
    {

    }
    public User(String name)
    {
        this.setName(name);
    }

    public User(long globalId,String name,long time)
    {
        this.globalId = globalId;
        this.setName(name);
        this.setTime(time);
    }

    public long getGlobalId() {
        return globalId;
    }

    public void setGlobalId(long globalId) {
        this.globalId = globalId;
    }

    public int getLocalId() {
        return localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
