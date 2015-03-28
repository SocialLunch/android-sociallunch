package com.sociallunch.android.application;

import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.sociallunch.android.models.User;

/**
 * Created by kelei on 3/22/15.
 */

public class OAuthApplication extends com.activeandroid.app.Application {
    private static Context context;
    private Firebase _ref;
    private AuthData _authData;
    private User _currentUser;

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

    public User getCurrentUser() {
        return _currentUser;
    }

    public void setAuthData(AuthData authData) {
        _authData = authData;
    }

    public void setCurrentUser(User currentUser) {
        _currentUser = currentUser;
    }
}