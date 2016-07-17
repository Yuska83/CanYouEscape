package com.project.yuliya.canyouescape.asyncTask;


import android.os.AsyncTask;
import android.util.Log;

import com.project.yuliya.canyouescape.classes.User;
import com.project.yuliya.canyouescape.constans.URL;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


public class SaveNewUserTask extends AsyncTask<User,Void,Long> {

    public static final String TAG = "MyLog";

    @Override
    protected Long doInBackground(User... params) {
        try {

            ClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            ((SimpleClientHttpRequestFactory) requestFactory).setConnectTimeout(3000);

            RestTemplate template = new RestTemplate(requestFactory);
            template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            return template.postForObject(URL.SAVE_NEW_USER,params[0],Long.class);

        } catch (RestClientException e) {
            Log.d(TAG,"on doInBackground : ",e);
            return null;
        }
    }

}
