package com.sociallunch.android.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;

import com.google.android.gms.maps.GoogleMap;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;
import com.sociallunch.android.R;
import com.sociallunch.android.dialogs.CreateSuggestionDialogFragment;
import com.sociallunch.android.dialogs.FilterSuggestionDialogFragment;
import com.sociallunch.android.fragments.ProfileFragment;
import com.sociallunch.android.fragments.SearchFragment;
import com.sociallunch.android.fragments.SearchListFragment;
import com.sociallunch.android.fragments.SearchMapFragment;
import com.sociallunch.android.fragments.UpcomingSessionFragment;
import com.sociallunch.android.layouts.FragmentNavigationDrawer;
import com.sociallunch.android.models.Filter;
import com.sociallunch.android.models.SuggestedVenue;
import com.sociallunch.android.models.Venue;
import com.sociallunch.android.workers.SearchWorkerFragment;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends ActionBarActivity implements
        SearchFragment.OnFragmentInteractionListener,
        SearchListFragment.OnFragmentInteractionListener,
        SearchMapFragment.OnFragmentInteractionListener,
        UpcomingSessionFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener,
        CreateSuggestionDialogFragment.OnFragmentInteractionListener,
        FilterSuggestionDialogFragment.OnFragmentInteractionListener,
        SearchWorkerFragment.OnFragmentInteractionListener {
    public static final String RESULT_SELECTED_SUGGESTION = "result.SELECTED_SUGGESTION";

    public enum NavDrawerSelectedIndex {
        SEARCH,
        UPCOMING_SESSION,
        PROFILE
    }
    private FragmentNavigationDrawer dlDrawer;
    private NavDrawerSelectedIndex navDrawerSelectedIndex;
    private SearchWorkerFragment mSearchWorkerFragment;
    private SearchListFragment mSearchListFragment;
    private SearchMapFragment mSearchMapFragment;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        mSearchWorkerFragment = (SearchWorkerFragment) fm.findFragmentByTag(SearchWorkerFragment.class.toString());

        if (mSearchWorkerFragment == null) {
            mSearchWorkerFragment = SearchWorkerFragment.newInstance();
            fm.beginTransaction().add(mSearchWorkerFragment, SearchWorkerFragment.class.toString()).commit();
        }

        // Set a Toolbar to replace the ActionBar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        dlDrawer = (FragmentNavigationDrawer) findViewById(R.id.drawer_layout);
        // Setup drawer view
        dlDrawer.setupDrawerConfiguration((ListView) findViewById(R.id.lvDrawer), toolbar,
                R.layout.drawer_nav_item, R.id.flContent);
        // Add nav items
        dlDrawer.addNavItem(getString(R.string.search_fragment_title), R.drawable.ic_nav_search, getString(R.string.search_fragment_title), SearchFragment.class);
//        dlDrawer.addNavItem(getString(R.string.upcoming_session_fragment_title), R.drawable.ic_nav_sessions, getString(R.string.upcoming_session_fragment_title), UpcomingSessionFragment.class);
        dlDrawer.addNavItem(getString(R.string.profile_fragment_title), R.drawable.ic_nav_profile, getString(R.string.profile_fragment_title), ProfileFragment.class);
        // Select default
        if (savedInstanceState == null) {
            dlDrawer.selectDrawerItem(0);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content
        if (dlDrawer.isDrawerOpen()) {
            // Uncomment to hide menu items
            // menu.findItem(R.id.mi_test).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        if (navDrawerSelectedIndex == NavDrawerSelectedIndex.SEARCH) {
            getMenuInflater().inflate(R.menu.menu_search, menu);
            MenuItem searchItem = menu.findItem(R.id.action_search);
            menu.findItem(R.id.action_filter).setIcon(
                    new IconDrawable(this, Iconify.IconValue.fa_filter)
                            .colorRes(R.color.white)
                            .actionBarSize());
            menu.findItem(R.id.action_suggest).setIcon(
                    new IconDrawable(this, Iconify.IconValue.fa_plus)
                            .colorRes(R.color.white)
                            .actionBarSize());
            menu.findItem(R.id.action_search).setIcon(
                    new IconDrawable(this, Iconify.IconValue.fa_search)
                            .colorRes(R.color.white)
                            .actionBarSize());
            searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    if (mSearchWorkerFragment != null) {
                        mSearchWorkerFragment.filterSuggestions(query);
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (newText.isEmpty()) {
                        if (mSearchWorkerFragment != null) {
                            mSearchWorkerFragment.filterSuggestions(null);
                        }
                    }
                    return false;
                }
            });
            if (mSearchWorkerFragment.mQuery != null && !mSearchWorkerFragment.mQuery.isEmpty()) {
                searchView.setQuery(mSearchWorkerFragment.mQuery, false);
                searchView.setIconified(false);
            }
        } else if (searchView != null) {
            InputMethodManager mgr = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (dlDrawer.getDrawerToggle().onOptionsItemSelected(item)) {
            return true;
        } else {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            if (id == R.id.action_filter) {
                FragmentManager fm = getSupportFragmentManager();
                FilterSuggestionDialogFragment alertDialog =
                    FilterSuggestionDialogFragment.newInstance(mSearchWorkerFragment.getFilter());
                alertDialog.show(fm, FilterSuggestionDialogFragment.class.toString());
                return true;
            } else if (id == R.id.action_suggest) {
                FragmentManager fm = getSupportFragmentManager();
                CreateSuggestionDialogFragment alertDialog = CreateSuggestionDialogFragment.newInstance();
                alertDialog.show(fm, CreateSuggestionDialogFragment.class.toString());
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        dlDrawer.getDrawerToggle().syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        dlDrawer.getDrawerToggle().onConfigurationChanged(newConfig);
    }

    @Override
    public void onSearchFragmentAttached(SearchFragment searchFragment) {
        navDrawerSelectedIndex = NavDrawerSelectedIndex.SEARCH;
        setTitle(getString(R.string.search_fragment_title));
        invalidateOptionsMenu();

        mSearchWorkerFragment.fetchSuggestions();
    }

    @Override
    public void onUpcomingSessionFragmentAttached() {
        navDrawerSelectedIndex = NavDrawerSelectedIndex.UPCOMING_SESSION;
        setTitle(getString(R.string.upcoming_session_fragment_title));
        invalidateOptionsMenu();
    }

    @Override
    public void onProfileFragmentAttached() {
        navDrawerSelectedIndex = NavDrawerSelectedIndex.PROFILE;
        setTitle(getString(R.string.profile_fragment_title));
        invalidateOptionsMenu();
    }

    @Override
    public void requestToCreateSuggestion(Venue venue, Calendar meetingTime) {
        mSearchWorkerFragment.addSuggestion(venue, meetingTime);
    }

    @Override
    public void filterSuggestions(Calendar earliestMeetingTime, Calendar latestMeetingTime) {
        Filter filter = mSearchWorkerFragment.getFilter();
        if (filter == null) {
            filter = new Filter();
        }
        filter.setEarliestMeetingTime(earliestMeetingTime);
        filter.setLatestMeetingTime(latestMeetingTime);
        mSearchWorkerFragment.setFilter(filter);
        onRequestToRefresh();
    }

    @Override
    public void onSearchListFragmentAttached(SearchListFragment searchListFragment) {
        mSearchListFragment = searchListFragment;
    }

    @Override
    public void onSearchMapFragmentAttached(SearchMapFragment searchMapFragment) {
        mSearchMapFragment = searchMapFragment;
    }

    @Override
    public void onMapInSearchMapFragmentLoaded(SearchMapFragment searchMapFragment, GoogleMap googleMap) {
        if (searchMapFragment != null && mSearchWorkerFragment != null) {
            searchMapFragment.updateItems(mSearchWorkerFragment.mFilteredSuggestedVenues);
        }
    }

    @Override
    public void selectSuggestedvenue(SuggestedVenue suggestedVenue, View view) {
        Intent intent = new Intent(this, SuggestedVenueActivity.class);
        intent.putExtra(SuggestedVenueActivity.EXTRA_SUGGESTED_VENUE, suggestedVenue);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, "suggestion");
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onRequestToRefresh() {
        mSearchWorkerFragment.fetchSuggestions();
    }

    @Override
    public void onUpdatedSuggestedVenues(ArrayList<SuggestedVenue> suggestedVenues) {
        if (mSearchListFragment != null) {
            mSearchListFragment.updateItems(suggestedVenues);
        }
        if (mSearchMapFragment != null) {
            mSearchMapFragment.updateItems(suggestedVenues);
        }
    }
}