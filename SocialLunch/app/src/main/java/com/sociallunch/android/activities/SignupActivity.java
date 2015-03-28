package com.sociallunch.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.sociallunch.android.R;
import com.sociallunch.android.application.OAuthApplication;


import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class SignupActivity extends ActionBarActivity {
    @InjectView(R.id.login_button) LoginButton mFacebookLoginButton;

    private CallbackManager callbackManager;
    private Firebase mFirebaseRef;
    private AccessTokenTracker accessTokenTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);

        callbackManager = CallbackManager.Factory.create();
        OAuthApplication application = (OAuthApplication) getApplication();
        mFirebaseRef = application.getFirebaseRef();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                if (newAccessToken != null) {
                    authenticateWithFirebase(newAccessToken.getToken());
                } else {
                    setupLogin();
                }
            }
        };
    }

    public void setupLogin() {
        OAuthApplication application = (OAuthApplication) getApplication();
        ArrayList<String> readPermissions = new ArrayList<>();
        readPermissions.add("user_friends");
        readPermissions.add("public_profile");
        readPermissions.add("email");
        mFacebookLoginButton.setReadPermissions(readPermissions);
        mFacebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                authenticateWithFirebase(loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException exception) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup, menu);
        return true;
    }

    public void authenticateWithFirebase(String token) {
        final OAuthApplication application = (OAuthApplication) getApplication();
        mFirebaseRef.authWithOAuthToken("facebook", token, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                application.setAuthData(authData);
                goToNextFragment();
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void goToNextFragment() {
        Intent i = new Intent(SignupActivity.this, CreateProfileActivity.class);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }
}
