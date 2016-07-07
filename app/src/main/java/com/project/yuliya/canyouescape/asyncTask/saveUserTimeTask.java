package com.project.yuliya.canyouescape.asyncTask;


import android.os.AsyncTask;
import android.util.Log;

import com.project.yuliya.canyouescape.classes.User;
import com.project.yuliya.canyouescape.constans.URL;
import com.project.yuliya.canyouescape.constans.dbKeys;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class saveUserTimeTask extends AsyncTask<User,Void,Integer > {


    @Override
    protected Integer doInBackground(User... user) {

        try {
            RestTemplate template = new RestTemplate();
            template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            return template.postForObject(URL.SAVE_USER_TIME,user[0],Integer.class);

        } catch (RestClientException e) {
            Log.d(dbKeys.TAG,"on doInBackground : ",e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(Integer id) {
        try {

            Log.d(dbKeys.TAG,"id : "+ String.valueOf(id));

        } catch (Exception e) {
            Log.d(dbKeys.TAG,"on onPostExecute : ",e);
        }

    }
}
