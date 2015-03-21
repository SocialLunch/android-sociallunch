package com.example.sociallunch.sociallunch.authentication;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;

/**
 * Created by kelei on 3/22/15.
 */

public class FacebookClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = FacebookApiV2.class;
    public static final String REST_URL = "https://graph.facebook.com/v2.2/";
    public static final String REST_CONSUMER_KEY = "1423528877943479";
    public static final String REST_CONSUMER_SECRET = "cbc5e0e3dd9760c18519848d842322ee";
    public static final String REST_CALLBACK_URL = "https://sociallunch.com/";

    public FacebookClient(Context context) {
        super(context, REST_API_CLASS, REST_URL,
            REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }

    public void getUserInfo(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("me");
        RequestParams params = new RequestParams();
        getClient().get(apiUrl, params, handler);
    }
}
