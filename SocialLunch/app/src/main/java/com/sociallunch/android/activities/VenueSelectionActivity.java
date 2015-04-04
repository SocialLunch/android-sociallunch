package com.sociallunch.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.sociallunch.android.R;
import com.sociallunch.android.adapters.VenueSelectionPagerAdapter;
import com.sociallunch.android.fragments.VenueSelectionListFragment;
import com.sociallunch.android.fragments.VenueSelectionMapFragment;
import com.sociallunch.android.models.Venue;
import com.sociallunch.android.models.YelpSearchAPIResponse;
import com.sociallunch.android.net.YelpAPI;
import com.sociallunch.android.workers.VenueSelectionWorkerFragment;

public class VenueSelectionActivity extends ActionBarActivity implements
        VenueSelectionListFragment.OnFragmentInteractionListener,
        VenueSelectionMapFragment.OnFragmentInteractionListener,
        VenueSelectionWorkerFragment.OnFragmentInteractionListener {
    public static final String RESULT_SELECTED_VENUE = "result.RESULT_SELECTED_VENUE";
    private VenueSelectionPagerAdapter venueSelectionPagerAdapter;
    private SearchView searchView;
    private VenueSelectionWorkerFragment mWorkerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_selection);

        FragmentManager fm = getSupportFragmentManager();
        mWorkerFragment = (VenueSelectionWorkerFragment) fm.findFragmentByTag(VenueSelectionWorkerFragment.class.toString());

        if (mWorkerFragment == null) {
            mWorkerFragment = VenueSelectionWorkerFragment.newInstance();
            fm.beginTransaction().add(mWorkerFragment, VenueSelectionWorkerFragment.class.toString()).commit();
        } else {
            if (mWorkerFragment.mSubmittedQuery != null && mWorkerFragment.mSubmittedQuery.length() > 0) {
                setTitle(mWorkerFragment.mSubmittedQuery);
            }
        }

        // Set a Toolbar to replace the ActionBar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        venueSelectionPagerAdapter = new VenueSelectionPagerAdapter(getSupportFragmentManager(), this);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(venueSelectionPagerAdapter);

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabsStrip.setShouldExpand(true);    // has to be invoked before setting view pager
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_venue_selection, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (mWorkerFragment != null) {
                    mWorkerFragment.mSubmittedQuery = query;
                    setTitle(query);
                    mWorkerFragment.fetchYelpSearchResultsAsync(query, "San Francisco, CA");
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        if (mWorkerFragment != null &&
                mWorkerFragment.mSubmittedQuery != null &&
                mWorkerFragment.mSubmittedQuery.length() > 0) {
            searchView.setQuery(mWorkerFragment.mSubmittedQuery, false);
        }
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
    public void onFetchYelpSearchResultsTaskPreExecute() {

    }

    @Override
    public void onFetchYelpSearchResultsTaskCancelled() {

    }

    @Override
    public void onFetchYelpSearchResultsTaskPostExecute(YelpSearchAPIResponse result) {
        mWorkerFragment.mSearchResults.clear();
        mWorkerFragment.mSearchResults.addAll(result.venues);
        VenueSelectionListFragment venueSelectionListFragment = (VenueSelectionListFragment) venueSelectionPagerAdapter.getItem(VenueSelectionPagerAdapter.VenueSelectionTabIndex.LIST.ordinal());
        venueSelectionListFragment.updateItems(mWorkerFragment.mSearchResults);
    }

    @Override
    public void onAttachedVenueSelectionListFragment(VenueSelectionListFragment fragment) {
    }

    @Override
    public void selectVenue(Venue venue) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(RESULT_SELECTED_VENUE, venue);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();

        mWorkerFragment.fetchYelpSearchResultsAsync(YelpAPI.DEFAULT_TERM, YelpAPI.DEFAULT_LOCATION);
    }
}
