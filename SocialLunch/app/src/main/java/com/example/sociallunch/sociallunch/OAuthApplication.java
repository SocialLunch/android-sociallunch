package com.example.sociallunch.sociallunch;

import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.example.sociallunch.sociallunch.authentication.FacebookClient;
import com.example.sociallunch.sociallunch.authentication.LinkedinClient;
import com.example.sociallunch.sociallunch.authentication.TwitterClient;

/**
 * Created by kelei on 3/22/15.
 */

public class OAuthApplication extends com.activeandroid.app.Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        OAuthApplication.context = this;
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