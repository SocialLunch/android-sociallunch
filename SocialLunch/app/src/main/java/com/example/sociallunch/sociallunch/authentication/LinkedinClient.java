package com.example.sociallunch.sociallunch.authentication;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.LinkedInApi;

/**
 * Created by kelei on 3/22/15.
 */
public class LinkedinClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = LinkedInApi.class;
    public static final String REST_URL = "https://api.linkedin.com/v1";
    public static final String REST_CONSUMER_KEY = "78o5oypknv4ljc";
    public static final String REST_CONSUMER_SECRET = "hCqhyFo2SPmIYkju";
    public static final String REST_CALLBACK_URL = "oauth://simplelunch";

    public LinkedinClient(Context context) {
        super(context, REST_API_CLASS, REST_URL,
            REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }

    public void getProfile(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("people/~:(id,picture-url,first-name,last-name,headline)?format=json");

        getClient().get(apiUrl, null, handler);
    }
}
