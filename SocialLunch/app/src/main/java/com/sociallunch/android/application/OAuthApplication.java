package com.sociallunch.android.application;

import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.firebase.client.Firebase;
import com.sociallunch.android.authentication.FacebookClient;
import com.sociallunch.android.authentication.LinkedinClient;
import com.sociallunch.android.authentication.TwitterClient;

/**
 * Created by kelei on 3/22/15.
 */

public class OAuthApplication extends com.activeandroid.app.Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        OAuthApplication.context = this;
        Firebase.setAndroidContext(this);
        ActiveAndroid.initialize(this);
    }

    public static FacebookClient getFacebookClient() {
        return (FacebookClient) FacebookClient
            .getInstance(FacebookClient.class, OAuthApplication.context);
    }

    public static LinkedinClient getLinkedinClient() {
        return (LinkedinClient) FacebookClient
            .getInstance(LinkedinClient.class, OAuthApplication.context);
    }

    public static TwitterClient getTwitterClient() {
        return (TwitterClient) TwitterClient
            .getInstance(TwitterClient.class, OAuthApplication.context);
    }
}