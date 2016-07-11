package com.project.yuliya.canyouescape.asyncTask;

import android.os.AsyncTask;
import android.util.Log;

import com.project.yuliya.canyouescape.classes.User;
import com.project.yuliya.canyouescape.constans.URL;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;


public class getTopRateUsersTask extends AsyncTask<Void,Void,ArrayList<User>> {

    public static final String TAG = "MyLog";

    @Override
    protected ArrayList<User> doInBackground(Void... params) {
        try {
            RestTemplate template = new RestTemplate();
            template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            ResponseEntity<ArrayList<User>> usersResponse = template.exchange(URL.GET_TOP_RATE_USERS,
                    HttpMethod.GET, null, new ParameterizedTypeReference<ArrayList<User>>() {
                    });

            ArrayList<User> topRateUsers = usersResponse.getBody();

            return topRateUsers;


        } catch (RestClientException e) {
            Log.d(TAG,"on doInBackground : ",e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<User> users) {
        super.onPostExecute(users);



    }
}