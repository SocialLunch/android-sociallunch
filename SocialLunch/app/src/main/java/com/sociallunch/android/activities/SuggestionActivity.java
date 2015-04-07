package com.sociallunch.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;
import com.sociallunch.android.R;
import com.sociallunch.android.application.OAuthApplication;
import com.sociallunch.android.fragments.SuggestionFragment;
import com.sociallunch.android.fragments.UsersJoinedFragment;
import com.sociallunch.android.models.Suggestion;
import com.sociallunch.android.models.User;
import com.sociallunch.android.workers.JoinedWorkerFragment;

import java.util.ArrayList;

public class SuggestionActivity extends ActionBarActivity implements SuggestionFragment.OnFragmentInteractionListener,
                                                                     UsersJoinedFragment.OnFragmentInteractionListener,
                                                                     JoinedWorkerFragment.OnFragmentInteractionListener {
    public static final String EXTRA_SUGGESTION = "extra.SUGGESTION";
    private JoinedWorkerFragment mJoinedWorkerFragment;
    private Suggestion suggestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);

        FragmentManager fm = getSupportFragmentManager();
        mJoinedWorkerFragment = (JoinedWorkerFragment) fm.findFragmentByTag(JoinedWorkerFragment.class.toString());

        if (mJoinedWorkerFragment == null) {
            mJoinedWorkerFragment = JoinedWorkerFragment.newInstance();
            fm.beginTransaction().add(mJoinedWorkerFragment, JoinedWorkerFragment.class.toString()).commit();
        }

        // Set a Toolbar to replace the ActionBar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent() != null) {
            suggestion = getIntent().getParcelableExtra(EXTRA_SUGGESTION);
            // Begin the transaction
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            // Replace the container with the new fragment
            ft.replace(R.id.flContent, SuggestionFragment.newInstance(suggestion));
            ft.replace(R.id.flJoined, UsersJoinedFragment.newInstance());
            // or ft.add(R.id.your_placeholder, new FooFragment());
            // Execute the changes specified
            ft.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_suggestion, menu);
        menu.findItem(R.id.action_chat_icon).setIcon(
                new IconDrawable(this, Iconify.IconValue.fa_comment_o)
                        .colorRes(R.color.white)
                        .actionBarSize());
        menu.findItem(R.id.action_join_icon).setIcon(
                new IconDrawable(this, Iconify.IconValue.fa_thumbs_o_up)
                        .colorRes(R.color.white)
                        .actionBarSize());
        menu.findItem(R.id.action_decline).setIcon(
                new IconDrawable(this, Iconify.IconValue.fa_thumbs_o_down)
                        .colorRes(R.color.white)
                        .actionBarSize());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_chat_icon:
                launchChat();
                return true;
            case R.id.action_decline:
                decline();
                return true;
            case R.id.action_join_icon:
                join();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void launchChat() {
        Intent i = new Intent(this, ChatActivity.class);
        OAuthApplication application = (OAuthApplication) getApplication();
        User user = application.getCurrentUser();
        i.putExtra("user", user);
        i.putExtra("identifier", suggestion.id);
        startActivity(i);
    }

    public void join() {
        mJoinedWorkerFragment.joinSuggestion(suggestion,true);
    }

    public void decline() {
        mJoinedWorkerFragment.joinSuggestion(suggestion,false);
    }


    @Override
    public void onUsersJoinedFragmentAttached(UsersJoinedFragment usersJoinedFragment) {
        mJoinedWorkerFragment.fetchJoinedUsers(suggestion);
    }

    @Override
    public void selectUser(User user) {
        // View User Profile?
    }

    @Override
    public void onRequestToRefresh() {
        mJoinedWorkerFragment.fetchJoinedUsers(suggestion);
    }

    @Override
    public void onUpdatedJoinedUsers(ArrayList<User> users) {
        FragmentManager fm = getSupportFragmentManager();
        UsersJoinedFragment fragment = (UsersJoinedFragment) fm.findFragmentById(R.id.flJoined);
        if (fragment != null) {
            fragment.updateItems(users);
        }
    }
}
