package com.project.yuliya.canyouescape.forserver;

public class Constants {

    public static final String TAG = "MyLog";

    public static class URL {
        private static final String HOST = "http://192.168.1.102:8082/";
        public static final String SAVE_NEW_USER = HOST + "/saveUser";
        public static final String SAVE_USER_TIME = HOST + "/saveUserTime";
        public static final String GET_TOP_RATE_USERS = HOST + "/getTopRateUsers";

    }
}
