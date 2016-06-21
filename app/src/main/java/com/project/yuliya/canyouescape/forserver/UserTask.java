package com.project.yuliya.canyouescape.forserver;


import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


public class UserTask extends AsyncTask<String,Void,User> {

    public static final String TAG = "MyLog";

    @Override
    protected User doInBackground(String... params) {
        try {
            RestTemplate template = new RestTemplate();
            template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            User user = new User(params[0]);
            user.setId(4);

            return template.postForObject(Constants.URL.SAVE_NEW_USER,user,User.class);

        } catch (RestClientException e) {
            Log.d(TAG,"on doInBackground : ",e);
            return null;
        }
    }



    @Override
    protected void onPostExecute(User user) {
        try {

                Log.d(TAG,"id : "+ user.getId()+" login : "+user.getLogin()+" password : "
                        +" time : "+user.getTime());

        } catch (Exception e) {
            Log.d(TAG,"on onPostExecute : ",e);
        }

    }


}
