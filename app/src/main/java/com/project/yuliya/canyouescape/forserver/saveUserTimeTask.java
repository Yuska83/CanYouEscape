package com.project.yuliya.canyouescape.forserver;


import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class saveUserTimeTask extends AsyncTask<User,Void,Integer > {


    @Override
    protected Integer doInBackground(User... user) {

        try {
            RestTemplate template = new RestTemplate();
            template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            return template.postForObject(Constants.URL.SAVE_USER_TIME,user[0],Integer.class);

        } catch (RestClientException e) {
            Log.d(Constants.TAG,"on doInBackground : ",e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(Integer id) {
        try {

            Log.d(Constants.TAG,"id : "+ String.valueOf(id));

        } catch (Exception e) {
            Log.d(Constants.TAG,"on onPostExecute : ",e);
        }

    }
}
