package com.sociallunch.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.sociallunch.android.R;
import com.sociallunch.android.fragments.SuggestedVenueFragment;
import com.sociallunch.android.models.SuggestedVenue;
import com.sociallunch.android.models.Suggestion;

public class SuggestedVenueActivity extends ActionBarActivity implements SuggestedVenueFragment.OnFragmentInteractionListener {
    public static final String EXTRA_SUGGESTED_VENUE = "extra.SUGGESTED_VENUE";
    private SuggestedVenue suggestedVenue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_venue);

        // Set a Toolbar to replace the ActionBar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent() != null) {
            suggestedVenue = getIntent().getParcelableExtra(EXTRA_SUGGESTED_VENUE);
            // Begin the transaction
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            // Replace the container with the new fragment
            ft.replace(R.id.flContent, SuggestedVenueFragment.newInstance(suggestedVenue));
            // or ft.add(R.id.your_placeholder, new FooFragment());
            // Execute the changes specified
            ft.commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_suggested_venue, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void selectSuggestion(Suggestion suggestion) {
        Intent intent = new Intent(this, SuggestionActivity.class);
        intent.putExtra(SuggestionActivity.EXTRA_SUGGESTION, suggestion);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
