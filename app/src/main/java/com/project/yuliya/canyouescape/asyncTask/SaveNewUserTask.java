package com.project.yuliya.canyouescape.asyncTask;


import android.os.AsyncTask;
import android.util.Log;

import com.project.yuliya.canyouescape.classes.User;
import com.project.yuliya.canyouescape.constans.URL;
import com.project.yuliya.canyouescape.constans.dbKeys;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


public class SaveNewUserTask extends AsyncTask<String,Void,User> {

    public static final String TAG = "MyLog";

    @Override
    protected User doInBackground(String... params) {
        try {
            RestTemplate template = new RestTemplate();
            template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            User user = new User(params[0]);
            return template.postForObject(URL.SAVE_NEW_USER,user,User.class);

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
