package com.sociallunch.android.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.sociallunch.android.R;
import com.sociallunch.android.application.OAuthApplication;
import com.sociallunch.android.models.User;


import java.util.ArrayList;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class SignupActivity extends ActionBarActivity {
    @InjectView(R.id.login_button) LoginButton mFacebookLoginButton;

    private CallbackManager callbackManager;
    private Firebase mFirebaseRef;
    private AccessTokenTracker accessTokenTracker;
    private ImageButton btnLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);

        callbackManager = CallbackManager.Factory.create();
        OAuthApplication application = (OAuthApplication) getApplication();
        mFirebaseRef = application.getFirebaseRef();

        btnLogo = (ImageButton)this.findViewById(R.id.btnLogo);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.gobble);
        btnLogo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                mp.start();
                Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.shake);
                btnLogo.startAnimation(animShake);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            authenticateWithFirebase(accessToken.getToken());
        } else {
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
                goToNextFragment(authData);
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

    public void goToNextFragment(final AuthData authData) {
        final OAuthApplication application = (OAuthApplication) getApplication();
        final Firebase userRef = mFirebaseRef.child("user");
        Query userQuery = userRef.child(authData.getUid());

        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Map<String, Object> value = (Map<String, Object> ) snapshot.getValue();

                User user = User.fromAuthData(authData);
                Intent i;
                if (value == null ||
                    (value.get("foodsDisliked") == null && value.get("foodsLiked") == null)) {
                    application.setCurrentUser(user);
                    i = new Intent(SignupActivity.this, CreateProfileActivity.class);
                    userRef.child(authData.getUid()).setValue(user);
                    startActivity(i);
                    overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
                } else {
                    user = User.fromMap(user, value);
                    application.setCurrentUser(user);
                    i = new Intent(SignupActivity.this, MainActivity.class);
                    userRef.child(authData.getUid()).setValue(user);
                    finish();
                    startActivity(i);
                    overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
                }

            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (accessTokenTracker != null) {
            accessTokenTracker.stopTracking();
        }
    }
}
