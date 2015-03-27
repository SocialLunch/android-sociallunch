package com.sociallunch.android.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.codepath.oauth.OAuthLoginActionBarActivity;
import com.sociallunch.android.application.OAuthApplication;
import com.sociallunch.android.R;
import com.sociallunch.android.authentication.LinkedinClient;
import com.sociallunch.android.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;


public class SignupActivity extends OAuthLoginActionBarActivity<LinkedinClient> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void onLinkedinConnect(View v) {
        LinkedinClient client = OAuthApplication.getLinkedinClient();
        client.connect();
    }

    @Override
    public void onLoginSuccess() {
        LinkedinClient client = OAuthApplication.getLinkedinClient();
        client.getProfile(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                User user = User.fromJSON(response, false);
                User storedUser = User.byId(user.getUid());

                SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("uid", user.getUid());
                editor.commit();

                if (storedUser == null) {
                    // New user
                    user.save();
                    Intent i = new Intent(SignupActivity.this, CreateProfileActivity.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(SignupActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    // Fires if the authentication process fails for any reason.
    @Override
    public void onLoginFailure(Exception e) {
        e.printStackTrace();
    }
}
