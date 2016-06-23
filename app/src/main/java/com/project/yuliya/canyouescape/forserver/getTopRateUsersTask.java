package com.project.yuliya.canyouescape.forserver;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Yuri on 23.06.2016.
 */
public class getTopRateUsersTask extends AsyncTask<String,Void,List<User>> {
    @Override
    protected List<User> doInBackground(String... params) {
        try {
            RestTemplate template = new RestTemplate();
            template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            User user = new User(params[0]);
            template.postForObject(Constants.URL.SAVE_NEW_USER,user,User.class);
            template.po

        } catch (RestClientException e) {
            Log.d(TAG,"on doInBackground : ",e);
            return null;
        }
    }
}
