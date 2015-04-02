package com.sociallunch.android.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.sociallunch.android.R;
import com.sociallunch.android.fragments.ProfileFragment;
import com.sociallunch.android.fragments.SearchFragment;
import com.sociallunch.android.fragments.SearchListFragment;
import com.sociallunch.android.fragments.SearchMapFragment;
import com.sociallunch.android.fragments.UpcomingSessionFragment;
import com.sociallunch.android.layouts.FragmentNavigationDrawer;

public class MainActivity extends ActionBarActivity implements
        SearchFragment.OnFragmentInteractionListener,
        SearchListFragment.OnFragmentInteractionListener,
        SearchMapFragment.OnFragmentInteractionListener,
        UpcomingSessionFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener {
    private FragmentNavigationDrawer dlDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        dlDrawer.addNavItem(getString(R.string.upcoming_session_fragment_title), R.drawable.ic_nav_sessions, getString(R.string.upcoming_session_fragment_title), UpcomingSessionFragment.class);
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_search) {
                Toast.makeText(this, getString(R.string.action_search), Toast.LENGTH_SHORT).show();//TODO-TEMP
                return true;
            } else if (id == R.id.action_filter) {
                Toast.makeText(this, getString(R.string.action_filter), Toast.LENGTH_SHORT).show();//TODO-TEMP
                return true;
            } else if (id == R.id.action_suggest) {
                Toast.makeText(this, getString(R.string.action_suggest), Toast.LENGTH_SHORT).show();//TODO-TEMP
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
}