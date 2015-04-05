package com.sociallunch.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.sociallunch.android.R;
import com.sociallunch.android.application.OAuthApplication;
import com.sociallunch.android.fragments.SuggestionFragment;
import com.sociallunch.android.models.Suggestion;
import com.sociallunch.android.models.User;

public class SuggestionActivity extends ActionBarActivity implements SuggestionFragment.OnFragmentInteractionListener {
    public static final String EXTRA_SUGGESTION = "extra.BOOKING";
    private String suggestionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);

        // Set a Toolbar to replace the ActionBar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent() != null) {
            Suggestion suggestion = getIntent().getParcelableExtra(EXTRA_SUGGESTION);
            suggestionId = suggestion.id;

            // Begin the transaction
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            // Replace the container with the new fragment
            ft.replace(R.id.flContent, SuggestionFragment.newInstance(suggestion));
            // or ft.add(R.id.your_placeholder, new FooFragment());
            // Execute the changes specified
            ft.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_suggestion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_chat:
                launchChat();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void launchChat() {
        Intent i = new Intent(this, ChatActivity.class);
        OAuthApplication application = (OAuthApplication) getApplication();
        User user = application.getCurrentUser();

        i.putExtra("user", user);
        i.putExtra("identifier", suggestionId);
        startActivity(i);
    }
}
