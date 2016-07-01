package com.project.yuliya.canyouescape.forserver;


import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class saveUserTimeTask extends AsyncTask<User,Void,User > {


    @Override
    protected User doInBackground(User... user) {

        try {
            RestTemplate template = new RestTemplate();
            template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            return template.postForObject(Constants.URL.SAVE_USER_TIME,user[0],User.class);

        } catch (RestClientException e) {
            Log.d(Constants.TAG,"on doInBackground : ",e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(User user) {
        try {

            Log.d(Constants.TAG,"id : "+ user.getId()+" login : "+user.getLogin() +" time : "+user.getTime());

        } catch (Exception e) {
            Log.d(Constants.TAG,"on onPostExecute : ",e);
        }

    }
}
