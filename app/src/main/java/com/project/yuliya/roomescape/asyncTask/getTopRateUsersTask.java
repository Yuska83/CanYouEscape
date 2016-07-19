package com.project.yuliya.roomescape.asyncTask;

import android.os.AsyncTask;
import android.util.Log;

import com.project.yuliya.roomescape.classes.User;
import com.project.yuliya.roomescape.constans.URL;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;


public class getTopRateUsersTask extends AsyncTask<Void,Void,ArrayList<User>> {

    public static final String TAG = "MyLog";

    @Override
    protected ArrayList<User> doInBackground(Void... params) {
        try {

            ClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            ((SimpleClientHttpRequestFactory) requestFactory).setConnectTimeout(3000);

            RestTemplate template = new RestTemplate(requestFactory);
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
