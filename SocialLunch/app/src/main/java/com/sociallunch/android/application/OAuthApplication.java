package com.sociallunch.android.application;

import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

/**
 * Created by kelei on 3/22/15.
 */

public class OAuthApplication extends com.activeandroid.app.Application {
    private static Context context;
    private Firebase _ref;
    private AuthData _authData;

    @Override
    public void onCreate() {
        super.onCreate();
        OAuthApplication.context = this;
        Firebase.setAndroidContext(this);
        ActiveAndroid.initialize(this);
        _ref = new Firebase("https://torrid-torch-5195.firebaseio.com");
    }

    public Firebase getFirebaseRef() {
        return _ref;
    }

    public AuthData getAuthData() {
        return _authData;
    }

    public void setAuthData(AuthData authData) {
        _authData = authData;
    }
}