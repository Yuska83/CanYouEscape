package com.project.yuliya.roomescape.asyncTask;


import android.os.AsyncTask;
import android.util.Log;

import com.project.yuliya.roomescape.classes.User;
import com.project.yuliya.roomescape.constans.URL;
import com.project.yuliya.roomescape.constans.dbKeys;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class saveUserTimeTask extends AsyncTask<User,Void,Integer > {


    @Override
    protected Integer doInBackground(User... user) {

        try {
            ClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            ((SimpleClientHttpRequestFactory) requestFactory).setConnectTimeout(3000);

            RestTemplate template = new RestTemplate(requestFactory);

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
