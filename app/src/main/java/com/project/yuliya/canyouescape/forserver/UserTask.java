package com.project.yuliya.canyouescape.forserver;


import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


public class UserTask extends AsyncTask<Void,Void,User> {

    public static final String TAG = "MyLog";

    @Override
    protected User doInBackground(Void... params) {
        try {
            RestTemplate template = new RestTemplate();
            template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            return template.getForObject(Constants.URL.GET_USER_FOR_NAME,User.class);

        } catch (RestClientException e) {
            Log.d(TAG,"on doInBackground : ",e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(User user) {
        try {

                Log.d(TAG,"id : "+ user.getId()+" login : "+user.getLogin()+" password : "
                        +user.getPassword()+" time : "+user.getTime());

        } catch (Exception e) {
            Log.d(TAG,"on onPostExecute : ",e);
        }

    }
}
